import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day1Test {

    @Test
    fun day1() {

        val data = Util().readData("fred.txt")
        assertEquals(2,data.size)
    }
}