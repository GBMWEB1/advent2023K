

class Day3 {

    class Part(val value: Int, val row: Int, val startCol: Int){

        fun isClose(rowIndex: Int, colIndex: Int): Boolean {
            val endCol = startCol+value.toString().length-1
            return (rowIndex+1 >= row) && (rowIndex-1 <= row)
                    && (colIndex+1 >= startCol)
                    && (colIndex-1 <= endCol)
        }

        companion object{
            fun extractPartsFromLine(line: String, rowIndex: Int): List<Part>{
                val parts = mutableListOf<Part>()
                var currentNumberStart = -1
                for (colIndex   in 0..line.length-1){
                    val bit = line[colIndex];
                    if (!bit.isDigit() && currentNumberStart>-1){
                        parts.add(extractPart(line, currentNumberStart, colIndex, rowIndex ))
                        currentNumberStart = -1
                    }
                    if (bit.isDigit() && currentNumberStart==-1){
                        currentNumberStart = colIndex
                    }
                }
                if (currentNumberStart>-1) {
                    parts.add(extractPart(line, currentNumberStart, line.length,rowIndex))
                }
                return parts
            }

            fun extractPart(line: String, currentNumberStart: Int, colIndex: Int, rowIndex: Int): Part{
                val nu = line.substring(currentNumberStart, colIndex)

                return Part(nu.toInt(), rowIndex, currentNumberStart)
            }
        }
    }

    companion object {

        // Part 1
        fun sumValidGames(allLines: List<String>): Int {
            var parts = mutableListOf<Int>()
            allLines.forEachIndexed { index, line ->
                parts.addAll(getPartsInLine(index, allLines))
            }
            return parts.sum()
        }

        private fun getPartsInLine(index: Int, allLines: List<String>): List<Int> {
            var row = allLines[index];
            var parts = mutableListOf<Int>()
            var currentNumberStart = -1
            for (colIndex   in 0..row.length-1){
                val bit = row[colIndex];
                if (!bit.isDigit() && currentNumberStart>-1){
                    if (isValidPart(allLines, index, currentNumberStart, colIndex-1)) {
                        parts.add(extractNumber(row, currentNumberStart, colIndex))
                    }
                    currentNumberStart = -1
                }
                if (bit.isDigit() && currentNumberStart==-1){
                    currentNumberStart = colIndex
                }
            }
            if (currentNumberStart>-1) {
                if (isValidPart(allLines, index, currentNumberStart, row.length)) {
                    parts.add(extractNumber(row, currentNumberStart, row.length))
                }
            }
            return parts;
        }

        fun isValidPart(allLines: List<String>, rowIndex: Int, currentNumberStart: Int, colIndex: Int): Boolean {
            for (row in Math.max(rowIndex-1,0) .. Math.min(rowIndex+1, allLines.size-1)){
                val searchStartCol = Math.max(currentNumberStart-1,0)
                val searchEndCol = Math.min(colIndex+1, allLines[rowIndex].length-1);

                for (col in  searchStartCol.. searchEndCol){
                    if (allLines[row][col].isDigit()){
                        continue
                    }
                    if (allLines[row][col] =='.'){
                        continue
                    }
                    return true;
                }
            }
            return false
        }

        fun extractNumber(row: String, currentNumberStart: Int, colIndex: Int): Int {
            val nu = row.substring(currentNumberStart, colIndex)
            return nu.toInt()
        }

        fun extractDigits(plan: List<String>) {
            val chars = mutableSetOf<Char>()
            plan.forEach {
                it.forEach { if (!it.isDigit()) chars.add(it) }
            }
            println(chars)
        }

        fun extractAll(plan: List<String>) {
            val chars = mutableSetOf<Char>()
            plan.forEach {
                it.forEach { chars.add(it) }
            }
            println(chars)
        }

        fun getGears(lines: List<String>): List<Int> {
            val parts = lines.flatMapIndexed { rowIndex: Int, line: String ->  Part.extractPartsFromLine(line, rowIndex) }

            val gears = mutableListOf<Int>()
            lines.forEachIndexed { rowIndex, row ->
                // for each line
                row.forEachIndexed { colIndex, c ->
                    if (c == '*'){
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



}