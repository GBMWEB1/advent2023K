class Day1 {

    companion object {
        private var digits = listOf("zero","one","two","three","four","five","six","seven","eight","nine")
        fun getValues(line: String): Int {

            val values = mutableListOf<Int>()
            var lettersSoFar = ""
            for (char in line) {
                if (char.isDigit()) {
                    values.add(char.toString().toInt())
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