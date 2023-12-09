class Day9(val readings: List<MutableList<Int>>) {

    // part 1
    fun sumHistory(): Int {
        return readings.sumOf { getHistory(it)  }
    }

    // part 2
    fun sumLeftMostHistory(): Int {
        return readings.sumOf { getLeftHistory(it)  }
    }

    private fun getHistory(inputRow: MutableList<Int>): Int {
        var row = inputRow
        var readingRows = mutableListOf(row)
        while (row.any { it !=0 }){
            row = createRow(row);
            readingRows.add(row)
        }
        return calculateNextValue(readingRows)
    }

    fun getLeftHistory(inputRow: MutableList<Int>): Int {
        var row = inputRow
        var readingRows = mutableListOf(row)
        while (row.any { it !=0 }){
            row = createRow(row);
            readingRows.add(row)
        }
        return calculateFirstValue(readingRows)
    }

    private fun calculateFirstValue(rows: MutableList<MutableList<Int>>): Int {
        val readingRows = rows.reversed()
        readingRows[0].add(0)
        for (x in 0..readingRows.size-2){

            var newValue = readingRows[x+1].first() - readingRows[x].first()
            readingRows[x+1].add(0, newValue)
        }
        return readingRows.last().first()
    }

    fun calculateNextValue(rows: MutableList<MutableList<Int>>): Int{
        val readingRows = rows.reversed()
        readingRows[0].add(0)
        for (x in 0..readingRows.size-2){
            var newValue = readingRows[x].last() + readingRows[x+1].last()
            readingRows[x+1].add(newValue)
        }
        return readingRows.last().last()
    }


    fun getFirstHistory(): Int {
        var row = readings[0]
        return getHistory(row)
    }

    fun createRow(row: MutableList<Int>): MutableList<Int> {
        var newRow = mutableListOf<Int>()
        for (pos in 0..row.size-2){
            newRow.add(row[pos+1] - row[pos])
        }
        return newRow;
    }



    companion object {
        fun ofList(input:  List<String>): Day9 {
            return Day9(input.map { it.split(" ").map { it.toInt() }.toMutableList() })
        }
        fun of(input:  String): Day9 {
            return Day9( listOf(input.split(" ").map { it.toInt() }.toMutableList() ))
        }
    }

}