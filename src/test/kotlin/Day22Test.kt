import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day22Test {


    @Test
    fun sample(){
        val sample = listOf(
            "broadcaster -> a, b, c",
            "%a -> b",
            "%b -> c",
            "%c -> inv",
            "&inv -> a"
        )
        var modules = Day20.createModules(sample)
        var counts = Day20.pressButton(modules, 1)
        assertEquals(8, counts.lowCount)
        assertEquals(4, counts.highCount)

        modules = Day20.createModules(sample)
        counts = Day20.pressButtonTimes(modules,1000)
        assertEquals(8000, counts.lowCount)
        assertEquals(4000, counts.highCount)
    }

    @Test
    fun part2() {
        val data = Util().readData("day20.txt")
        val modules = Day20.createModules(data)

        modules.watchers.add(Day20.Watch("tx", Day20.PULSE_STRENGTH.HIGH))
        modules.watchers.add(Day20.Watch("gc", Day20.PULSE_STRENGTH.HIGH))
        modules.watchers.add(Day20.Watch("kp", Day20.PULSE_STRENGTH.HIGH))
        modules.watchers.add(Day20.Watch("vg", Day20.PULSE_STRENGTH.HIGH))
        Day20.pressButtonTimes(modules,10000)
        assertEquals(238593356738827, modules.findAllHit())
    }
}