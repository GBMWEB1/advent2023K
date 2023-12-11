class Day10(private val grid: List<List<Pipe>>) {

    private var currentInfection = listOf<Pipe>()

    companion object{


        fun of(data: List<String>): Day10{
            val pipes = mutableListOf<List<Pipe>>()
            for (y in 0.. data.size-1){
                var row = mutableListOf<Pipe>()
                data[y].forEachIndexed { x, c ->
                    row.add(Pipe(c, x, y))
                }
                pipes.add(row)
            }
            return Day10(pipes)
        }
    }

    class Pipe(var type : Char, val x: Int, val y : Int){
        var horizontalGap: Boolean = false
        var enclosed: Boolean = false
        var verticalGap: Boolean = false
        var infected: Boolean = false
        var navigated =false

        var nextPipe: Pipe? = null
        var previousPipe: Pipe? = null

        fun next(): Pipe {
            if (nextPipe!= null){
                return nextPipe!!
            }
            throw Error("No next pipe configured")
        }

        fun prev(): Pipe {
            if (previousPipe!= null){
                return previousPipe!!
            }
            throw Error("No next pipe configured")
        }

        fun navigate(oldPipe: Pipe): Pipe {
            navigated = true
            if (next()== oldPipe){
                return prev();
            }
            return next()
        }

        fun display() {
            if (infected){
                print("O")
            } else if (enclosed){
                print("I")
            } else{
                print(type)
            }
        }

    }
    fun joinPipes() {
        grid.forEach {
            it.forEach { linkPipe(it) }
        }
    }

    fun getPipe(x: Int, y:Int): Pipe? {
        if (y== grid.size || y ==-1){
            return null;
        }
        if (x== grid[0].size || x ==-1){
            return null;
        }
        return grid[y][x];
    }

    private fun linkPipe(pipe: Pipe) {
        if (pipe.type=='.'){
            return
        }
        if (pipe.type=='S'){
            var joiningPipes = mutableListOf<Pipe>()
            var topPipe = getPipe(pipe.x, pipe.y-1)

            if (topPipe!= null && (topPipe.type == '|' || topPipe.type == '7' || topPipe.type == 'F'  )){
                joiningPipes.add(topPipe)
            }

            var bottomPipe = getPipe(pipe.x, pipe.y+1)
            if (bottomPipe!= null &&(bottomPipe.type == '|' || bottomPipe.type == 'L' || bottomPipe.type == 'J' )){
                joiningPipes.add(bottomPipe)
            }

            var leftPipe = getPipe(pipe.x-1, pipe.y)
            if (leftPipe!= null &&(leftPipe.type == '-' || leftPipe.type == 'L' || leftPipe.type == 'F'  )){
                joiningPipes.add(leftPipe)
            }

            var rightPipe = getPipe(pipe.x+1, pipe.y)
            if (rightPipe!= null &&(rightPipe.type == '-' || rightPipe.type == '7' || rightPipe.type == 'J' )){
                joiningPipes.add(rightPipe)
            }
            if (joiningPipes.size==2){
                pipe.nextPipe = joiningPipes[0]
                pipe.previousPipe = joiningPipes[1]
            }
        }

        if (pipe.type=='|'){
            pipe.previousPipe = getPipe(pipe.x, pipe.y-1)
            pipe.nextPipe = getPipe(pipe.x, pipe.y+1)
        }
        if (pipe.type=='L'){
            pipe.previousPipe = getPipe(pipe.x, pipe.y-1)
            pipe.nextPipe = getPipe(pipe.x+1, pipe.y)
        }
        if (pipe.type=='-'){
            pipe.previousPipe = getPipe(pipe.x-1, pipe.y)
            pipe.nextPipe = getPipe(pipe.x+1, pipe.y)
        }

        if (pipe.type=='J'){
            pipe.previousPipe = getPipe(pipe.x, pipe.y-1)
            pipe.nextPipe = getPipe(pipe.x-1, pipe.y)
        }

        if (pipe.type=='7'){
            pipe.previousPipe = getPipe(pipe.x-1, pipe.y)
            pipe.nextPipe = getPipe(pipe.x, pipe.y+1)
        }

        if (pipe.type=='7'){
            pipe.previousPipe = getPipe(pipe.x-1, pipe.y)
            pipe.nextPipe = getPipe(pipe.x, pipe.y+1)
        }

        if (pipe.type=='F'){
            pipe.previousPipe = getPipe(pipe.x+1, pipe.y)
            pipe.nextPipe = getPipe(pipe.x, pipe.y+1)
        }
    }

    fun getStartingPipe(): Pipe {
        return grid.flatten().find { it.type=='S'}!!
    }

    fun navigate(): Int {
        var previous = getStartingPipe();
        previous.navigated = true
        var f1 = previous.next();
        f1.navigated=true

        var steps =1;
        while (f1.type != 'S'){

            var next = f1.navigate(previous)
            previous = f1
            f1 = next
            steps++
        }
        return steps/2
    }

    fun display() {
        println()
        grid.forEach {
            it.forEach { it.display() }
            println()
        }
    }

    fun startInfection() {
        // identify verticalGaps
        for (y in 0..grid.size-1 ){
            for (x in 0.. grid.size-2){
                var cell = grid[y][x]
                var nextCell = grid[y][x+1]
                if (listOf('J','|','7','.').contains(cell.type) &&
                    listOf('F','|','L','.').contains(nextCell.type)){
                    if (cell.type!='.'){
                        cell.verticalGap = true
                    }
                }
            }
        }
        // identify horizontal gaps
        for (y in 0..grid.size-2 ){
            for (x in 0.. grid.size-1){
                var cell = grid[y][x]
                var nextCell = grid[y+1][x]
                if (listOf('L','-','J','.').contains(cell.type) &&
                    listOf('F','-','7','.').contains(nextCell.type)){
                    if (cell.type!='.'){
                        cell.horizontalGap = true
                    }
                }
            }
        }

        currentInfection = grid.flatten().filter { isEdge(it) && !it.navigated }
        currentInfection.forEach { it.infected = true }
    }

    private fun isEdge(it: Pipe): Boolean {
        return it.x==0 || it.x == grid[0].size-1 || it.y==0 || it.y == grid.size-1
    }

    fun spreadToEnd(){
        while (currentInfection.size>0){
            spreadInfection()
        }
       grid.flatten().filter { !it.infected && !it.navigated && it.type!= 'S'}.forEach { it.enclosed = true }
    }

    fun spreadInfection(){
        var nextInfected = mutableListOf<Pipe>()
        currentInfection.forEach {

                if (it.x > 0) { // check if can spread to left
                    var leftCell = grid[it.y][it.x - 1]
                    if (!leftCell.navigated && !leftCell.infected){
                        nextInfected.add(leftCell)
                    } else {
                        while (leftCell.horizontalGap && leftCell.x > 0) {
                            leftCell = grid[leftCell.y][leftCell.x - 1]
                        }
                        if (!leftCell.infected && !leftCell.horizontalGap) {
                            if (!leftCell.navigated) {
                                nextInfected.add(leftCell)
                            }
                        }
                    }
                }
                if (it.x < grid[0].size - 1) { // check if can spread to right
                    var rightCell = grid[it.y][it.x + 1]
                    if (!rightCell.navigated && !rightCell.infected){
                        nextInfected.add(rightCell)
                    } else {
                        while (rightCell.horizontalGap && rightCell.x < grid[0].size - 1) {
                            rightCell = grid[rightCell.y][rightCell.x + 1]
                        }
                        if (!rightCell.infected && !rightCell.horizontalGap) {
                            if (!rightCell.navigated) {
                                nextInfected.add(rightCell)
                            }
                        }
                    }
                }

            if (it.y>0){ // check if can spread to up
                var upperCell = grid[it.y-1][it.x]
                if (!upperCell.navigated && !upperCell.infected){
                    nextInfected.add(upperCell)
                } else {
                    while (upperCell.verticalGap && upperCell.y > 0) {
                        var rightCell = grid[upperCell.y][upperCell.x + 1]
                        if (rightCell.type == '.') {
                            nextInfected.add(rightCell)
                        }
                        upperCell = grid[upperCell.y - 1][upperCell.x]
                    }
                    if (!upperCell.infected && !upperCell.verticalGap) {
                        if (!upperCell.navigated) {
                            nextInfected.add(upperCell)
                        }
                    }
                }
            }
            if (it.y<grid.size-1){ // check if can spread dpwm
                var lowerCell = grid[it.y+1][it.x]
                if (!lowerCell.navigated && !lowerCell.infected){
                    nextInfected.add(lowerCell)
                } else {
                    while (lowerCell.verticalGap && lowerCell.y < grid.size - 1) {
                        var rightCell = grid[lowerCell.y][lowerCell.x + 1]
                        if (rightCell.type == '.' && !rightCell.infected) {
                            nextInfected.add(rightCell)
                        }
                        lowerCell = grid[lowerCell.y + 1][lowerCell.x]
                    }
                    if (!lowerCell.infected && !lowerCell.verticalGap) {
                        if (!lowerCell.navigated) {
                            nextInfected.add(lowerCell)
                        }
                    }
                }
            }
        }
        nextInfected.forEach { it.infected = true }
        currentInfection = nextInfected
    }

    fun getEnclosedTiles(): Int {
        grid.flatten().filter { !it.infected && !it.navigated && it.type!= 'S'}.forEach { it.enclosed = true }
        return grid.flatten().filter { it.enclosed }.size
    }

    fun resetInfection() {
        for (y in 0..grid.size-1 ) {
            for (x in 0..grid.size - 1) {
                if (grid[y][x].infected){
                    grid[y][x].infected=false
                    grid[y][x].type='.'
                }
                grid[y][x].enclosed=false
            }
        }
        currentInfection = grid.flatten().filter { isEdge(it) && !it.navigated }
        currentInfection.forEach { it.infected = true }
    }
}