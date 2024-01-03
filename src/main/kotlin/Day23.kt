import java.sql.Driver

class Day23 {

    enum class Direction{
        NORTH,
        SOUTH,
        EAST,
        WEST;

        fun opposite(): Direction {
            return when (this){
                NORTH -> SOUTH
                SOUTH -> NORTH
                EAST -> WEST
                WEST -> EAST
            }
        }
    }

    class Node(val x:Int = 0, val y: Int=0, val directions: List<Direction>){
        val distances : MutableMap<Direction, Pair<Node, Int>> = mutableMapOf()
        fun recordDistance(direction: Direction, steps: Int, endNode: Node) {
            distances[direction] = Pair(endNode, steps)
        }

        fun getAvailableDirections(): List<Direction> {
            return directions.filter { !distances.containsKey(it) }
        }
    }

    class Grid(val cells: List<String>, val slippySlopes: Boolean)
    {
        val nodes = calculateNodes()

        private fun calculateNodes(): List<Node> {
            var nodes = mutableListOf<Node>()

            nodes.add(Node(cells[0].indexOfFirst { it=='.' },0, listOf(Direction.SOUTH)))
            for (row in cells.indices){
                for (col in cells[row].indices){
                    if (col==11 && row==3){
                        println()
                    }
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
                        nodes.add(Node(col, row, directions))
                    }
                }
            }
            nodes.add(Node(cells.last().indexOfFirst { it=='.' },cells.size-1, listOf(Direction.NORTH)))
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

    class Hike(
        val startingNode : Node,
        val startingDirection : Direction,
        var steps: MutableList<Pair<Int,Int>>,
        var lastDirection: Direction){

        fun getSteps(): Int {
            return steps.size-1
        }

        fun takeStep(grid:Grid) : List<Hike>{
            val currentStep = steps.last()
            val hikes = mutableListOf<Hike>()

            val south = Pair(currentStep.first, currentStep.second+1)
            val north = Pair(currentStep.first, currentStep.second-1)
            val east = Pair(currentStep.first+1, currentStep.second)
            val west = Pair(currentStep.first-1, currentStep.second)

            // Check if I am at a node
            val node = grid.nodes.find { it.x == currentStep.first && it.y== currentStep.second }
            if (node!= null&& currentStep.second>0){
                //if we are at a node, record the steps from the last node to this node
                this.startingNode.recordDistance(startingDirection, getSteps(), node)
                node.recordDistance(lastDirection.opposite(), getSteps(), startingNode)
                steps = mutableListOf();
                // for this node, set in the directions not yet done
                val availableDirection = node.getAvailableDirections()
                if (availableDirection.contains(Direction.SOUTH)){
                    val steps = mutableListOf(currentStep,south)
                    hikes.add(Hike(node, Direction.SOUTH, steps, Direction.SOUTH))
                }
                if (availableDirection.contains(Direction.NORTH)){
                    val steps = mutableListOf(currentStep, north)
                    hikes.add(Hike(node, Direction.NORTH, steps, Direction.NORTH))
                }
                if (availableDirection.contains(Direction.EAST)){
                    val steps = mutableListOf(currentStep,east)
                    hikes.add(Hike(node, Direction.EAST, steps, Direction.EAST))
                }
                if (availableDirection.contains(Direction.WEST)){
                    val steps = mutableListOf(currentStep,west)
                    hikes.add(Hike(node, Direction.WEST, steps, Direction.WEST))
                }
            } else{
                hikes.add(this)
                if (grid.canGo(south, Direction.SOUTH) && !steps.contains(south)){
                    steps.add(south)
                    lastDirection = Direction.SOUTH
                }
                if (grid.canGo(north, Direction.NORTH) && !steps.contains(north)){
                    steps.add(north)
                    lastDirection = Direction.NORTH
                }
                if (grid.canGo(east, Direction.EAST) && !steps.contains(east)){
                    steps.add(east)
                    lastDirection = Direction.EAST
                }
                if (grid.canGo(west, Direction.WEST) && !steps.contains(west)){
                    steps.add(west)
                    lastDirection = Direction.WEST
                }
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
            val initialNode = grid.nodes.first()
            val initialHike = Hike(initialNode, Direction.SOUTH, mutableListOf(Pair(initialNode.x, initialNode.y)), Direction.SOUTH)
            var hikes = listOf(initialHike)
            while ( hikes.isNotEmpty()){
                hikes = hikes.flatMap { it.takeStep(grid) }
            }
            return 0
        }

        fun getLongestHikes(hikes: List<Hike>): Int {
            return hikes.maxOf { it.getSteps() }
        }
    }
}