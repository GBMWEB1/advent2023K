import kotlin.math.pow

class Day12() {
    companion object{
        fun getCombinations(s: String, groups: List<Int>): Int {
            // so any question mark can be a combination of # or .
            // so if 1 question mark, just 2 states
            // if 2 question marks then 4 states
            // so count in binary 2 to power of question marks, then convert to binary with left pad with .
            // if groups match, then check legth of each group.

            val unknowns = s.count { it=='?' }

            val max = 2.toDouble().pow(unknowns).toInt()-1

            val brokenStrings = s.count { it=='#'}
            var targetBrokenStrings = groups.sumOf { it }

            var count = 0;
            for (x in 0.. max){
                var pattern =
                    Integer.toBinaryString(x)!!
                if (pattern.count { it=='1'} + brokenStrings == targetBrokenStrings ) {
                    var chars = pattern.padStart(unknowns,'0')
                        .map { mapChar(it) }
                    val replacedString = replaceString(s, chars)
                    if (groups== getGroups(replacedString)){
                        count++
                    }

                }


            }
            println("Completed -$count")
            // length of string needs to be number of question marks
            return count;
        }

        private fun replaceString(s: String, pattern: List<Char>): String {
            var newString = s.toList().toMutableList()
            var patternIndex = 0
            newString.forEachIndexed { index, c ->
                if (c=='?'){
                    newString[index] = pattern[patternIndex]
                    patternIndex++
                }
            }
            return String(newString.toCharArray())
        }

        private fun mapChar(it: Char): Char {
            if (it=='1'){return '#'}
            return '.'
        }

        fun getGroups(line: String): List<Int> {
            return line.split(".").filter { it.isNotEmpty() }.map { it.length }
        }

        fun getCombinations(s: String): Int {
            val numbers = s.substringAfter(" ").split(",").map { it.toInt() }
            val pattern = s.substringBefore(" ")
            return getCombinations(pattern, numbers)
        }

        fun foldPattern(pattern: String): String {
            return pattern + '?' + pattern + '?' +pattern + '?' +pattern + '?' +pattern
        }

        fun getCombinationsFolded(s: String): Int {
            val numbers = s.substringAfter(" ").split(",").map { it.toInt() }
            val pattern = s.substringBefore(" ")
            var foldedNumbers = numbers.toMutableList()
            foldedNumbers.addAll(numbers)
            foldedNumbers.addAll(numbers)
            foldedNumbers.addAll(numbers)
            foldedNumbers.addAll(numbers)

            return getCombinations(foldPattern(pattern), foldedNumbers)
        }

        fun sumCombinations(data: List<String>): Int {
            return data.sumOf { getCombinations(it) }
        }
    }
}