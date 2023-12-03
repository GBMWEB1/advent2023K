import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class Day3Test {

    private val games = listOf(
        "467..110..",
        "...*......",
        "..35...633",
        "......%...",
        "617*......",
        ".....+.58.",
        "..592.....",
        "......755.",
        "...$.*....",
        ".664.598..",
    )
    @Test
    fun debugDigits() {
        val data = Util().readData("day3.txt")

        val digits = mutableSetOf<Char>()
        val chars = mutableSetOf<Char>()
        data.forEach { row ->
            row.forEach { bit -> if (!bit.isDigit()) digits.add(bit) }
            row.forEach { bit -> chars.add(bit) }
        }
        println(digits)
        println(chars)
    }

    @Test
    fun samplePart1() {
       assertEquals(4361, Day3(games).sumValidGames())

        assertEquals(0, Day3(listOf(
            "....",
            ".56.",
            "....")).sumValidGames(
        ))
        assertEquals(/* expected = */ 56, /* actual = */
            Day3(listOf(
            "..-.",
            ".56.",
            "....")).sumValidGames(
        ))
        assertEquals(/* expected = */ 56, /* actual = */ Day3(listOf(
            "..-",
            "..56",
            "....")).sumValidGames(
        ))
    }
    @Test
    fun part1() {
        val data = Util().readData("day3.txt")
        assertEquals(560670, Day3(data).sumValidGames())
    }

    // Part 2
    @Test
    fun part(){
        val part = Day3.Part(100, 3, 2)
        // same row
        assertFalse(part.isClose(3,0 ))
        assertTrue(part.isClose(3,1 ))
        assertTrue(part.isClose(3,5 ))
        assertFalse(part.isClose(3,6 ))

        // row before
        assertFalse(part.isClose(2,0 ))
        assertTrue(part.isClose(2,1 ))
        assertTrue(part.isClose(2,2 ))
        assertTrue(part.isClose(2,3 ))
        assertTrue(part.isClose(2,4 ))
        assertTrue(part.isClose(2,5 ))
        assertFalse(part.isClose(2,6 ))

        // two row before
        assertFalse(part.isClose(1,1 ))

        // row after
        assertFalse(part.isClose(4,0 ))
        assertTrue(part.isClose(4,1 ))
        assertTrue(part.isClose(4,2 ))
        assertTrue(part.isClose(4,3 ))
        assertTrue(part.isClose(4,4 ))
        assertTrue(part.isClose(4,5 ))
        assertFalse(part.isClose(4,6 ))

        // two row after
        assertFalse(part.isClose(5,1 ))
    }

    @Test
    fun extractParts() {
        val parts = Day3.Part.extractPartsFromLine(1, "467..114..")
        assertEquals(2, parts.size)
        assertEquals(467, parts[0].value)
        assertEquals(1, parts[0].row)
        assertEquals(0, parts[0].startCol)

        assertEquals(114, parts[1].value)
        assertEquals(1, parts[1].row)
        assertEquals(5, parts[1].startCol)

        assertEquals(2, parts.size)
    }

    @Test
    fun sampleGearsPart2() {
        assertEquals(467835, Day3(games).getGears().sum())
    }

    @Test
    fun part2() {
        val data = Util().readData("day3.txt")
        assertEquals(91622824,  Day3(data).getGears().sum())
    }
}