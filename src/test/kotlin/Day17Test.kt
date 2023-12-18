import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day17Test {
    @Test
    fun sample(){
        val data = Util().readData("day17Sample.txt")
        val grid = Day17.Grid.of(data)
        assertEquals(102, Day17().findBestPath(grid))
    }


    @Test
    fun part1() {
        val data = Util().readData("day17.txt")
        val grid = Day17.Grid.of(data)

        assertEquals(635, Day17().findBestPath(grid))
    }

    @Test
    fun sample2(){
        val data = Util().readData("day17Sample.txt")
        val grid = Day17.Grid.of(data, 4, 10)

        assertEquals(94, Day17().findBestPath(grid))
    }

    @Test
    fun sample3(){
        val data = Util().readData("day17Sample2.txt")
        val grid = Day17.Grid.of(data, 4, 10)

        assertEquals(71, Day17().findBestPath(grid))
    }

    @Test
    fun part2() {
        val data = Util().readData("day17.txt")
        val grid = Day17.Grid.of(data, 4, 10)

        assertEquals(734, Day17().findBestPath(grid))
    }

}