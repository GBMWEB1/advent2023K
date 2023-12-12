import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day12Test {
    @Test
    fun sample1(){
//        assertEquals(2, Day12.getCombinations("??", 1))
        assertEquals(listOf(1,1,3), Day12.getGroups("#.#.###"))
        assertEquals(listOf(1,1,3), Day12.getGroups(".#...#....###."))

        assertEquals(1, Day12.getCombinations("???.###", listOf(1,1,3)))
        assertEquals(4, Day12.getCombinations(".??..??...?##.", listOf(1,1,3)))
        assertEquals(1, Day12.getCombinations("?#?#?#?#?#?#?#?", listOf(1,3,1,6)))
        assertEquals(1, Day12.getCombinations("????.#...#...", listOf(4,1,1)))
        assertEquals(4, Day12.getCombinations("????.######..#####.", listOf(1,6,5)))
        assertEquals(10, Day12.getCombinations("?###????????", listOf(3,2,1)))

        assertEquals(10, Day12.getCombinations("?###???????? 3,2,1"))
    }

    @Test // takes 48 seconds to run
    fun part1() {
        val data = Util().readData("day12.txt")
        assertEquals(7007, Day12.sumCombinations(data))
    }

    @Test // takes 48 seconds to run
    fun samplePart2() {
        assertEquals("???.###????.###????.###????.###????.###", Day12.foldPattern("???.###"))
        assertEquals(1, Day12.getCombinations("???.###????.###????.###????.###????.### 1,1,3,1,1,3,1,1,3,1,1,3,1,1,3"))
        assertEquals(1, Day12.getCombinationsFolded("???.### 1,1,3"))
        assertEquals(16384, Day12.getCombinationsFolded(".??..??...?##. 1,1,3"))
        assertEquals(1, Day12.getCombinationsFolded("?#?#?#?#?#?#?#? 1,3,1,6"))
        assertEquals(16, Day12.getCombinationsFolded("????.#...#... 4,1,1"))
        assertEquals(2500, Day12.getCombinationsFolded("????.######..#####. 1,6,5"))
        assertEquals(506250, Day12.getCombinationsFolded("?###???????? 3,2,1"))
    }
}