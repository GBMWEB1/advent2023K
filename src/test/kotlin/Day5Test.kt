import Day5.Range
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day5Test {

    @Test
    fun sampleSmallPart1(){
        val smallSample = listOf("50 98 2",
                "52 50 48")
        val map = Day5.Mapping.fromLines(smallSample)
        assertEquals(81, map.findTarget(79))
        assertEquals(14, map.findTarget(14))
        assertEquals(57, map.findTarget(55))
        assertEquals(13, map.findTarget(13))
    }

    @Test
    fun samplePart1(){
        val data = Util().readData("day5Sample.txt")
        val day5 = Day5.fromList(data)

        assertEquals(82, day5.findLocation(79))
        assertEquals(43, day5.findLocation(14))
        assertEquals(86, day5.findLocation(55))
        assertEquals(35, day5.findLocation(13))

        assertEquals(35, day5.findLowestLocation())
    }

    @Test
    fun part1() {
        val data = Util().readData("day5.txt")
        val day5 = Day5.fromList(data)
        assertEquals(226172555, day5.findLowestLocation())
    }

    @Test
    fun part1Sample() {
        val data = Util().readData("day5Sample.txt")
        val day5 = Day5.fromList(data)
        assertEquals(46, day5.findLowestLocationAsSeedPair())
    }



    @Test
    fun sampleRangesSimple() {
        val smallSample = listOf(
            "15 5 2",
            "17 7 2"
        )
        val map = Day5.Mapping.fromLines(smallSample)

        assertEquals(listOf(Range(15,2)),map.findTargets(Range(5, 2)))
        assertEquals(listOf(Range(17,2)),map.findTargets(Range(7, 2)))

        assertEquals(listOf(Range(3,1)),map.findTargets(Range(3, 1)))
        assertEquals(listOf(Range(3,2)),map.findTargets(Range(3, 2)))
        assertEquals(listOf(Range(3,2),Range(15,1) ),map.findTargets(Range(3, 3)))
        assertEquals(listOf(Range(3,2),Range(15,2) ),map.findTargets(Range(3, 4)))
        assertEquals(listOf(Range(3,2),Range(15,2),Range(17,1)  ),map.findTargets(Range(3, 5)))
        assertEquals(listOf(Range(3,2),Range(15,2),Range(17,2)  ),map.findTargets(Range(3, 6)))
        assertEquals(listOf(Range(3,2),Range(15,2),Range(17,2),Range(9,1)   ),map.findTargets(Range(3, 7)))
        assertEquals(listOf(Range(3,2),Range(15,2),Range(17,2),Range(9,2)   ),map.findTargets(Range(3, 8)))

        assertEquals(listOf(Range(18,1)),map.findTargets(Range(8, 1)))
        assertEquals(listOf(Range(18,1),Range(9,1) ),map.findTargets(Range(8, 2)))
    }

    @Test
    fun part2Sample() {
        val data = Util().readData("day5Sample.txt")
        val day5 = Day5.fromList(data)
        assertEquals(46, day5.findLowestLocationAsSeedPair())
    }

    @Test
    fun part2() {
        val data = Util().readData("day5.txt")
        val day5 = Day5.fromList(data)
        assertEquals(47909639, day5.findLowestLocationAsSeedPair())
    }
}