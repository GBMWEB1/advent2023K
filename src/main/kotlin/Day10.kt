class Day10(private var grid: List<List<Pipe>>) {

    private var currentInfection = listOf<Pipe>()

    companion object{


        fun of(lines: List<String>): Day10{
            val pipes = mutableListOf<List<Pipe>>()
            for (line in lines){
                val row = mutableListOf<Pipe>()
                line.forEach { c ->
                    row.add(Pipe(c))
                }
                pipes.add(row)
            }
            return Day10(pipes)
        }
    }

    class Pipe(var type : Char, var navigated: Boolean = false, val gap: Boolean = false){

        var enclosed: Boolean = false

        var infected: Boolean = false

        var nextPipe: Pipe? = null
        var previousPipe: Pipe? = null

        var topPipe: Pipe? = null
        var bottomPipe : Pipe? = null
        var leftPipe : Pipe? = null
        var rightPipe : Pipe? = null

        private fun canBeInfected(): Boolean{
            return !navigated && !infected
        }

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
                return prev()
            }
            return next()
        }

        fun spreadInfection() : List<Pipe>{
            val spread = mutableListOf<Pipe>()

            if (topPipe!= null && topPipe!!.canBeInfected()) {
                spread.add(topPipe!!)
            }

            if (bottomPipe!= null && bottomPipe!!.canBeInfected()) {
                spread.add(bottomPipe!!)
            }

            if (leftPipe!= null && leftPipe!!.canBeInfected()) {
                spread.add(leftPipe!!)
            }
            if (rightPipe!= null && rightPipe!!.canBeInfected()) {
                spread.add(rightPipe!!)
            }

            spread.forEach { it.infected= true }
            return spread.toList()
        }

        fun display() {
            if (infected){
                print('I')
            } else if (enclosed) {
                print('O')
            } else{
                print(type)
            }
        }

    }
    fun joinPipes() {
        for (y in grid.indices){
            for (x in 0..<grid[0].size){
                val pipe = grid[y][x]
                if (x > 0) {
                    pipe.leftPipe = grid[y][x-1]
                }
                if (x < grid[0].size - 2) {
                    pipe.rightPipe = grid[y][x+1]
                }
                if (y > 0) {
                    pipe.topPipe = grid[y-1][x]
                }
                if (y < grid.size - 2) {
                    pipe.bottomPipe = grid[y+1][x]
                }
            }
        }

        for (y in grid.indices) {
            for (x in 0..<grid[0].size) {
                linkPipe(getPipe(x,y)!!,x,y )
            }
        }
    }

    fun getPipe(x: Int, y:Int): Pipe? {
        if (y== grid.size || y ==-1){
            return null
        }
        if (x== grid[0].size || x ==-1){
            return null
        }
        return grid[y][x]
    }

    private fun linkPipe(pipe: Pipe, x: Int, y:Int) {
        if (pipe.type == 'S') {
            val joiningPipes = mutableListOf<Pipe>()
            val topPipe = pipe.topPipe
            if (topPipe != null && (topPipe.type == '|' || topPipe.type == '7' || topPipe.type == 'F')) {
                joiningPipes.add(topPipe)
            }

            val bottomPipe = pipe.bottomPipe
            if (bottomPipe != null && (bottomPipe.type == '|' || bottomPipe.type == 'L' || bottomPipe.type == 'J')) {
                joiningPipes.add(bottomPipe)
            }

            val leftPipe = pipe.leftPipe
            if (leftPipe != null && (leftPipe.type == '-' || leftPipe.type == 'L' || leftPipe.type == 'F')) {
                joiningPipes.add(leftPipe)
            }

            val rightPipe = pipe.rightPipe
            if (rightPipe != null && (rightPipe.type == '-' || rightPipe.type == '7' || rightPipe.type == 'J')) {
                joiningPipes.add(rightPipe)
            }
            if (joiningPipes.size == 2) {
                pipe.nextPipe = joiningPipes[0]
                pipe.previousPipe = joiningPipes[1]
            }
        }

        if (pipe.type == '|') {
            pipe.previousPipe = getPipe(x, y - 1)
            pipe.nextPipe = getPipe(x, y + 1)
        }
        if (pipe.type == '-') {
            pipe.previousPipe = getPipe(x - 1, y)
            pipe.nextPipe = getPipe(x + 1, y)
        }
        if (pipe.type == 'L') {
            pipe.previousPipe = getPipe(x, y - 1)
            pipe.nextPipe = getPipe(x + 1, y)
        }
        if (pipe.type == 'J') {
            pipe.previousPipe = getPipe(x, y - 1)
            pipe.nextPipe = getPipe(x - 1, y)
        }
        if (pipe.type == '7') {
            pipe.previousPipe = getPipe(x - 1, y)
            pipe.nextPipe = getPipe(x, y + 1)
        }
        if (pipe.type == 'F') {
            pipe.previousPipe = getPipe(x + 1, y)
            pipe.nextPipe = getPipe(x, y + 1)
        }
    }

    private fun getStartingPipe(): Pipe {
        return grid.flatten().find { it.type=='S'}!!
    }

    fun navigate(): Int {
        var previous = getStartingPipe()
        previous.navigated = true
        var f1 = previous.next()
        f1.navigated=true

        var steps =1
        while (f1.type != 'S'){

            val next = f1.navigate(previous)
            previous = f1
            f1 = next
            steps++
        }
        return steps/2
    }

    fun display() {
        println()
        grid.forEach {
            it.forEach { pipe -> pipe.display() }
            println()
        }
    }

    fun startInfection() {
        for (y in grid.indices) {
            for (x in 0..<grid[0].size) {
                val pipe = getPipe(x,y)!!
                if (isEdge(x,y) && !pipe.navigated){
                    pipe.infected = true
                }
            }
        }
        currentInfection = grid.flatten().filter { it.infected }
    }

    private fun isEdge(x: Int, y:Int): Boolean {
        return x==0 || x == grid[0].size-1 || y==0 || y == grid.size-1
    }

    fun spreadToEnd(){
        while (currentInfection.isNotEmpty()){
            spreadInfection()
        }
        grid.flatten().forEach { it.enclosed=false }
        grid.flatten()
            .filter { it.type!=' ' && !it.gap }
            .filter { !it.infected && !it.navigated }
            .forEach { it.enclosed = true }
    }

    fun spreadInfection(){
        val nextInfected = currentInfection.flatMap { it.spreadInfection() }
        currentInfection = nextInfected
    }

    fun getEnclosedTiles(): Int {
        return grid.flatten().filter { it.enclosed }.size
    }

    fun expand(startPipe: Char) {
        getStartingPipe().type= startPipe
        grid = grid.flatMap{ pipes: List<Pipe> ->
          expandRow(pipes)
        }
        joinPipes()
    }

    private fun expandRow(row: List<Pipe>) : List<List<Pipe>> {
        val rowBefore = mutableListOf<Pipe>()
        val currentRow = mutableListOf<Pipe>()
        val rowAfter = mutableListOf<Pipe>()

        for (pipe in row) {
            when (pipe.type) {
                '.' -> {
                    rowBefore.add(Pipe(' '))
                    rowBefore.add(Pipe(' '))
                    rowBefore.add(Pipe(' '))
                    currentRow.add(Pipe(' '))
                    currentRow.add(pipe)
                    currentRow.add(Pipe(' '))
                    rowAfter.add(Pipe(' '))
                    rowAfter.add(Pipe(' '))
                    rowAfter.add(Pipe(' '))
                }

                '-' -> {
                    rowBefore.add(Pipe(' '))
                    rowBefore.add(Pipe(' '))
                    rowBefore.add(Pipe(' '))
                    currentRow.add(Pipe('-', pipe.navigated, true))
                    currentRow.add(pipe)
                    currentRow.add(Pipe('-', pipe.navigated, true))
                    rowAfter.add(Pipe(' '))
                    rowAfter.add(Pipe(' '))
                    rowAfter.add(Pipe(' '))
                }

                '|' -> {
                    rowBefore.add(Pipe(' '))
                    rowBefore.add(Pipe('|', pipe.navigated, true))
                    rowBefore.add(Pipe(' '))
                    currentRow.add(Pipe(' '))
                    currentRow.add(pipe)
                    currentRow.add(Pipe(' '))
                    rowAfter.add(Pipe(' '))
                    rowAfter.add(Pipe('|', pipe.navigated, true))
                    rowAfter.add(Pipe(' '))
                }

                'F' -> {
                    rowBefore.add(Pipe(' '))
                    rowBefore.add(Pipe(' '))
                    rowBefore.add(Pipe(' '))
                    currentRow.add(Pipe(' '))
                    currentRow.add(pipe)
                    currentRow.add(Pipe('-', pipe.navigated, true))
                    rowAfter.add(Pipe(' '))
                    rowAfter.add(Pipe('|', pipe.navigated, true))
                    rowAfter.add(Pipe(' '))
                }

                'J' -> {
                    rowBefore.add(Pipe(' '))
                    rowBefore.add(Pipe('|', pipe.navigated, true))
                    rowBefore.add(Pipe(' '))
                    currentRow.add(Pipe('-', pipe.navigated, true))
                    currentRow.add(pipe)
                    currentRow.add(Pipe(' '))
                    rowAfter.add(Pipe(' '))
                    rowAfter.add(Pipe(' '))
                    rowAfter.add(Pipe(' '))
                }

                'L' -> {
                    rowBefore.add(Pipe(' '))
                    rowBefore.add(Pipe('|', pipe.navigated, true))
                    rowBefore.add(Pipe(' '))
                    currentRow.add(Pipe(' '))
                    currentRow.add(pipe)
                    currentRow.add(Pipe('-', pipe.navigated, true))
                    rowAfter.add(Pipe(' '))
                    rowAfter.add(Pipe(' '))
                    rowAfter.add(Pipe(' '))
                }

                '7' -> {
                    rowBefore.add(Pipe(' '))
                    rowBefore.add(Pipe(' '))
                    rowBefore.add(Pipe(' '))
                    currentRow.add(Pipe('-', pipe.navigated, true))
                    currentRow.add(pipe)
                    currentRow.add(Pipe(' '))
                    rowAfter.add(Pipe(' '))
                    rowAfter.add(Pipe('|', pipe.navigated, true))
                    rowAfter.add(Pipe(' '))
                }
            }

        }
        return listOf(rowBefore, currentRow, rowAfter)
    }
}