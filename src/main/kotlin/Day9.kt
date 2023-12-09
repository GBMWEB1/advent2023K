class Day9(private val readings: List<MutableList<Int>>) {

    companion object {
        fun ofList(input:  List<String>): Day9 {
            return Day9(input
                .map { line -> line.split(" ")
                    .map { it.toInt() }
                    .toMutableList() }
            )
        }
        fun of(input:  String): Day9 {
            return Day9(
                listOf(
                    input.split(" ")
                        .map { it.toInt() }
                        .toMutableList()
                )
            )
        }
    }

    // part 1
    fun sumHistory(): Int {
        return readings.sumOf { getHistory(it)  }
    }

    private fun getHistory(inputRow: List<Int>): Int {
        return calculateNextValue(expandToList(inputRow))
    }

    private fun expandToList(inputRow: List<Int>): List<List<Int>> {
        val rows = mutableListOf(inputRow)
        while (rows.last().any { it != 0 }) {
            rows.add(createRow(rows.last()))
        }
        return rows
    }

    fun createRow(row: List<Int>): List<Int> {
        val newRow = mutableListOf<Int>()
        for (pos in 0..row.size-2){
            newRow.add(row[pos+1] - row[pos])
        }
        return newRow
    }

    private fun calculateNextValue(rows: List<List<Int>>): Int{
        val readingRows = rows.map { it.toMutableList() }.reversed()
        readingRows[0].add(0)
        for (x in 0..readingRows.size-2){
            val newValue = readingRows[x].last() + readingRows[x+1].last()
            readingRows[x+1].add(newValue)
        }
        return readingRows.last().last()
    }


    // part 2
    fun sumLeftHistory(): Int {
        return readings.sumOf { getFirstValue(it)  }
    }

    private fun getFirstValue(inputRow: List<Int>): Int {
        return calculateFirstValue(expandToList(inputRow))
    }

    private fun calculateFirstValue(rows: List<List<Int>>): Int {
        val readingRows = rows.map { it.toMutableList() }.reversed()
        readingRows[0].add(0)
        for (x in 0..readingRows.size-2){
            val newValue = readingRows[x+1].first() - readingRows[x].first()
            readingRows[x+1].add(0, newValue)
        }
        return readingRows.last().first()
    }

}