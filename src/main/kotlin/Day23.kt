import java.sql.Driver

class Day23 {

    enum class Direction{
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    class Node(val x:Int = 0, val y: Int=0){
        // There may be a path either North, South, East, West
    }

    class Grid(val cells: List<String>, val slippySlopes: Boolean)
    {
        val nodes = calculateNodes()

        private fun calculateNodes(): List<Node> {
            var nodes = mutableListOf<Node>()

            nodes.add(Node(cells[0].indexOfFirst { it=='.' },0))
            for (row in cells.indices){
                for (col in cells[row].indices){
                    val cell = cells[row][col]
                    if (cell == '#'){
                        continue
                    }
                    val currentStep = Pair(col, row)
                    val directions = mutableListOf<Direction>()
                    val south = Pair(currentStep.first, currentStep.second+1)
                    if (canGo(south, Direction.SOUTH)){
                        directions.add(Direction.SOUTH)
                    }

                    if (canGo(Pair(currentStep.first, currentStep.second-1), Direction.NORTH)){
                        directions.add(Direction.NORTH)
                    }

                    val east = Pair(currentStep.first+1, currentStep.second)
                    if (canGo(east, Direction.EAST)){
                        directions.add(Direction.EAST)
                    }
                    val west = Pair(currentStep.first-1, currentStep.second)
                    if (canGo(west, Direction.WEST)){
                        directions.add(Direction.WEST)
                    }
                    if (directions.size>2){
                        nodes.add(Node(col, row))
                    }
                }
            }
            nodes.add(Node(cells.last().indexOfFirst { it=='.' },cells.size-1))
            return nodes
        }

        fun getEndCell(): Pair<Int, Int> {
            val x = cells.last().indexOfFirst { it=='.' }
            val y = cells.size-1
            return Pair(x,y)
        }

        fun canGo(cell: Pair<Int, Int>, direction: Direction): Boolean {

            // boundary checks first
            if (cell.first<0 || cell.second<0){
                return false
            }
            if (cell.first> cells[0].length-1){
                return false
            }
            if (cell.second> cells.size-1){
                return false
            }
            val land = cells[cell.second][cell.first]
            if (land=='#'){
                return false
            }
            if (land=='.'){
                return true
            }
            if (!slippySlopes){
                return true;
            }

            if (land == '>' && direction==Direction.EAST){
                return true
            }
            if (land == '<' && direction==Direction.WEST){
                return true
            }
            if (land == 'v' && direction==Direction.SOUTH){
                return true
            }
            if (land == '^' && direction==Direction.NORTH){
                return true
            }
            return false
        }

        companion object{
            fun of(data: List<String>, slippySlopes: Boolean= true): Grid {
                return Grid(data, slippySlopes)
            }
        }
    }

    class Hike(val steps: MutableList<Pair<Int,Int>>, val direction: Direction){
        fun getSteps(): Int {
            return steps.size-1
        }

        fun takeStep(grid:Grid) : List<Hike>{
            val currentStep = steps.last()
            if (atEnd(grid)){
                return listOf(this)
            }
            val hikes = mutableListOf<Hike>()
            val south = Pair(currentStep.first, currentStep.second+1)
            if (grid.canGo(south, Direction.SOUTH) && !steps.contains(south)){
                val newList = this.steps.toList().toMutableList()
                newList.add(south)
                hikes.add(Hike(newList, Direction.SOUTH))
            }

            val north = Pair(currentStep.first, currentStep.second-1)
            if (grid.canGo(north, Direction.NORTH) && !steps.contains(north)){
                val newList = this.steps.toList().toMutableList()
                newList.add(north)
                hikes.add(Hike(newList, Direction.NORTH))
            }

            val east = Pair(currentStep.first+1, currentStep.second)
            if (grid.canGo(east, Direction.EAST) && !steps.contains(east)){
                val newList = this.steps.toList().toMutableList()
                newList.add(east)
                hikes.add(Hike(newList, Direction.EAST))
            }
            val west = Pair(currentStep.first-1, currentStep.second)
            if (grid.canGo(west, Direction.WEST) && !steps.contains(west)){
                val newList = this.steps.toList().toMutableList()
                newList.add(west)
                hikes.add(Hike(newList, Direction.WEST))
            }
            // using the current location (i.e. the last step)
            return hikes
        }

        fun atEnd(grid: Grid): Boolean {
            return steps.last() == grid.getEndCell()
        }
    }

    companion object {
        fun calculateHikes(grid: Grid, prune: Boolean): Int {
            val firstStep = grid.cells[0].indexOfFirst { it=='.' }
            val initialHike = Hike(mutableListOf(Pair(firstStep,0)),Direction.SOUTH)
            var hikes = listOf(initialHike)
            var maxTimesAtEnd = 0;
            var times=0
            while ( hikes.isNotEmpty()){
                hikes = hikes.flatMap { it.takeStep(grid) }
                times++
                if (hikes.any { it.atEnd(grid) }) {
                    hikes = hikes.filter { !it.atEnd(grid) }
                    maxTimesAtEnd = times
                }
                if (hikes.size>60000){
                    println("big")
                }
                println(hikes.size)
            }
            return maxTimesAtEnd
        }

        fun getLongestHikes(hikes: List<Hike>): Int {
            return hikes.maxOf { it.getSteps() }
        }
    }
}