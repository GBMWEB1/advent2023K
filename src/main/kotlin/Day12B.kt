import kotlin.math.pow

class Day12B() {

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

            return getCombo(foldPattern(pattern), foldedNumbers)
        }

        fun sumCombinations(data: List<String>): Int {
            return data.sumOf { getCombinations(it) }
        }

        data class Option(var pattern:List<String>, val numbers: List<Int>) {

            var patternExplored : String = ""

            fun isFinished(): Boolean {
                return pattern.size==0 && numbers.size==0
            }
            fun isValid(): Boolean{
                if (pattern.size==0){
                    if (numbers.size==0){
                        return true
                    }
                    return false
                }

                // Matched the numbers, but there are still ones
                if (numbers.size==0){ // we have matched all the numbers, but there are still
                    // any of the pattern remaining
                    if (pattern.any { it.contains('#')}) {
                        return false
                    }
                    return true
                }

                var question = pattern[0].indexOf('?')
                if (question > numbers[0]){
                    return false
                }

                val minSize = numbers.sum() + numbers.size-1
                if (pattern.size==1 && pattern[0].length< minSize){
                    return false
                }
                return true

            }

            fun explore(): List<Option> {
                if (pattern == listOf("#?????")){
                    println()
                }
                if (isFinished()){
                    return listOf(this)
                }

                if (!isValid()){
                    return listOf()
                }

                patternExplored = pattern[0]

                if (numbers.size==0){
                    if (!patternExplored.contains("#")){
                        return listOf(Option(listOf(), listOf()))
                    }
                }

                if (numbers[0]> patternExplored.length){
                    if (pattern.size==1){
                        return listOf()
                    }
                    // can't match in this pattern, but other may
                    return listOf(Option(pattern.drop(1), numbers))
                }

                // direct match
                if (numbers[0] == patternExplored.length){
                    return listOf(Option(pattern.drop(1), numbers.drop(1)))
                }

                var options = mutableListOf<Option>()
                var question = pattern[0].indexOf('?')

                if (question>-1){

                    var beforeQuestion  = pattern[0].substring(0,question)
                    if (beforeQuestion.isEmpty()){
                        options.add(Option(pattern.drop(1), numbers))
                    }
                    if (beforeQuestion.length> 0 && beforeQuestion.length == numbers[0]) {
                        options.add(Option(pattern.drop(1), numbers))
                    }

                    // add if it wasn't a space
                    var newPatternWithSpring = pattern[0].replaceFirst('?','#')

                    var newPatternsWithSpring = mutableListOf<String>(newPatternWithSpring)
                    newPatternsWithSpring.addAll(pattern.drop(1))
                    options.add(Option(newPatternsWithSpring, numbers))
                }
                return options.toList()
            }
        }


        fun getCombo(s: String, numbers: List<Int>): Int {
            var options = listOf(s);
            println("Starting $s")
            while (options.any { it.contains("?") }){
                options = options.flatMap { mutateOption(it) }.filter { isValid(it, numbers) }
            }
            return options.size
        }

        private fun isValid(it: String, numbers: List<Int>): Boolean {
            var groupRocks = firstRocks(it)
            if (it.contains("?")){
                val good = groupRocks.first == numbers.take(groupRocks.first.size)
                if (!good){
                    return false;
                }
                // checl the next one:
                val nextOne = numbers.drop(groupRocks.first.size).take(1)
                if (groupRocks.second > nextOne[0]){
                    return false
                }
                return true;
            }
            return groupRocks == numbers

        }

        private fun firstRocks(it: String) : Pair<List<Int>,Int> {
            var rocks = mutableListOf<Int>()
            var currentRocks = 0
            for (pos in 0..<it.length){
                if (it[pos]=='#'){
                    currentRocks++
                } else if (it[pos]=='.'){
                    if (currentRocks>0) {
                        rocks.add(currentRocks)
                    }
                    currentRocks=0
                } else{ // question mark
                    return Pair(rocks.toList(),currentRocks)
                }
            }
            if( currentRocks>0){
                rocks.add(currentRocks)
            }
            return Pair(rocks.toList(),0)
        }

        private fun justRocks(it: String) : String {
            return it.filter { it!= '?' }
        }

        private fun mutateOption(it: String): List<String> {
            if (!it.contains("?")){
                return listOf(it);
            }
            val question = it.indexOfFirst { it =='?'}
            val option1 = replaceQuestion(it, question, '.')
            val option2 = replaceQuestion(it, question, '#')

            return listOf(option1, option2)
        }

        private fun replaceQuestion(it: String, question: Int, c: Char): String {
            return it.substring(0, question)  + c + it.substring(question+1)
        }

        fun getCombinations2(s: String): Int{
            val numbers = s.substringAfter(" ").split(",").map { it.toInt() }
            val pattern = s.substringBefore(" ")
            return getCombo(pattern, numbers)
        }

        fun sumCombinations2(data: List<String>): Int {
            return data.sumOf { getCombinations2(it) }
        }

        fun sumCombinationsFolded(data: List<String>): Int {
            return data.sumOf { getCombinationsFolded(it) }
        }
    }
}