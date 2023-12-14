import javax.management.Query
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

            return getCombinations2(foldPattern(pattern), foldedNumbers)
        }

        fun sumCombinations(data: List<String>): Int {
            return data.sumOf { getCombinations(it) }
        }

        data class Option(var pattern:List<String>, val numbers: List<Int>) {

            var patternExplored : String = ""

            fun explore(): List<Option> {

                if (pattern == listOf("#","?")){
                    println()
                }

                if (pattern.size==0){
                    return listOf(this)
                }
                patternExplored = pattern[0]

                // Matched the numbers, but there are still ones
                if (numbers.size==0){ // we have matched all the numbers, but there are still
                    // any of the pattern remaining
                    if (pattern.any { it.contains('#')}) {
                        return listOf()
                    }
                    return listOf(Option(listOf(), numbers))
                }

                // we still have numbers to match
                // check that the current pattern is possible
                var question = pattern[0].indexOf('?')
                if (question > numbers[0]){
                    return listOf()
                }

                // Can't possibly match
                if (numbers[0]> pattern[0].length){
                    // can't match this pattern, but other may
                    if (pattern.size>1) {
                        return listOf(Option(pattern.drop(1), numbers))
                    }
                    return listOf()
                }

                val unknowns = pattern[0].count { it=='?' }

                // direct match
                if (numbers[0] == pattern[0].length && unknowns==0){
                    return listOf(Option(pattern.drop(1), numbers.drop(1)))
                }

                var options = mutableListOf<Option>()
                if (unknowns>0){

                    var beforeQuestion  = pattern[0].substring(0,question)

                    var newPatternsWithOutSpring = mutableListOf<String>()

                    if (beforeQuestion.isNotEmpty()){
                        newPatternsWithOutSpring.add(beforeQuestion)
                    }
                    if (question != pattern[0].length-1){
                        newPatternsWithOutSpring.add(pattern[0].substring(question+1))
                    }
                    newPatternsWithOutSpring.addAll(pattern.drop(1))
                    options.add(Option(newPatternsWithOutSpring, numbers))
                    //
                    var newPatternsWithSpring = mutableListOf(pattern[0].replaceFirst('?','#'))
                    newPatternsWithSpring.addAll(pattern.drop(1))
                    options.add(Option(newPatternsWithSpring, numbers))
                }

                // each unknown can be on or off.
                // if off, add new option - adding to the pattern
//
//                for (c in 0.. pattern[0].length-1){
//                    if (pattern[0][c]=='?'){
//                        if (currentPattern.isNotEmpty()){ // not filled in
//                            var newPatterns = mutableListOf<String>(currentPattern)
//                            if (c< pattern[0].length-1){
//                                newPatterns.add(pattern[0].substring(c+1))
//                            }
//                            newPatterns.addAll(pattern.drop(1))
//                            options.add(Option(newPatterns, numbers))
//                        } else{
//                            currentPattern = currentPattern+'#'
//                        }
//                    } else{
//                        currentPattern = currentPattern+pattern[0][c]
//                    }
//                }


//                val max = 2.toDouble().pow(unknowns).toInt()-1
//                for (x in 0.. max) {
//                    var patterToInject =
//                        Integer.toBinaryString(x)!!.padStart(unknowns, '0').map { mapChar(it) }
//
//                    val replacedString = replaceString(pattern[0], patterToInject)
//                    val groups = getGroups(replacedString)
//                    if (groups.size> numbers.size){
//                        continue
//                    }
//                    if (groups.size==0){
//                        options.add(Option(pattern.drop(1), numbers))
//                    } else{
//                        val groupsToMatch = numbers.toList().subList(0, groups.size)
//                        if (groups == groupsToMatch) {
//                            options.add(Option(pattern.drop(1), numbers.drop(groups.size)))
//                        }
//                    }
//                }
                return options.toList()
            }
        }


        fun getCombinations2(s: String, numbers: List<Int>): Int {

            val groups = s.split(".").filter { it.length>0 }

            var options = listOf<Option>(Option(groups, numbers))
            while (options.any { it.pattern.size >0 }){
                options = options.flatMap { it.explore() }
            }
            var num = options.filter { it.numbers.size==0 && it.pattern.size==0 }
            return num.size
        }

        fun getCombinations2(s: String): Int{
            val numbers = s.substringAfter(" ").split(",").map { it.toInt() }
            val pattern = s.substringBefore(" ")
            return getCombinations2(pattern, numbers)
        }

        fun sumCombinations2(data: List<String>): Int {
            return data.sumOf { getCombinations2(it) }
        }
    }
}