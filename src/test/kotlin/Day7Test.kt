import Day5.Range
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day7Test {

    @Test
    fun sample()
    {
        assertEquals(0,Day6.calcTotalDistance(0, 7))
        assertEquals(6,Day6.calcTotalDistance(1, 7))
        assertEquals(10,Day6.calcTotalDistance(2, 7))
        assertEquals(12,Day6.calcTotalDistance(3, 7))
        assertEquals(6,Day6.calcTotalDistance(6, 7))
        assertEquals(0,Day6.calcTotalDistance(7, 7))

        val race1 = Day6.calculateWinningNumbers(7, 9)
        val race2 = Day6.calculateWinningNumbers(15, 40)
        val race3 = Day6.calculateWinningNumbers(30, 200)

        val total = race1 * race2 * race3
        assertEquals(288, total)
    }

    @Test
    fun part1() {
        val race1 = Day6.calculateWinningNumbers(58, 434)
        val race2 = Day6.calculateWinningNumbers(81, 1041)
        val race3 = Day6.calculateWinningNumbers(96, 2219)
        val race4 = Day6.calculateWinningNumbers(76, 1218)
        val total = race1 * race2 * race3* race4
        assertEquals(1159152, total)
    }

    @Test
    fun part2sample() {
        assertEquals(71503, Day6.calculateWinningNumbers(71530, 940200))
    }
    @Test
    fun part2() {
        assertEquals(41513103, Day6.calculateWinningNumbers(58819676, 434104122191218L))
    }
}