import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day13Test {

    private var sample = listOf(
        "#.##..##.",
        "..#.##.#.",
        "##......#",
        "##......#",
        "..#.##.#.",
        "..##..##.",
        "#.#.##.#."
    )

    private var sample2 = listOf(
        "#...##..#",
        "#....#..#",
        "..##..###",
        "#####.##.",
        "#####.##.",
        "..##..###",
        "#....#..#",
    )
    @Test
    fun sample1(){
        val pattern= Day13.Pattern(sample)
        assertEquals(9, pattern.cols.size)
        assertEquals(7, pattern.rows.size)
        assertEquals(5, pattern.getTotal())
    }

    @Test
    fun sample2(){
        val pattern= Day13.Pattern(sample2)
        assertEquals(9, pattern.cols.size)
        assertEquals(7, pattern.rows.size)
        assertEquals(400, pattern.getTotal())
    }

    @Test
    fun sample(){
        val totalPattern = mutableListOf<String>()
        totalPattern.addAll(sample)
        totalPattern.add("")
        totalPattern.addAll(sample2)
        assertEquals(405, Day13.getTotal(totalPattern))
    }
    @Test // takes 48 seconds to run
    fun part1() {
        val data = Util().readData("day13.txt")
        assertEquals(35538, Day13.getTotal(data))
    }


    @Test
    fun sample1Part2(){
        val pattern= Day13.Pattern(sample)
        assertEquals(9, pattern.cols.size)
        assertEquals(7, pattern.rows.size)
        assertEquals(300, pattern.getTotal(1))
    }

    @Test
    fun sample2Part2(){
        val pattern= Day13.Pattern(sample2)
        assertEquals(9, pattern.cols.size)
        assertEquals(7, pattern.rows.size)
        assertEquals(100, pattern.getTotal(1))
    }

    @Test
    fun samplePart2(){
        val totalPattern = mutableListOf<String>()
        totalPattern.addAll(sample)
        totalPattern.add("")
        totalPattern.addAll(sample2)
        assertEquals(400, Day13.getTotal(totalPattern, 1))
    }

    @Test
    fun part2() {
        val data = Util().readData("day13.txt")
        assertEquals(30442, Day13.getTotal(data,1))
    }

}