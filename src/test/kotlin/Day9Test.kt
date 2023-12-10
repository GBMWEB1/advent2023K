import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day9Test {
    @Test
    fun sample1(){
    val map = Day9.of("0 3 6 9 12 15")
        assertEquals(listOf(3,3,3,3,3), map.createRow(listOf(0,3,6,9,12,15)))
        assertEquals(18, map.sumNextValues())
    }

    @Test
    fun sample2(){
        val map = Day9.of("1 3 6 10 15 21")
        assertEquals(28, map.sumNextValues())
    }

    @Test
    fun sample3(){
        val map = Day9.of("10 13 16 21 30 45")
        assertEquals(68, map.sumNextValues())
    }

    @Test
    fun sample4(){
        val map = Day9.of("8 6 4 2 0 -2 -4 -6")
        assertEquals(-8, map.sumNextValues())
    }

    @Test
    fun sample5(){
        val map = Day9.of("-2 -4 -6")
        assertEquals(-8, map.sumNextValues())
    }

    @Test
    fun part1() {
        val data = Util().readData("day9.txt")
        assertEquals(1995001648, Day9.ofList(data).sumNextValues())
    }
    @Test
    fun samplePart2(){
        assertEquals(-3, Day9.of("0 3 6 9 12 15").sumFirstValue())
        assertEquals(0, Day9.of("1 3 6 10 15 21").sumFirstValue())
        assertEquals(5, Day9.of("10 13 16 21 30 45").sumFirstValue())
    }
    @Test
    fun part2() {
        val data = Util().readData("day9.txt")
        assertEquals(988, Day9.ofList(data).sumFirstValue())
    }
}