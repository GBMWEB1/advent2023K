import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day8Test {

    val sample = listOf<String>(
        "RL",
        "",
        "AAA = (BBB, CCC)",
        "BBB = (DDD, EEE)",
        "CCC = (ZZZ, GGG)",
        "DDD = (DDD, DDD)",
        "EEE = (EEE, EEE)",
        "GGG = (GGG, GGG)",
        "ZZZ = (ZZZ, ZZZ)"
    )

    @Test
    fun sample(){
    val map = Day8.of(sample);
        assertEquals(2, map.navigateToEnd());
    }
        @Test
    fun part1() {
        val data = Util().readData("day8.txt")
        val map = Day8.of(data);
        assertEquals(20659, map.navigateToEnd());
    }

    var samplePart2Data = listOf(
        "LR",
        "",
        "11A = (11B, XXX)",
        "11B = (XXX, 11Z)",
        "11Z = (11B, XXX)",
        "22A = (22B, XXX)",
        "22B = (22C, 22C)",
        "22C = (22Z, 22Z)",
        "22Z = (22B, 22B)",
        "XXX = (XXX, XXX)"
    )
    @Test
    fun samplePart2(){
        val map = Day8.of(samplePart2Data);
        assertEquals(listOf("11A", "22A"), map.getStartingNodes())
        assertEquals(6, map.navigateGhosts());
    }

    @Test
    fun part2() {
        val data = Util().readData("day8.txt")
        val map = Day8.of(data);
        assertEquals(15690466351717, map.navigateGhosts());
    }
}