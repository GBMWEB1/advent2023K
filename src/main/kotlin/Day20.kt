class Day20 {

    enum class PULSE_STRENGTH {
        HIGH,
        LOW
    }

    data class Pulse(val source: String, val strength: PULSE_STRENGTH, val destination: String)

    interface Module{

        val id: String
        val destinations: List<String>

        fun receive(pulse: Pulse): List<Pulse>

        companion object{
            fun createModule(it: String) : Module {
                val id = it.substringBefore(" ->")
                val destinations = it.substringAfter("-> ").split(", ")
                if (id== "broadcaster"){
                    return BroadCaster("broadcaster", destinations )
                }
                if (id.startsWith("%")){
                    return FlipFlop(id.substring(1), destinations)
                }
                if (id.startsWith("&")){
                    return Conjunction(id.substring(1), destinations)
                }
                throw Error("Unable to create a module for $it")
            }
        }
    }

    class BroadCaster(override val id: String, override val destinations: List<String>) : Module{

        override fun receive(pulse: Pulse): List<Pulse> {
            return destinations.map { Pulse(id, pulse.strength, it) }
        }
  }

    class FlipFlop(override val id: String, override val destinations: List<String>) : Module{
        var on: Boolean = false

        override fun receive(pulse: Pulse): List<Pulse> {
            // However, if a flip-flop module receives a low pulse, it flips between on and off.
            // If it was off, it turns on and sends a high pulse. If it was on, it turns off and sends a low pulse.
            if (pulse.strength == PULSE_STRENGTH.LOW){
                on = !on
                if (on){
                    return destinations.map { Pulse(id, PULSE_STRENGTH.HIGH, it )}
                }
                return destinations.map { Pulse(id, PULSE_STRENGTH.LOW, it )}
            }
            return listOf()
        }
    }

    //Conjunction modules (prefix &) remember the type of the most recent pulse received from each of their connected input modules;
    // they initially default to remembering a low pulse for each input.
    // When a pulse is received, the conjunction module first updates its memory for that input.
    // Then, if it remembers high pulses for all inputs, it sends a low pulse; otherwise, it sends a high pulse.
    class Conjunction(override val id: String, override val destinations: List<String>) : Module {
        var memory: MutableMap<String, PULSE_STRENGTH> = mutableMapOf()

        override fun receive(pulse: Pulse): List<Pulse> {
            memory[pulse.source] = pulse.strength
            if (memory.all { it.value==PULSE_STRENGTH.HIGH }){
                return destinations.map { Pulse(id, PULSE_STRENGTH.LOW, it) }
            }
            return destinations.map { Pulse(id, PULSE_STRENGTH.HIGH, it) }
        }

        fun setInputs(inputs: List<String>) {
            inputs.forEach { memory[it] = PULSE_STRENGTH.LOW }
        }

        fun getInputs(): List<String> {
            return memory.keys.toList()
        }
    }

    data class Watch (val target: String, val strength: PULSE_STRENGTH){
        var times = mutableListOf<Int>()
    }

    data class Modules(val mods: List<Module>){
        var watchers = mutableListOf<Watch>()

        fun findAllHit(): Long {
            val numbers = watchers.map { it.times[0].toLong() }
            return numbers[0]*numbers[1]*numbers[2]*numbers[3]
        }
    }

    data class ButtonStats(val lowCount: Int, val highCount: Int)

    companion object {
        fun createModules(data: List<String>): Modules {
            val modules = data.map { Module.createModule(it) }

            modules
                .filterIsInstance<Conjunction>()
                .forEach { conj->
                    val inputs = modules.filter { it.destinations.contains(conj.id)}.map { it.id }
                    conj.setInputs(inputs)
                }
            return Modules(modules)
        }

        fun pressButton(modules: Modules, times: Int): ButtonStats {
           // println("Button pressed")
            var pulses = listOf(Pulse("button", PULSE_STRENGTH.LOW, "broadcaster"))
            var lowCount = 1
            var highCount= 0
            while (pulses.isNotEmpty()){
                pulses = pulses.flatMap { processPulse(modules.mods, it) }
                lowCount += pulses.count { it.strength == PULSE_STRENGTH.LOW }
                highCount += pulses.count { it.strength == PULSE_STRENGTH.HIGH }

                val goodPulses = pulses.filter{ it.destination== "bq" && it.strength==PULSE_STRENGTH.HIGH}

                goodPulses.forEach { pulse->
                    val watch = modules.watchers.find { it.target== pulse.source}
                    if (watch!= null){
                        watch.times.add(times)
                    } else{
                        println("Not watching ${pulse.source}")
                    }
                }
            }
            return ButtonStats(lowCount, highCount)
        }
        fun pressButtonTimes(modules: Modules, times: Int): ButtonStats {
            var lowCount = 0
            var highCount = 0
            var count =1
            repeat(times){
                val buttonCount = pressButton(modules,count )
                lowCount += buttonCount.lowCount
                highCount += buttonCount.highCount
                count++
            }
            return ButtonStats(lowCount,highCount)
        }

        private fun processPulse(modules: List<Module>, pulse: Pulse): List<Pulse> {
            val module = modules.find { it.id==pulse.destination }
            if (module!= null){
                return module.receive(pulse)
            }
            return listOf()
        }
    }
}