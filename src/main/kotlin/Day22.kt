class Day22(val bricks: List<Brick>) {
    fun canDrop(brick: Day22.Brick): Boolean {
        if (brick.start[2]==1){
            return false
        }
        val otherBricks = bricks.dropWhile { it==brick }
        val justBelow = otherBricks.filter { it.end[2] +1 == brick.start[2] }
        if (justBelow.any { it.interest(brick) }){
            return false
        }
        return true
    }

    fun settle() {
        val sortedBricks = bricks.sortedBy { it.start[2]}
        sortedBricks.forEach { brick->
            while(canDrop(brick)){
                brick.drop()
            }
        }
    }

    fun linkBricks() {
        // sorted by the tops of all the bricks
        val sortedBricks = bricks.sortedBy { it.end[2]}

        sortedBricks.forEach { brick->
            val layerAbove = bricks.filter { it.start[2] == brick.end[2]+1 }
            val supports = layerAbove.filter { it.interest(brick) }
            brick.supports = supports
            supports.forEach {
                it.supportedBy.add(brick)
            }
        }
    }

    fun countBricksToBeDisintigrated(): Int {
        return bricks.count { brick -> canBeRemoved(brick) }
    }

    private fun canBeRemoved(brick: Brick): Boolean {
        // The brick can be removed if
        // -- it supports more than one brick, and each brick has more one than one supporting by bricks
        return brick.supports.isEmpty() || brick.supports.all {
            it.supportedBy.size>1
        }

    }

    fun countCascadingBricks(): Int {
        return bricks.sumOf { countCascadingBrick(it) }
    }

    private fun countCascadingBrick(brick: Day22.Brick): Int {
        if (brick.supports.size==0){
            return 0
        }
        var bricksCollapsed = mutableSetOf<Brick>()
        var bricksToCollapse = brick.cascadeDrop(bricksCollapsed)
        while(bricksToCollapse.size>0){
            bricksToCollapse = bricksToCollapse.flatMap { it.cascadeDrop(bricksCollapsed) }
            bricksToCollapse = bricksToCollapse.sortedBy { it.start[2] }
        }
        return bricksCollapsed.size
    }


    // Brick has start[x,y,z]
    // And end [x,y,z]

    // settling the bricks
    // starting from z
    // find any blocks that finish one below.
    // If not the drop, by 1
    // For each found, if any intersect the z and y. Then drop
    // repeat the process for each brick.

    // Once settled
    // iterate through each brick - ordered by end z
    // find any bricks that start z+1 and match the x and y footprint, if they do - add to either side:
    // supports and supportedBy

    // find all the bricks that are supported by more than one brick

    class Brick(val start: MutableList<Int>,val end: MutableList<Int>){

        var supports: List<Brick> = listOf()
        var supportedBy: MutableList<Brick> = mutableListOf()

        val cubes = buildCubes()
        fun buildCubes(): List<Pair<Int, Int>> {
            // intersect if there is any cross over in x and y
            val tempCubes = mutableListOf<Pair<Int,Int>>()
            for (x in start[0]..end[0]){
                for (y in start[1]..end[1]){
                    tempCubes.add(Pair(x,y))
                }
            }
            return tempCubes.toList()
        }

        fun interest(brick: Brick): Boolean {
            return brick.cubes.intersect(this.cubes).size>0
        }

        fun drop(){
            start[2] = start[2]-1
            end[2] = end[2]-1
        }

        fun cascadeDrop(bricksCollapsed: MutableSet<Brick>): List<Brick>{
            if (supportedBy.all { bricksCollapsed.contains(it) }){
                bricksCollapsed.add(this)
                return supports
            }
            return listOf()
        }


        fun getAllSupports(): Set<Brick>{
            val childSupports =  supports.flatMap { it.getAllSupports() }.toMutableSet()
            childSupports.add(this)
            return childSupports
        }


        companion object{
            fun of (data: List<String>): List<Brick>{
                return data.map { ofLine(it) }
            }

            fun ofLine(it: String) : Brick {
                val startSequence = it.substringBefore("~").split(",").map { it.toInt() }
                val endSequence = it.substringAfter("~").split(",").map { it.toInt() }
                return Brick(startSequence.toMutableList(), endSequence.toMutableList())
            }
        }
    }

}