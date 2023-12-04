class Day1 {

    // Part 1 - Search for digits either end
    // Part 2 - Search for the word "one" or digits from either end

    companion object {
        private var digits = listOf("zero","one","two","three","four","five","six","seven","eight","nine")
        fun getValues(line: String): Int {


            val values = mutableListOf<Int>()
            var lettersSoFar = ""
            for (char in line) {
                if (char.isDigit()) {
                    values.add(char.digitToInt())
                    lettersSoFar = ""
                }
                else {
                    lettersSoFar += char
                    val digitsFound = digits.find { lettersSoFar.contains(it) }
                    if (digitsFound != null) {
                        values.add(digits.indexOf(digitsFound))
                        lettersSoFar = char.toString()
                    }
                }
            }

            return "${values.first()}${values.last()}".toInt()
        }

        fun sumValues(line: List<String>): Int {
            return line.sumOf { getValues(it) }
        }
    }
}