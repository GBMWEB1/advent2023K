import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day19Test {

    @Test
    fun createPart(){
        val part = Day19.Part.of("{x=787,m=2655,a=1222,s=2876}")
        assertEquals(787, part.x)
        assertEquals(2655, part.m)
        assertEquals(1222, part.a)
        assertEquals(2876, part.s)
    }

    @Test
    fun createRule(){
        val workflow = Day19.Workflow.of("qqz{s>2770:qs,m<1801:hdj,R}")
        assertEquals("qqz", workflow.id)
        assertEquals(3, workflow.rules.size)
        var part = Day19.Part.of("{x=787,m=1800,a=1222,s=2771}")
        assertEquals("qs", workflow.evaluate(part))

        part = Day19.Part.of("{x=787,m=1800,a=1222,s=2770}")
        assertEquals("hdj", workflow.evaluate(part))

        part = Day19.Part.of("{x=787,m=1801,a=1222,s=2770}")
        assertEquals("R", workflow.evaluate(part))

        val data = Util().readData("day19Sample.txt")
        val day19 = Day19(data, 10)
        assertEquals(5, day19.parts.size)
        assertEquals(11, day19.workflows.size)

        assertEquals(19114, day19.sumAccepted())
    }

    @Test
    fun rangeTest(){
        var range = Day19.Range()
        range.lessThan=5
        assertEquals(4,range.count())

        range = Day19.Range()
        range.greaterThan=3999
        assertEquals(1,range.count())

        range = Day19.Range()
        range.lessThan=2
        range.greaterThan=3999
        assertEquals(2,range.count())

        range = Day19.Range()
        range.lessThan=3
        range.greaterThan=1
        assertEquals(1,range.count())
    }

    @Test
    fun part1() {
        val data = Util().readData("day19.txt")
        val day19 = Day19(data, 10)
        assertEquals(331208, day19.sumAccepted())
    }

    @Test
    fun smallSample2() {
        val data = listOf(
            "in{a>9:bb,R}",
            "bb{m>9:cc,R}",
            "cc{x>9:dd,R}",
            "dd{s>9:ee,R}",
            "ee{A}"
        )
        val day19 = Day19(data, 10)
        assertEquals(1, day19.evaluateWorkflowOptions())
    }

    @Test
    fun sampleRule() {
        val data = Util().readData("day19Sample.txt")
        val day19 = Day19(data)
        assertEquals(167409079868000, day19.evaluateWorkflowOptions())
    }

    @Test
    fun part2() {
        val data = Util().readData("day19.txt")
        assertEquals(121464316215623, Day19(data).evaluateWorkflowOptions())
    }
}