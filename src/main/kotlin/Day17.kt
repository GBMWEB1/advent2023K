class Day17 {

    enum class Direction{
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    enum class Orientation{
        LEFT,
        RIGHT,
        STRAIGHT
    }


    class Path(var direction: Direction,
               var row: Int = 0,
               var col: Int = 0,
               var count :Int =0,
               var forwards: Int =0
    ){

        companion object{
            fun clone(path: Path, orientation: Orientation): Path{

                val newPath = Path(
                    path.direction,
                    path.row,
                    path.col,
                    path.count,
                    path.forwards
                )
                newPath.changeDirection(orientation)
                return newPath
            }
        }

        fun changeDirection(orientation: Orientation){
            if (orientation == Orientation.STRAIGHT) {
               return
            }
            forwards=0

            if (orientation == Orientation.LEFT){
                direction = when (direction){
                    Direction.NORTH -> Direction.WEST
                    Direction.WEST ->  Direction.SOUTH
                    Direction.SOUTH -> Direction.EAST
                    Direction.EAST -> Direction.NORTH
                }
            }
            else if (orientation == Orientation.RIGHT){
                direction = when (direction){
                    Direction.NORTH -> Direction.EAST
                    Direction.EAST ->  Direction.SOUTH
                    Direction.SOUTH -> Direction.WEST
                    Direction.WEST -> Direction.NORTH
                }
            }

        }

        fun navigate(grid: Grid): List<Path> {
            val paths = mutableListOf<Path>()
            // options are forward, turn left or turn right
            val straightPath = clone(this, Orientation.STRAIGHT)
            if (straightPath.move(grid)){
                paths.add(straightPath)
            }

            if (this.forwards>=grid.minMoves){
                val leftPath = clone(this, Orientation.LEFT)
                if (leftPath.move(grid)){
                    paths.add(leftPath)
                }

                val rightPath = clone(this,Orientation.RIGHT)
                if (rightPath.move(grid)){
                    paths.add(rightPath)
                }
            }
            return paths
        }

        private fun move(grid: Grid): Boolean {
            var jump = 1
            if (forwards==0){
                jump = maxOf(grid.minMoves,jump)
            }
            forwards += jump
            if (forwards>grid.maxMoves){
                return false
            }
            if (direction==Direction.NORTH) {
                if (row-jump>=0) {
                    repeat(jump){
                        this.row--
                        count += grid.blocks[row][col]
                    }

                    return grid.isLowest(this)
                }
                return false
            }

            if (direction==Direction.SOUTH) {
                if (row+jump<=grid.blocks.size-1) {
                    repeat(jump){
                        this.row++
                        count += grid.blocks[row][col]
                    }
                    return grid.isLowest(this)
                }
                return false
            }

            if (direction==Direction.WEST) {
                if (col-jump>=0) {
                    repeat(jump){
                        this.col--
                        count += grid.blocks[row][col]
                    }
                    return grid.isLowest(this)
                }
                return false
            }

            if (direction==Direction.EAST) {
                if (col+jump<=grid.blocks[row].size-1) {
                    repeat(jump){
                        this.col++
                        count += grid.blocks[row][col]
                    }
                    return grid.isLowest(this)
                }
                return false
            }
            return false
        }
    }

    fun findBestPath(grid: Grid): Int {
        var paths = listOf(Path(Direction.EAST))
        while (paths.isNotEmpty()){
            paths = paths.flatMap { it.navigate(grid) }
            if (paths.size>10000){
                paths = paths.sortedBy { it.count }.take((paths.size/2))
            }
        }
        return grid.mins.last().last().getOverallLowest()
    }

    class Mins(size: Int){
        private var north = mutableListOf<Int>()
        private var south  = mutableListOf<Int>()
        private var west = mutableListOf<Int>()
        private var east = mutableListOf<Int>()

        init {
            for (x in 0..<size){
                north.add(Int.MAX_VALUE)
                south.add(Int.MAX_VALUE)
                west.add(Int.MAX_VALUE)
                east.add(Int.MAX_VALUE)
            }
        }

        fun getLowest(direction: Direction, forwards: Int): Int {
            return when (direction){
                Direction.EAST -> east[forwards-1]
                Direction.WEST -> west[forwards-1]
                Direction.NORTH -> north[forwards-1]
                Direction.SOUTH -> south[forwards-1]
            }
        }

        fun setLowest(direction: Direction, forwards: Int, lowest: Int){
            when (direction){
                Direction.EAST -> east[forwards-1] = lowest
                Direction.WEST -> west[forwards-1]  = lowest
                Direction.NORTH -> north[forwards-1] = lowest
                Direction.SOUTH -> south[forwards-1] = lowest
            }
        }

        fun getOverallLowest(): Int{
            return listOf(north.min(), south.min(), east.min(),west.min()).min()
        }
    }

    class Grid(val blocks: List<List<Int>>, var mins: List<MutableList<Mins>>, var minMoves: Int, var maxMoves: Int){
        fun isLowest(path: Path): Boolean {
            val lowestCollection = mins[path.row][path.col]
            val lowest = lowestCollection.getLowest(path.direction,path.forwards)
            if (lowest> path.count){
                lowestCollection.setLowest(path.direction, path.forwards, path.count)
                return true
            }
            return false
        }

        companion object{
            fun of(data: List<String>, minMoves: Int =0, maxMoves: Int =3): Grid{
                val blocks = data.map { it.map { c -> c.digitToInt()  } }
                val mins = data.map { it.map { Mins(maxMoves)  }.toMutableList() }

                return Grid(blocks, mins, minMoves,maxMoves)
            }
        }
    }
}