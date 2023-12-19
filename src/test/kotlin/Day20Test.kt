import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day20Test {

    @Test
    fun part1() {
        val data = Util().readData("day20.txt")
        val day19 = Day19(data, 10)
        assertEquals(331208, day19.sumAccepted())
    }
}