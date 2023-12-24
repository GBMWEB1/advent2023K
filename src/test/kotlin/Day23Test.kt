import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day23Test {

    @Test
    fun miniGrid(){
        val sample = listOf(
        "#.#######",
        "#.......#",
        "#######.#"
        )
        var grid = Day23.Grid.of(sample, false)
        assertEquals(2,grid.nodes.size)

        val hikes = Day23.calculateHikes(grid, true)
        assertEquals(8, hikes)
    }

    @Test
    fun miniGrid2Hikes(){
        val sample = listOf(
            "#.#######",
            "#...>..#",
            "###.##.#",
            "###....#",
            "######.#",
            )
        var grid = Day23.Grid.of(sample, false)
        assertEquals(4,grid.nodes.size)
        val hikes = Day23.calculateHikes(grid, true)
        assertEquals(9, hikes)
    }

    @Test
    fun sample(){
        val data = Util().readData("day23Sample.txt")
        var grid = Day23.Grid.of(data, true)
        val hikes = Day23.calculateHikes(grid, true)
        assertEquals(94, hikes)
    }

    @Test
    fun part1() {
        val data = Util().readData("day23.txt")
        var grid = Day23.Grid.of(data)
        val hikes = Day23.calculateHikes(grid, true)
        assertEquals(2406,hikes)
    }

    @Test
    fun samplePart2(){
        val data = Util().readData("day23Sample.txt")
        var grid = Day23.Grid.of(data, false)
        val hikes = Day23.calculateHikes(grid, true)
        assertEquals(154, hikes)
    }

    @Test
    fun part2() {
        val data = Util().readData("day23.txt")
        var grid = Day23.Grid.of(data, false)
        val hikes = Day23.calculateHikes(grid, true)
        assertEquals(2406,hikes)
    }
}