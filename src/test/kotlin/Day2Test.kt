import Day2.Companion.getCubes
import Day2.Cube
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day2Test {

    private val games = listOf(
        "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
        "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
        "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
        "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
        "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
    )

    @Test
    fun samplePart1() {
        assertTrue(Day2.isGameValid(games[0]))
        assertTrue(Day2.isGameValid(games[1]))
        assertFalse(Day2.isGameValid(games[2]))
        assertFalse(Day2.isGameValid(games[3]))
        assertTrue(Day2.isGameValid(games[4]))

        assertEquals(Day2.sumValidGames(games), 8)
    }
    @Test
    fun part1() {
        val data = Util().readData("day2.txt")
        assertEquals(2632,Day2.sumValidGames(data))
    }

    @Test
    fun samplePart2() {
        assertEquals(listOf(4,1), games[0].getCubes(Cube.RED))
        assertEquals(listOf(2,2), games[0].getCubes(Cube.GREEN))
        assertEquals(listOf(3,6), games[0].getCubes(Cube.BLUE))

        assertEquals(48, Day2.getGamePower(games[0]))
        assertEquals(12, Day2.getGamePower(games[1]))
        assertEquals(1560, Day2.getGamePower(games[2]))
        assertEquals(630, Day2.getGamePower(games[3]))
        assertEquals(36, Day2.getGamePower(games[4]))

        assertEquals(2286, Day2.sumGamePowers(games))
    }

    @Test
    fun part2() {
        val data = Util().readData("day2.txt")
        assertEquals(69629,Day2.sumGamePowers(data))
    }

}