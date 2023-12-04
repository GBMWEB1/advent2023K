import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class Day4Test {

    private val sample = listOf(
    "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
    "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
    "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
    "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
    "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
    "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11",
    )

    @Test
    fun samplePart1(){
        assertEquals(8, Day4.getPoints(sample[0]))
        assertEquals(2, Day4.getPoints(sample[1]))
        assertEquals(2, Day4.getPoints(sample[2]))
        assertEquals(1, Day4.getPoints(sample[3]))
        assertEquals(0, Day4.getPoints(sample[4]))
        assertEquals(0, Day4.getPoints(sample[5]))
    }

    @Test
    fun samplePart2(){
        assertEquals(8, Day4.getPoints(sample[0]))
        assertEquals(2, Day4.getPoints(sample[1]))
        assertEquals(2, Day4.getPoints(sample[2]))
        assertEquals(1, Day4.getPoints(sample[3]))
        assertEquals(0, Day4.getPoints(sample[4]))
        assertEquals(0, Day4.getPoints(sample[5]))
    }
    @Test
    fun part1() {
        val data = Util().readData("day4.txt")
        assertEquals(26346,  Day4.sumScore(data))
    }

    @Test
    fun part2Sample() {
        assertEquals(30,  Day4.part2(sample))
    }

    @Test
    fun part2() {
        val data = Util().readData("day4.txt")
        assertEquals(8467762,  Day4.part2(data))
    }
}