import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day9Test {
    @Test
    fun sample1(){
    val map = Day9.of("0 3 6 9 12 15")
        assertEquals(listOf(3,3,3,3,3), map.createRow(mutableListOf(0,3,6,9,12,15)))
        assertEquals(18, map.getFirstHistory());
    }

    @Test
    fun sample2(){
        val map = Day9.of("1 3 6 10 15 21")
        assertEquals(28, map.getFirstHistory());
    }

    @Test
    fun sample3(){
        val map = Day9.of("10 13 16 21 30 45")
        assertEquals(68, map.getFirstHistory());
    }

    // test 8 6 4 2 0 -2 -4 -6 (sequences going down)
    @Test
    fun sample4(){
        val map = Day9.of("8 6 4 2 0 -2 -4 -6")
        assertEquals(-8, map.getFirstHistory());
    }

    @Test
    fun sample5(){
        val map = Day9.of("-2 -4 -6")
        assertEquals(-8, map.getFirstHistory());
    }

    @Test
    fun part1() {
        val data = Util().readData("day9.txt")
        val map = Day9.ofList(data);
        assertEquals(1995001648, map.sumHistory());
    }
    @Test
    fun samplePart2(){
        assertEquals(-3, Day9.of("0 3 6 9 12 15").sumLeftMostHistory());
        assertEquals(0, Day9.of("1 3 6 10 15 21").sumLeftMostHistory());
        assertEquals(5, Day9.of("10 13 16 21 30 45").sumLeftMostHistory());
    }
    @Test
    fun part2() {
        val data = Util().readData("day9.txt")
        val map = Day9.ofList(data);
        assertEquals(988, map.sumLeftMostHistory());
    }
}