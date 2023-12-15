import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day15Test {
    @Test
    fun sample(){
        assertEquals(200, Day15.initSequence('H',0))
        assertEquals(153, Day15.initSequence('A',200))
        assertEquals(172, Day15.initSequence('S',153))
        assertEquals(52, Day15.initSequence('H',172))

        assertEquals(30, Day15.runStep("rn=1"))
        assertEquals(253, Day15.runStep("cm-"))
        assertEquals(97, Day15.runStep("qp=3"))
        assertEquals(47, Day15.runStep("cm=2"))

        val data = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7".split(",")
        assertEquals(1320, Day15.sumSequences(data))
    }

    @Test // takes 48 seconds to run
    fun part1() {
        val data = Util().readData("day15.txt")
        val lines = data[0].split(",")
        assertEquals(514025, Day15.sumSequences(lines))
    }

    // part2
    @Test
    fun part2Sample(){
        assertEquals(0, Day15.runStep("rn"))
        assertEquals(0, Day15.runStep("cm"))

        val boxes = Day15()
        boxes.processLense("rn=1")
        boxes.processLense("cm-")
        boxes.processLense("qp=3")
        boxes.processLense("cm=2")
        boxes.processLense("qp-")
        boxes.processLense("pc=4")
        boxes.processLense("ot=9")
        boxes.processLense("ab=5")
        boxes.processLense("pc-")
        boxes.processLense("pc=6")
        boxes.processLense("ot=7")
        boxes.display()
        assertEquals(145, boxes.sumFocusPower())
    }

    @Test
    fun part2() {
        val data = Util().readData("day15.txt")
        val lines = data[0].split(",")
        val boxes = Day15()
        lines.forEach { boxes.processLense(it) }
        assertEquals(244461, boxes.sumFocusPower())
    }
}