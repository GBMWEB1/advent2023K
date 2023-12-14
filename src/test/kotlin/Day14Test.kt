import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day14Test {

    private var sample = listOf(
        "O....#....",
        "O.OO#....#",
        ".....##...",
        "OO.#O....O",
        ".O.....O#.",
        "O.#..O.#.#",
        "..O..#O..O",
        ".......O..",
        "#....###..",
        "#OO..#...."
    )
    @Test
    fun sample1(){
        val grid= Day14(sample.toMutableList())
        grid.tiltNorth(1)
        assertEquals("O.OO.#....", grid.grid[0])
        assertEquals("O...#....#", grid.grid[1])
        grid.tiltAllNorth()
        assertEquals(136, grid.getScore())
    }

    @Test // takes 48 seconds to run
    fun part1() {
        val data = Util().readData("day14.txt")
        var grid = Day14(data.toMutableList())
        grid.tiltAllNorth()
        assertEquals(113456,grid.getScore() )
    }

    @Test
    fun sample1Part2AllSouth(){
        var sample2 = listOf(
            "..O..#O..O",
            "........O.",
            "#....###..",
            "#OO..#...."
        )
        val grid= Day14(sample2.toMutableList())
        grid.tiltAllSouth()

        assertEquals(".....#....", grid.grid[0])
        assertEquals("......O...", grid.grid[1])
        assertEquals("#.O..###..", grid.grid[2])
        assertEquals("#OO..#..OO", grid.grid[3])
    }

    @Test
    fun sample1Part2AllEast(){
        var sample2 = listOf(
            "..O..#O..O",
            "........O.",
            "#....###..",
            "#OO..#...."
        )
        val grid= Day14(sample2.toMutableList())
        grid.tiltAllEast()

        assertEquals("....O#..OO", grid.grid[0])
        assertEquals(".........O", grid.grid[1])
        assertEquals("#....###..", grid.grid[2])
        assertEquals("#..OO#....", grid.grid[3])
    }

    @Test
    fun sample1Part2AllWest(){
        var sample2 = listOf(
            "..O..#O..O",
            "........O.",
            "#....###..",
            "#.OO.#...."
        )
        val grid= Day14(sample2.toMutableList())
        grid.tiltAllWest()

        assertEquals("O....#OO..", grid.grid[0])
        assertEquals("O.........", grid.grid[1])
        assertEquals("#....###..", grid.grid[2])
        assertEquals("#OO..#....", grid.grid[3])
    }

    @Test
    fun testReplaceRockEast(){
        var grid = Day14(listOf("....O#..OO").toMutableList());
        grid.replaceRockInColumn(4, 6, 0)
        assertEquals(".....#O.OO", grid.grid[0])
    }


    @Test
    fun sample1Part2(){
        val grid= Day14(sample.toMutableList())
        assertEquals(64, grid.findScore(1000000000))
    }

    @Test
    fun part2() {
        val data = Util().readData("day14.txt")
        val grid = Day14(data.toMutableList())
        assertEquals(118747, grid.findScore(1000000000))
    }

}