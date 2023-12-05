import java.lang.Error

class Day5(
    private val seeds: List<Long>,
    private val seedToSoil: Mapping,
    private val soilToFert: Mapping,
    private val fertToWater: Mapping,
    private val waterToLight: Mapping,
    private val lighToTemperature: Mapping,
    private val temperatureToHumidity: Mapping,
    private val humidityToLocation: Mapping
) {
    data class Range(val start: Long, val duration: Long){
        val end = start+ duration-1

        fun isInRange(value: Long): Boolean {
            return start<= value && value <= end
        }
    }

    data class Mapping( val mappings : Map<Long, Range>){

        // part 1
        fun findTarget(value: Long): Long {
            val entry = mappings.filter { it.value.isInRange(value) }.toList()
            if (entry.size==1){
                val diff = value - entry[0].second.start
                return entry[0].first + diff
            }
            if (entry.isEmpty()){
                return value
            }
            throw Error("Found multiple entries")
        }

        // part 2
        fun findTargets(value: Range): List<Range> {
            val ranges = mutableListOf<Range>()
            var durationLeft = value.duration
            while (durationLeft>0){
                var currentValue = value.start + (value.duration - durationLeft)

                val foundRanges = mappings.filter { it.value.isInRange(currentValue) }.toList()
                if (foundRanges.isNotEmpty()){
                    val foundRange = foundRanges[0]
                    var start = foundRange.first
                    var duration = foundRange.second.duration
                    if (value.start> foundRange.second.start){
                        val startOffset = (value.start - foundRanges[0].second.start)
                        start += startOffset
                        duration -= startOffset
                    }
                    duration = duration.coerceAtMost(durationLeft)
                    ranges.add(Range(start, duration))
                    durationLeft -= duration
                } else{
                    // calculate end as passedInRange end or if there is a later range, use this
                    var end = value.end
                    val nextRange = mappings.filter { currentValue< it.value.start}.toList().sortedBy { it.second.start }
                    if (nextRange.isNotEmpty()){
                        end = (nextRange[0].second.start - 1).coerceAtMost(value.end)
                    }
                    val start = currentValue
                    val duration = end-start+1

                    ranges.add(Range(start, duration))

                    durationLeft -= duration
                }
            }
            return ranges
        }

        companion object{
            fun fromLines(data: List<String>): Mapping {
                val mappings = HashMap<Long, Range>()
                data.forEach { it ->
                    val numbers = it
                        .split(" ")
                        .filter { it.isNotEmpty() }
                    mappings[numbers[0].toLong()] = Range(numbers[1].toLong(), numbers[2].toLong())
                }
                return Mapping(mappings)
            }
        }
    }


    // part 1 - Find the location for a sead
    fun findLocation(seed: Long): Long {
        val soil = seedToSoil.findTarget(seed)
        val fertilizer  = soilToFert.findTarget(soil)
        val water  = fertToWater.findTarget(fertilizer)
        val light  = waterToLight.findTarget(water)
        val temperature  = lighToTemperature.findTarget(light)
        val humidity  = temperatureToHumidity.findTarget(temperature)
        return humidityToLocation.findTarget(humidity)
    }

    fun findLowestLocation(): Long {
        return seeds.minOf { findLocation(it) }
    }

    // part 2
    fun findLowestLocationAsSeedPair(): Long {
        val locations = seeds.chunked(2)
            .map { Range(it[0],it[1]) }
            .flatMap { seedToSoil.findTargets(it)}
            .flatMap { soilToFert.findTargets(it)}
            .flatMap { fertToWater.findTargets(it)}
            .flatMap { waterToLight.findTargets(it)}
            .flatMap { lighToTemperature.findTargets(it)}
            .flatMap { temperatureToHumidity.findTargets(it)}
            .flatMap { humidityToLocation.findTargets(it)}
        return locations.sortedBy { it.start }[0].start
    }

    companion object{
        fun fromList(data: List<String>): Day5{
            val seeds = data[0].substringAfter("seeds: ").split(" ").map { it.toLong() }
            return Day5(
                seeds,
                Mapping.fromLines(findLines("seed-to-soil", data)),
                Mapping.fromLines(findLines("soil-to-fertilizer", data)),
                Mapping.fromLines(findLines("fertilizer-to-water", data)),
                Mapping.fromLines(findLines("water-to-light", data)),
                Mapping.fromLines(findLines("light-to-temperature", data)),
                Mapping.fromLines(findLines("temperature-to-humidity", data)),
                Mapping.fromLines(findLines("humidity-to-location", data))
            )
        }

        private fun findLines(mapToFind: String, data: List<String>): List<String> {
            var active= false
            val lines = mutableListOf<String>();
            data.forEach {
                if (it.startsWith(mapToFind)){
                    active = true
                } else{
                    if (active){
                       if (it.isEmpty()){
                           return lines
                       }
                       lines.add(it)
                    }
                }
            }
            return lines
        }
    }
}