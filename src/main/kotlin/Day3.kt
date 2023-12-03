class Day3(private val lines: List<String>) {

    private val parts = lines.flatMapIndexed { rowIndex: Int, line: String ->  Part.extractPartsFromLine(rowIndex, line) }

    data class Part(val value: Int, val row: Int, val startCol: Int){
        private val endCol = startCol+value.toString().length-1

        fun isValid(allLines: List<String>): Boolean {
            val searchStartRow = (row - 1).coerceAtLeast(0)
            val searchEndRow = (row + 1).coerceAtMost(allLines.size - 1)
            val searchStartCol = (startCol - 1).coerceAtLeast(0)
            val searchEndCol = (endCol + 1).coerceAtMost(allLines[row].length - 1)

            for (searchRowIndex in searchStartRow..searchEndRow){
                for (searchColIndex in  searchStartCol.. searchEndCol){
                    val bit = allLines[searchRowIndex][searchColIndex]
                    if (!bit.isDigit() && bit !='.'){
                        return true
                    }
                }
            }
            return false
        }

        fun isClose(rowIndex: Int, colIndex: Int): Boolean {
            return (rowIndex+1 >= row)
                    && (rowIndex-1 <= row)
                    && (colIndex+1 >= startCol)
                    && (colIndex-1 <= endCol)
        }

        companion object{
            fun extractPartsFromLine( rowIndex: Int, line: String): List<Part>{
                val parts = mutableListOf<Part>()
                var colStart : Int = -1
                line.forEachIndexed { currentCol, bit ->
                    if (!bit.isDigit() && colStart>-1){
                        parts.add(extractPart(rowIndex, line, colStart, currentCol ))
                        colStart = -1
                    }
                    if (bit.isDigit() && colStart==-1){
                        colStart = currentCol
                    }
                }
                if (colStart>-1) {
                    parts.add(extractPart(rowIndex, line, colStart, line.length))
                }
                return parts
            }

            private fun extractPart(rowIndex: Int, line: String, startCol: Int, endCol: Int): Part{
                val value = line.substring(startCol, endCol).toInt()

                return Part(value, rowIndex, startCol)
            }
        }
    }

    fun sumValidGames(): Int {
        return parts
            .filter { it.isValid(lines) }
            .sumOf { it.value }
    }

    // part 2
    fun getGears(): List<Int> {
        val gears = mutableListOf<Int>()
        for (rowIndex in lines.indices){
            for (colIndex in lines[rowIndex].indices){
                val bit = lines[rowIndex][colIndex]
                if (bit == '*'){
                    val closeParts = parts.filter { it.isClose(rowIndex, colIndex) }
                    if (closeParts.size==2){
                        gears.add(closeParts[0].value * closeParts[1].value)
                    }
                }
            }
        }
        return gears
    }
}