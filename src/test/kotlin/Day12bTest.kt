import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Test {
    @Test
    fun sample1(){
        assertEquals(listOf(1,1,3), Day12B.getGroups("#.#.###"))
        assertEquals(listOf(1,1,3), Day12B.getGroups(".#...#....###."))

        assertEquals(4, Day12B.getCombo(".??..??...?##.", listOf(1,1,3)))
        assertEquals(1, Day12B.getCombo("?#?#?#?#?#?#?#?", listOf(1,3,1,6)))
        assertEquals(1, Day12B.getCombo("????.#...#...", listOf(4,1,1)))
        assertEquals(4, Day12B.getCombo("????.######..#####.", listOf(1,6,5)))
        assertEquals(10, Day12B.getCombo("?###????????", listOf(3,2,1)))
        assertEquals(10, Day12B.getCombinations2("?###???????? 3,2,1"))

        assertEquals(1, Day12B.getCombo("?", listOf(1)))
        assertEquals(2, Day12B.getCombo("??", listOf(1)))
        assertEquals(3, Day12B.getCombo("???", listOf(1)))
        assertEquals(4, Day12B.getCombo("????", listOf(1)))
        assertEquals(1, Day12B.getCombo("????", listOf(2,1)))
        assertEquals(3, Day12B.getCombo("?????", listOf(2,1)))
        assertEquals(6, Day12B.getCombo("??????", listOf(2,1)))
        assertEquals(10, Day12B.getCombo("???????", listOf(2,1)))
        assertEquals(10, Day12B.getCombo("?###????????", listOf(3,2,1)))
    }

    @Test
    fun part1() {
        val data = Util().readData("Day12.txt")
        assertEquals(7007, Day12B.sumCombinations2(data))
    }

    @Test // takes 48 seconds to run
    fun samplePart2() {
        assertEquals("???.###????.###????.###????.###????.###", Day12B.foldPattern("???.###"))
        assertEquals(16, Day12B.getCombinationsFolded("????.#...#... 4,1,1"))
        assertEquals(2500, Day12B.getCombinationsFolded("????.######..#####. 1,6,5"))
        assertEquals(16384, Day12B.getCombinationsFolded(".??..??...?##. 1,1,3"))
        assertEquals(1, Day12B.getCombinationsFolded("???.### 1,1,3"))
        assertEquals(1, Day12B.getCombinations2("???.###????.###????.###????.###????.### 1,1,3,1,1,3,1,1,3,1,1,3,1,1,3"))

        assertEquals(1, Day12B.getCombinationsFolded("?#?#?#?#?#?#?#? 1,3,1,6"))
        assertEquals(506250, Day12B.getCombinationsFolded("?###???????? 3,2,1"))
    }

    @Test
    fun part2() {
        val data = Util().readData("Day12.txt")
        assertEquals(7007, Day12B.getCombinationsFolded(data[5]))
    }
}