import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day1Test {

    @Test
    fun day1() {

        val data = Util().readData("fred.txt")
        assertEquals(2,data.size)
    }

    @Test
    fun addTwoNumbers() {
        val day1 = Day1()
        assertEquals(3, day1.addTwoNumbers(1, 2))
    }
}