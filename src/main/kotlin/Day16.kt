
class Day16 {

   class Grid(val tiles: List<List<Tile>>,
              var initBean: Beam = Beam(0, 0, Direction.EAST)){
       var beams = listOf(initBean)
       init {
           tiles.flatten().forEach { it.clear() }
       }
       fun display() {
           tiles.forEach { row ->
               println()
               row.forEach {
                   it.display()
               }
           }
       }

       fun getTile(row: Int, col: Int): Tile {
           return tiles[row][col]
       }
       fun navigateToEnd() {
           this.beams = this.beams.filter {
               getTile(it.row, it.col).shouldNavigate(it)
           }

           while (this.beams.size>0){
               this.beams = beams.flatMap { it.navigate(this) }.toMutableList()
               this.beams = this.beams.filter {
                   getTile(it.row, it.col).shouldNavigate(it)
               }
           }
       }

       fun clear() {
           tiles.forEach { row ->
               println()
               row.forEach {
                  it.clear()
               }
           }
       }

       fun displayEnergy() {
           tiles.forEach { row ->
               println()
               row.forEach {
                   if (it.previous.size==0){
                       print(".")
                   } else{
                       print("#")
                   }
               }
           }
       }

       fun getEnergyCount(): Int {
           return tiles.flatten().count { it.previous.size>0 }
       }


       companion object{

           fun from(list: List<String>, initBean: Beam = Beam(0, 0, Direction.EAST)): Grid {
               val grid = mutableListOf<List<Tile>>()
               for (row in list.indices){
                   var rowList = mutableListOf<Tile>()
                   for (col in list[row].indices){
                       rowList.add(Tile.fromChar(list[row][col], row, col))
                   }
                   grid.add(rowList.toList())
               }
               return Grid(grid.toList(), initBean)
           }
       }
   }

    enum class TileType(val bit:Char){
        SPACE('.'),
        MIRROR_UP('/'),
        MIRROR_DOWN('\\'),
        SPLIT_HORIZONTAL('-'),
        SPLIT_VERTICAL('|'),
    }

    enum class Direction{
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    data class Beam(var row : Int, val col: Int, var direction: Direction){
        fun navigate(grid: Grid): List<Beam> {
            val tile = grid.getTile(row, col)
            val tileType = tile.tileType
            var newBeans = mutableListOf<Beam>()

            // for each beam
            if (tileType == TileType.SPLIT_VERTICAL) {
                if (direction == Direction.EAST || direction == Direction.WEST) {
                    if (row > 0) {
                        newBeans.add(Beam(row-1, col, Direction.NORTH))
                    }
                    if (row < grid.tiles.size - 1) {
                        newBeans.add(Beam(row+1, col, Direction.SOUTH))
                    }
                } else if (direction == Direction.NORTH) {
                    if (row > 0) {
                        newBeans.add(Beam(row - 1, col, Direction.NORTH))
                    }
                } else if (direction == Direction.SOUTH) {
                    if (row < grid.tiles.size - 1) {
                        newBeans.add(Beam(row + 1,col, Direction.SOUTH))
                    }
                }
            }
            if (tileType == TileType.SPLIT_HORIZONTAL) {
                if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    if (col > 0) {
                        newBeans.add(Beam(row,col - 1, Direction.WEST))
                    }
                    if (col < grid.tiles[row].size - 1) {
                        newBeans.add(Beam(row,col + 1,Direction.EAST))
                    }
                } else if (direction == Direction.WEST) {
                    if (col > 0) {
                        newBeans.add(Beam(row, col - 1,Direction.WEST))
                    }
                } else if (direction == Direction.EAST) {
                    if (col < grid.tiles[row].size - 1) {
                        newBeans.add(Beam(row, col + 1, Direction.EAST))
                    }
                }
            }
            if (tileType == TileType.SPACE) {
                when (direction) {
                    Direction.NORTH -> {
                        if (row > 0) {
                            newBeans.add(Beam(row - 1,col, Direction.NORTH))
                        }
                    }
                    Direction.SOUTH -> {
                        if (row < grid.tiles.size - 1) {
                            newBeans.add(Beam(row + 1,col,Direction.SOUTH))
                        }
                    }

                    Direction.WEST -> {
                        if (col > 0) {
                            newBeans.add(Beam(row,col - 1,Direction.WEST))
                        }
                    }

                    Direction.EAST -> {
                        if (col < grid.tiles[row].size - 1) {
                            newBeans.add(Beam(row,col + 1,Direction.EAST))
                        }
                    }
                }
            }
            if (tileType == TileType.MIRROR_UP) {
                when (direction) {
                    Direction.NORTH -> {
                        if (col < grid.tiles[row].size - 1) {
                            newBeans.add(Beam(row,col + 1,Direction.EAST))
                        }
                    }
                    Direction.SOUTH -> {
                        if (col > 0) {
                            newBeans.add(Beam(row,col - 1,Direction.WEST))
                        }
                    }

                    Direction.WEST -> {
                        if (row < grid.tiles.size - 1) {
                            newBeans.add(Beam(row + 1,col,Direction.SOUTH))
                        }
                    }

                    Direction.EAST -> {
                        if (row > 0) {
                            newBeans.add(Beam(row - 1,col,Direction.NORTH))
                        }
                    }
                }
            }
            if (tileType == TileType.MIRROR_DOWN) {
                when (direction) {
                    Direction.NORTH -> {
                        if (col > 0) {
                            newBeans.add(Beam(row, col - 1, Direction.WEST))
                        }
                    }

                    Direction.SOUTH -> {
                        if (col < grid.tiles[row].size - 1) {
                            newBeans.add(Beam(row, col + 1, Direction.EAST))
                        }
                    }

                    Direction.WEST -> {
                        if (row > 0) {
                            newBeans.add(Beam(row - 1, col, Direction.NORTH))
                        }
                    }

                    Direction.EAST -> {
                        if (row < grid.tiles.size - 1) {
                            newBeans.add(Beam(row + 1, col, Direction.SOUTH))
                        }
                    }
                }
            }
            return newBeans
        }
    }
    class Tile(val tileType: TileType, val row: Int, val col: Int){
        val previous = mutableListOf<Beam>()

        fun display() {
            if (tileType==TileType.SPACE){
                if (previous.size==1){
                    when (previous[0].direction){
                        Direction.EAST -> print(">")
                        Direction.WEST -> print("<")
                        Direction.NORTH -> print("^")
                        Direction.SOUTH -> print("v")
                    }
                } else if (previous.size==0){
                    print(tileType.bit)
                }
            } else{
                print(tileType.bit)
            }
        }

        fun recordVisit(beam: Beam) {
            previous.add(beam)
        }

        fun clear() {
            previous.clear()
        }

        fun shouldNavigate(beam: Beam): Boolean {
            if (previous.any { it.direction == beam.direction }){
                return false;
            }
            previous.add(beam)
            return true;
        }

        companion object {
            fun fromChar(cha: Char, row: Int, col: Int): Tile {
                val tileType = when (cha){
                    '.' -> TileType.SPACE
                    '/' -> TileType.MIRROR_UP
                    '\\' -> TileType.MIRROR_DOWN
                     '-' -> TileType.SPLIT_HORIZONTAL
                     '|' -> TileType.SPLIT_VERTICAL
                    else -> throw Error("Invalid $cha")
                }
                return Tile(tileType, row ,col)
            }
        }
    }

    companion object {
        fun findHighestEnergy(list: List<String>): Int {
            val tiles = mutableListOf<List<Tile>>()
            for (row in list.indices){
                var rowList = mutableListOf<Tile>()
                for (col in list[row].indices){
                    rowList.add(Tile.fromChar(list[row][col], row, col))
                }
                tiles.add(rowList.toList())
            }
            var max = 0
            // top row
            for (col in 0.. tiles[0].size-1){
                var grid = Grid(tiles, Beam(0,col, Direction.SOUTH))
                grid.navigateToEnd()
                max = maxOf(max,grid.getEnergyCount())

                grid = Grid(tiles, Beam(tiles.size-1,col, Direction.NORTH))
                grid.navigateToEnd()
                max = maxOf(max,grid.getEnergyCount())
            }

            for (row in 0.. tiles.size-1){
                var grid = Grid(tiles, Beam(row,0, Direction.EAST))
                grid.navigateToEnd()
                max = maxOf(max,grid.getEnergyCount())

                grid = Grid(tiles, Beam(row,tiles[0].size-1, Direction.WEST))
                grid.navigateToEnd()
                max = maxOf(max,grid.getEnergyCount())
            }
            return max
        }
    }
}