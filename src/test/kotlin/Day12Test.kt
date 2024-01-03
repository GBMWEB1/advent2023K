import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day12Test {
    @Test
    fun sample1GetCombinations(){
        assertEquals(listOf(1,1,3), Day12C.getGroups("#.#.###"))
        assertEquals(listOf(1,1,3), Day12C.getGroups(".#...#....###."))

        assertEquals(4, Day12C.getCombo(".??..??...?##.", listOf(1,1,3)))
        assertEquals(1, Day12C.getCombo("?#?#?#?#?#?#?#?", listOf(1,3,1,6)))
        assertEquals(1, Day12C.getCombo("????.#...#...", listOf(4,1,1)))
        assertEquals(4, Day12C.getCombo("????.######..#####.", listOf(1,6,5)))
        assertEquals(10, Day12C.getCombo("?###????????", listOf(3,2,1)))

        assertEquals(1, Day12C.getCombo("?", listOf(1)))
        assertEquals(2, Day12C.getCombo("??", listOf(1)))
        assertEquals(3, Day12C.getCombo("???", listOf(1)))
        assertEquals(4, Day12C.getCombo("????", listOf(1)))
        assertEquals(1, Day12C.getCombo("????", listOf(2,1)))
        assertEquals(3, Day12C.getCombo("?????", listOf(2,1)))
        assertEquals(6, Day12C.getCombo("??????", listOf(2,1)))
        assertEquals(10, Day12C.getCombo("???????", listOf(2,1)))
        assertEquals(10, Day12C.getCombo("?###????????", listOf(3,2,1)))

        assertEquals(10, Day12C.getCombinations("?###???????? 3,2,1"))
    }

    @Test
    fun part1() {
        val data = Util().readData("Day12.txt")
        assertEquals(7007, Day12C.sumCombinations(data))
    }

    @Test
    fun samplePart2() {
        assertEquals("???.###????.###????.###????.###????.###", Day12C.foldPattern("???.###"))
        assertEquals(16, Day12C.getCombinationsFolded("????.#...#... 4,1,1"))
        assertEquals(2500, Day12C.getCombinationsFolded("????.######..#####. 1,6,5"))
        assertEquals(16384, Day12C.getCombinationsFolded(".??..??...?##. 1,1,3"))
        assertEquals(1, Day12C.getCombinationsFolded("???.### 1,1,3"))
 
        assertEquals(1, Day12C.getCombinationsFolded("?#?#?#?#?#?#?#? 1,3,1,6"))
        assertEquals(506250, Day12C.getCombinationsFolded("?###???????? 3,2,1"))
    }

    @Test
    fun part2() {
        val data = Util().readData("Day12.txt")
        assertEquals(3476169006222, Day12C.sumCombinationsFolded(data))
    }
}