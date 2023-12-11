import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day11Test {
    @Test
    fun sample1(){
       val data = Util().readData("day11Sample.txt")
        var grid = Day11(data)
        assertEquals(2, grid.expandingRows.size)
        assertEquals(3, grid.expandingColumns.size)
        assertEquals(9, grid.galaxies.size)
        assertEquals(36, grid.pairs.size)

        assertEquals(9, grid.calculateDistance(5, 9))
        assertEquals(9, grid.calculateDistance(9, 5))
        assertEquals(36, grid.pairs.size)
        assertEquals(374, grid.calculateShortestDistance())
        grid.display()
    }

    @Test // takes 48 seconds to run
    fun part1() {
        val data = Util().readData("day11.txt")
        var grid = Day11(data)
        assertEquals(9684228, grid.calculateShortestDistance())
    }

    @Test
    fun samplePat2(){
        val data = Util().readData("day11Sample.txt")
        var grid = Day11(data)

        assertEquals(25, grid.calculateDistance(9, 5, 10))
        assertEquals(25, grid.calculateDistance(5, 9,10))
        assertEquals(1030, grid.calculateShortestDistance(10))

        assertEquals(8410, grid.calculateShortestDistance(100))
        grid.display()
    }

    @Test
    fun part2() {
        val data = Util().readData("day11.txt")
        var grid = Day11(data)
        assertEquals(483844716556, grid.calculateShortestDistance(1000000))
    }
}