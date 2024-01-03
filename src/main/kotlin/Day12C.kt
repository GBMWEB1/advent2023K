class Day12C {

    class Option(var groups : MutableList<Int>, var currentGroup: String= "", var count: Long= 1) {
        private fun processRock(): Option {
            currentGroup += '#'
            return this
        }

        fun processSpace(): Option {
            val option = Option(this.groups.toMutableList(), "", this.count)
            if (currentGroup.isNotEmpty()) {
                option.groups.add(currentGroup.length)
            }
            return option
        }

        fun process(c: Char): List<Option> {
            return when (c) {
                '.' -> listOf(this.processSpace())
                '#' -> listOf(this.processRock())
                '?' -> listOf(this.processSpace(), this.processRock())
                else -> {
                    emptyList()
                }
            }
        }

        fun isValid(numbers: List<Int>): Boolean {
            if (this.groups.isNotEmpty()) {
                return this.groups == numbers.take(this.groups.size)
            }
            return true
        }
    }

    companion object{

        fun getGroups(line: String): List<Int> {
            return line.split(".").filter { it.isNotEmpty() }.map { it.length }
        }

        fun foldPattern(pattern: String): String {
            return "$pattern?$pattern?$pattern?$pattern?$pattern"
        }

        fun getCombinationsFolded(s: String): Long {
            val numbers = s.substringAfter(" ").split(",").map { it.toInt() }
            val pattern = s.substringBefore(" ")
            val foldedNumbers = numbers.toMutableList()
            foldedNumbers.addAll(numbers)
            foldedNumbers.addAll(numbers)
            foldedNumbers.addAll(numbers)
            foldedNumbers.addAll(numbers)

            return getCombo(foldPattern(pattern), foldedNumbers)
        }

        fun getCombo(pattern: String, numbers: List<Int>): Long {
            var options = listOf(Option(mutableListOf()))
            for (char in pattern){
                options = options
                    .flatMap { it.process(char)}
                    .filter { it.isValid(numbers) }
                    .let { consolidate(it) }
            }
            options = options.map { it.processSpace() }.filter { it.groups==numbers }
            return options.sumOf { it.count }
        }

        private fun consolidate(options: List<Option>): List<Option> {
            // create a map of groups, current -> List<Options>
            val groups =
                options.groupBy { Pair(it.groups, it.currentGroup)}
            return groups.map { group -> Option(group.key.first, group.key.second, group.value.sumOf { it.count }) }
        }

        fun getCombinations(s: String): Long {
            val numbers = s.substringAfter(" ").split(",").map { it.toInt() }
            val pattern = s.substringBefore(" ")
            return getCombo(pattern, numbers)
        }

        fun sumCombinations(data: List<String>): Long {
            return data.sumOf { getCombinations(it) }
        }

        fun sumCombinationsFolded(data: List<String>): Long {
            return data.sumOf { getCombinationsFolded(it) }
        }
    }
}