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
        private var infectBottom: Boolean = false
        private var infectLeft: Boolean = false
        private var infectRight: Boolean = false
        private var infectTop: Boolean = false

        var leftGap: Boolean = false
        var rightGap: Boolean = false
        var topGap: Boolean = false
        var bottomGap: Boolean = false

        var navigated =false
        var enclosed: Boolean = false

        var infected: Boolean = false

        var nextPipe: Pipe? = null
        var previousPipe: Pipe? = null

        var topPipe: Pipe? = null
        var bottomPipe : Pipe? = null
        var leftPipe : Pipe? = null
        var rightPipe : Pipe? = null

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

        fun infectFromTop(): Boolean{
            if (infectTop){
                return false;
            } else{
                infectTop=true
            }
            if (type=='.' || !navigated){
                infectLeft = true
                infectRight= true
                infectBottom = true
                infected = true;
                return true
            }
            else if (topGap){
                if (rightGap){
                    infectRight = true;
                }
                if (leftGap){
                    infectLeft = true;
                }
                return true;
            }
            return false
        }

        fun infectFromBottom(): Boolean {
            if (infectBottom){
                return false;
            } else{
                infectBottom=true
            }
            if (type=='.' || !navigated){
                infectLeft = true
                infectRight= true
                infectTop = true
                infected = true;
                return true
            }
            else if (bottomGap){
                if (rightGap){
                    infectRight = true;
                }
                if (leftGap){
                    infectLeft = true;
                }
            }
            return true
        }

        fun infectFromLeft(): Boolean {
            if (infectLeft){
                return false;
            } else{
                infectLeft=true
            }
            if (type=='.' || !navigated){
                infectRight= true
                infectTop = true
                infectBottom = true
                infected = true
                return true
            }
            else if (leftGap){
                if (topGap){
                    infectTop = true;
                }
                if (bottomGap){
                    infectBottom = true;
                }
                if (type=='.'){
                    infectRight = true
                }
                return true
            }
            return false
        }

        fun infectFromRight(): Boolean {
            if (infectRight){
                return false;
            } else{
                infectRight=true
            }

            if (type=='.' || !navigated){
                infectLeft = true
                infectTop = true
                infectBottom = true
                infected = true
                return true
            }
            else if (rightGap){
                if (topGap){
                    infectTop = true;
                }
                if (bottomGap){
                    infectBottom = true;
                }
                return true;
            }
            return false
        }

        fun spreadInfection() : List<Pipe>{
            var spread = mutableListOf<Pipe>()
            if (x==4 && y==7){
                println()
            }
            if (infectTop) {
                if (topPipe!= null && topPipe!!.bottomGap && topPipe!!.infectFromBottom()) {
                    spread.add(topPipe!!)
                }
                if (leftPipe!= null && leftPipe!!.topGap && leftPipe!!.infectFromTop()) {
                    spread.add(leftPipe!!)
                }
                if (rightPipe!= null && rightPipe!!.topGap && rightPipe!!.infectFromTop()) {
                    spread.add(rightPipe!!)
                }

            }
            if (infectBottom) {
                if (bottomPipe!= null && bottomPipe!!.topGap && bottomPipe!!.infectFromTop()) {
                    spread.add(bottomPipe!!)
                }
                if (leftPipe!= null && leftPipe!!.bottomGap && leftPipe!!.infectFromBottom()) {
                    spread.add(leftPipe!!)
                }
                if (rightPipe!= null && rightPipe!!.bottomGap && rightPipe!!.infectFromBottom()) {
                    spread.add(rightPipe!!)
                }
            }

            if (infectLeft) {
                if (leftPipe!= null && leftPipe!!.rightGap && leftPipe!!.infectFromRight()) {
                    spread.add(leftPipe!!)
                }
                if (topPipe!= null && topPipe!!.leftGap && topPipe!!.infectFromLeft()) {
                    spread.add(topPipe!!)
                }
                if (bottomPipe!= null && bottomPipe!!.leftGap && bottomPipe!!.infectFromLeft()) {
                    spread.add(bottomPipe!!)
                }
            }

            if (infectRight) {
                if (rightPipe!= null && rightPipe!!.leftGap && rightPipe!!.infectFromLeft()) {
                    spread.add(rightPipe!!)
                }
                if (topPipe!= null && topPipe!!.rightGap && topPipe!!.infectFromRight()) {
                    spread.add(topPipe!!)
                }
                if (bottomPipe!= null && bottomPipe!!.rightGap && bottomPipe!!.infectFromRight()) {
                    spread.add(bottomPipe!!)
                }
            }
            return spread.toList()
        }

        fun display() {
            if (navigated){
                print(type)
            } else if (enclosed){
                print("I")
            } else if (infected){
                print(".")
            }else {
                print("?")
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
        if (pipe.x > 0) {
            pipe.leftPipe = getPipe(pipe.x - 1, pipe.y)
        }
        if (pipe.y > 0) {
            pipe.topPipe = getPipe(pipe.x, pipe.y - 1)
        }
        if (pipe.x < grid[0].size - 2) {
            pipe.rightPipe = getPipe(pipe.x + 1, pipe.y)
        }
        if (pipe.y < grid.size - 2) {
            pipe.bottomPipe = getPipe(pipe.x, pipe.y + 1)
        }

        if (pipe.type == '.') {
            pipe.topGap = true
            pipe.leftGap = true
            pipe.rightGap = true
            pipe.bottomGap = true
        }
        if (pipe.type == 'S') {
            var joiningPipes = mutableListOf<Pipe>()
            var topPipe = pipe.topPipe
            if (topPipe != null && (topPipe.type == '|' || topPipe.type == '7' || topPipe.type == 'F')) {
                joiningPipes.add(topPipe)
            }

            var bottomPipe = pipe.bottomPipe
            if (bottomPipe != null && (bottomPipe.type == '|' || bottomPipe.type == 'L' || bottomPipe.type == 'J')) {
                joiningPipes.add(bottomPipe)
            }

            var leftPipe = pipe.leftPipe
            if (leftPipe != null && (leftPipe.type == '-' || leftPipe.type == 'L' || leftPipe.type == 'F')) {
                joiningPipes.add(leftPipe)
            }

            var rightPipe = pipe.rightPipe
            if (rightPipe != null && (rightPipe.type == '-' || rightPipe.type == '7' || rightPipe.type == 'J')) {
                joiningPipes.add(rightPipe)
            }
            if (joiningPipes.size == 2) {
                pipe.nextPipe = joiningPipes[0]
                pipe.previousPipe = joiningPipes[1]
            }
        }

        if (pipe.type == '|') {
            pipe.previousPipe = getPipe(pipe.x, pipe.y - 1)
            pipe.nextPipe = getPipe(pipe.x, pipe.y + 1)
            pipe.leftGap = true
            pipe.rightGap = true
        }
        if (pipe.type == '-') {
            pipe.previousPipe = getPipe(pipe.x - 1, pipe.y)
            pipe.nextPipe = getPipe(pipe.x + 1, pipe.y)
            pipe.topGap = true
            pipe.bottomGap = true
        }

        if (pipe.type == 'L') {
            pipe.previousPipe = getPipe(pipe.x, pipe.y - 1)
            pipe.nextPipe = getPipe(pipe.x + 1, pipe.y)
            pipe.leftGap = true
            pipe.bottomGap = true
        }

        if (pipe.type == 'J') {
            pipe.previousPipe = getPipe(pipe.x, pipe.y - 1)
            pipe.nextPipe = getPipe(pipe.x - 1, pipe.y)
            pipe.bottomGap = true
            pipe.rightGap = true
        }

        if (pipe.type == '7') {
            pipe.previousPipe = getPipe(pipe.x - 1, pipe.y)
            pipe.nextPipe = getPipe(pipe.x, pipe.y + 1)
            pipe.topGap = true
            pipe.rightGap = true
        }

        if (pipe.type == 'F') {
            pipe.previousPipe = getPipe(pipe.x + 1, pipe.y)
            pipe.nextPipe = getPipe(pipe.x, pipe.y + 1)
            pipe.topGap = true
            pipe.leftGap = true
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
        currentInfection = grid.flatten().filter { isEdge(it) && !it.navigated }
        currentInfection.filter { it.rightPipe== null }.forEach { it.infectFromRight() }
        currentInfection.filter { it.topPipe== null }.forEach { it.infectFromTop() }
        currentInfection.filter { it.bottomPipe == null }.forEach { it.infectFromBottom() }
        currentInfection.filter { it.leftPipe== null }.forEach { it.infectFromLeft() }
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
        var nextInfected = currentInfection.flatMap { it.spreadInfection() }
        currentInfection = nextInfected
    }

    fun getEnclosedTiles(): Int {
        grid.flatten().filter { !it.infected && !it.navigated && it.type!= 'S'}.forEach { it.enclosed = true }
        return grid.flatten().filter { it.enclosed }.size
    }
}