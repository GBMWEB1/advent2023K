import kotlin.math.min

class Day11(private val universe: List<String>) {
    var galaxies = Galaxy.from(universe)
    var pairs = calcPairs()
    var expandingRows = calcExpandingRows()
    var expandingColumns = calcExpandingCols()

    data class Galaxy(val id: Int, val x:Int, val y:Int){
        companion object {
            fun from(universe: List<String>) :List<Galaxy> {
                val galaxies = mutableListOf<Galaxy>()
                for (row in universe.indices){
                    for (col in universe[0].indices){
                        if (universe[row][col] == '#'){
                            galaxies.add(Galaxy(galaxies.size+1, col, row))
                        }
                    }
                }
                return galaxies
            }
        }
    }

    private fun calcPairs(): List<Pair<Int, Int>> {
        val pairs = mutableListOf<Pair<Int, Int>>()
        for (thisGalaxy in 1..<galaxies.size){
            for (otherGalaxy in thisGalaxy+1 .. galaxies.size){
                pairs.add(Pair(thisGalaxy, otherGalaxy))
            }
        }
        return pairs.toList()
    }

    private fun calcExpandingRows(): List<Int> {
        val blankRows = mutableListOf<Int>()
        universe.forEachIndexed { index, row ->
            if (row.all { it=='.' }){
                blankRows.add(index)
            }
        }
        return blankRows.toList()
    }

    private fun calcExpandingCols(): List<Int> {
        val blankColumns = mutableListOf<Int>()
        for (col in 0..<universe[0].length) {
            if (universe.map { it[col] }.all { it == '.' }){
                blankColumns.add(col)
            }
        }
        return blankColumns.toList()
    }

    // Part 1 and 2
    fun calculateShortestDistance(expandingSize: Long = 1): Long {
        return pairs
            .sumOf { pair -> calculateDistance(pair.first, pair.second, expandingSize) }
    }

    private fun getGalaxy(id: Int): Galaxy {
        return galaxies.find { it.id== id}!!
    }

    fun calculateDistance(thisGalaxyId: Int, otherGalaxyId: Int, expandingSize: Long = 1): Long{
        val thisGalaxy = getGalaxy(thisGalaxyId)
        val otherGalaxy= getGalaxy(otherGalaxyId)

        val minX = thisGalaxy.x.coerceAtMost(otherGalaxy.x)
        val maxX = thisGalaxy.x.coerceAtLeast(otherGalaxy.x)

        val minY = thisGalaxy.y.coerceAtMost(otherGalaxy.y)
        val maxY = thisGalaxy.y.coerceAtLeast(otherGalaxy.y)

        var crossedExpandingX=0L
        for (x in minX .. maxX){
            if (expandingColumns.contains(x)){
                crossedExpandingX++
            }
        }
        val expand = (expandingSize - 1).coerceAtLeast(1)
        crossedExpandingX *= (expand)

        var crossedExpandingY=0L
        for (y in minY .. maxY){
            if (expandingRows.contains(y)){
                crossedExpandingY++
            }
        }
        crossedExpandingY *= (expand)
        return (maxX + crossedExpandingX - minX) + (maxY + crossedExpandingY - minY)
    }

    fun display() {
       universe.forEach { println(it) }
    }
}