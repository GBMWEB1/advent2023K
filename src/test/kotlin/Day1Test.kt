import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day1Test {

    @Test
    fun part1And2() {

        val data = Util().readData("day1.txt")
        //assertEquals(56042,Day1.sumValues(data)) part1And2
        assertEquals(55358,Day1.sumValues(data)) //part2
    }

    @Test
    fun samplePart1GetValues() {

        val sampleData = listOf("1abc2", "pqr3stu8vwx", "a1b2c3d4e5f", "treb7uchet")

        assertEquals(12, Day1.getValues(sampleData[0]))
        assertEquals(38, Day1.getValues(sampleData[1]))
        assertEquals(15, Day1.getValues(sampleData[2]))
        assertEquals(77, Day1.getValues(sampleData[3]))

        assertEquals(142, Day1.sumValues(sampleData))
    }

    @Test
    fun samplePart2GetValues() {
        val day1 = Day1()

        val sampleData = listOf("two1nine", "eightwothree", "abcone2threexyz", "xtwone3four", "4nineeightseven2","zoneight234", "7pqrstsixteen")

        assertEquals(29, Day1.getValues(sampleData[0]))
        assertEquals(83, Day1.getValues(sampleData[1]))
        assertEquals(13, Day1.getValues(sampleData[2]))
        assertEquals(24, Day1.getValues(sampleData[3]))
        assertEquals(42, Day1.getValues(sampleData[4]))
        assertEquals(14, Day1.getValues(sampleData[5]))
        assertEquals(76, Day1.getValues(sampleData[6]))

        assertEquals(281, Day1.sumValues(sampleData))
    }

    fun part2() {
        val data = Util().readData("day1.txt")
        assertEquals(56042,Day1.sumValues(data))
    }
}