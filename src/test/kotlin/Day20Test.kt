import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day20Test {

    @Test
    fun testBroadcaster(){
        val module = Day20.BroadCaster("broadcaster", listOf("A","B"))
        val pulses = module.receive(Day20.Pulse("test", Day20.PULSE_STRENGTH.HIGH, "broadcaster"))
        assertEquals(2, pulses.size)
        assertEquals(Day20.PULSE_STRENGTH.HIGH,pulses[0].strength)
        assertEquals("A",pulses[0].destination)
        assertEquals(Day20.PULSE_STRENGTH.HIGH,pulses[1].strength)
        assertEquals("B",pulses[1].destination)
    }

    //Flip-flop modules (prefix %) are either on or off; they are initially off.
    // If a flip-flop module receives a high pulse, it is ignored and nothing happens.
    // However, if a flip-flop module receives a low pulse, it flips between on and off.
    // If it was off, it turns on and sends a high pulse. If it was on, it turns off and sends a low pulse.
    @Test
    fun testFlipFlop(){
        val module = Day20.FlipFlop("id", listOf("A","B"))
        assertEquals(false, module.on)
        var pulses = module.receive(Day20.Pulse("test",Day20.PULSE_STRENGTH.HIGH, "id"))
        assertEquals(false, module.on)
        assertEquals(true, pulses.isEmpty())

        // Send a low pulse
        pulses = module.receive(Day20.Pulse("test", Day20.PULSE_STRENGTH.LOW, "id"))
        assertEquals(true, module.on)
        assertEquals(2, pulses.size)
        assertEquals(Day20.PULSE_STRENGTH.HIGH,pulses[0].strength)
        assertEquals("A",pulses[0].destination)
        assertEquals(Day20.PULSE_STRENGTH.HIGH,pulses[1].strength)
        assertEquals("B",pulses[1].destination)

        // Send another pulse
        pulses = module.receive(Day20.Pulse("test",Day20.PULSE_STRENGTH.LOW, "id"))
        assertEquals(false, module.on)
        assertEquals(2, pulses.size)
        assertEquals(Day20.PULSE_STRENGTH.LOW,pulses[0].strength)
        assertEquals("A",pulses[0].destination)
        assertEquals(Day20.PULSE_STRENGTH.LOW,pulses[1].strength)
        assertEquals("B",pulses[1].destination)

    }

    //Conjunction modules (prefix &) remember the type of the most recent pulse received from each of their connected input modules;
    // they initially default to remembering a low pulse for each input.
    // When a pulse is received, the conjunction module first updates its memory for that input.
    // Then, if it remembers high pulses for all inputs, it sends a low pulse; otherwise, it sends a high pulse.
    @Test
    fun testConjunction(){
        val module = Day20.Conjunction("id", listOf("C","D"))
        module.setInputs(listOf("A","B"))
        var pulses = module.receive(Day20.Pulse("A", Day20.PULSE_STRENGTH.HIGH, "id"))
        assertEquals(module.memory["A"], Day20.PULSE_STRENGTH.HIGH)
        assertEquals(2, pulses.size)
        assertEquals(Day20.PULSE_STRENGTH.HIGH,pulses[0].strength)
        assertEquals("C",pulses[0].destination)
        assertEquals(Day20.PULSE_STRENGTH.HIGH,pulses[1].strength)
        assertEquals("D",pulses[1].destination)

        pulses = module.receive(Day20.Pulse("B", Day20.PULSE_STRENGTH.HIGH, "id"))
        assertEquals(2, pulses.size)
        assertEquals(Day20.PULSE_STRENGTH.LOW,pulses[0].strength)
        assertEquals("C",pulses[0].destination)
        assertEquals(Day20.PULSE_STRENGTH.LOW,pulses[1].strength)
        assertEquals("D",pulses[1].destination)
    }

    @Test
    fun testCreateModules(){

        val sample = listOf(
            "broadcaster -> a, b, c",
            "%a -> b",
            "%b -> c",
            "%c -> inv",
            "&inv -> a"
        )
        val modules = Day20.createModules(sample).mods
        assertEquals(Day20.BroadCaster::class, modules[0]::class)
        assertEquals("broadcaster", (modules[0] as Day20.BroadCaster).id )
        assertEquals(listOf("a","b","c"), (modules[0] as Day20.BroadCaster).destinations )

        assertEquals(Day20.FlipFlop::class, modules[1]::class)
        assertEquals("a", (modules[1] as Day20.FlipFlop).id )
        assertEquals(listOf("b"), (modules[1] as Day20.FlipFlop).destinations )

        assertEquals(Day20.FlipFlop::class, modules[2]::class)
        assertEquals("b", (modules[2] as Day20.FlipFlop).id )
        assertEquals(listOf("c"), (modules[2] as Day20.FlipFlop).destinations )

        assertEquals(Day20.FlipFlop::class, modules[3]::class)
        assertEquals("c", (modules[3] as Day20.FlipFlop).id )
        assertEquals(listOf("inv"), (modules[3] as Day20.FlipFlop).destinations )

        assertEquals(Day20.Conjunction::class, modules[4]::class)
        assertEquals("inv", (modules[4] as Day20.Conjunction).id )
        assertEquals(listOf("a"), (modules[4] as Day20.Conjunction).destinations )
        assertEquals(listOf("c"), (modules[4] as Day20.Conjunction).getInputs() )
    }

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
    fun sample2(){
        val sample = listOf(
            "broadcaster -> a",
            "%a -> inv, con",
            "&inv -> b",
            "%b -> con",
            "&con -> output",
        )
        val modules = Day20.createModules(sample)
        var counts = Day20.pressButtonTimes(modules,1000)
        assertEquals(4250, counts.lowCount)
        assertEquals(2750, counts.highCount)
    }
    @Test
    fun part1() {
        val data = Util().readData("day20.txt")
        val modules = Day20.createModules(data)
        var counts = Day20.pressButtonTimes(modules,1000)
        assertEquals(16772, counts.lowCount)
        assertEquals(40615, counts.highCount)
        //681194780
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