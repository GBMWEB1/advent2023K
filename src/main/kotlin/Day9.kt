class Day9(private val readings: List<List<Int>> = listOf()) {

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
    fun sumNextValues(): Int {
        return readings.sumOf { calculateNextValue(expandToList(it))  }
    }

    private fun expandToList(row: List<Int>): List<List<Int>> {
        val rows = mutableListOf(row)
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

    fun calculateNextValue(rows: List<List<Int>>): Int{
        val readingRows = rows.map { it.toMutableList() }.reversed()
        readingRows[0].add(0)
        for (x in 0..readingRows.size-2){
            val newValue = readingRows[x].last() + readingRows[x+1].last()
            readingRows[x+1].add(newValue)
        }
        return readingRows.last().last()
    }

    // part 2
    fun sumFirstValue(): Int {
        return readings.sumOf { calculateFirstValue(expandToList(it)) }
    }

    private fun calculateFirstValue(expandedList: List<List<Int>>): Int {
        val rows = expandedList.map { it.toMutableList() }.reversed()
        rows[0].add(0)
        for (x in 0..rows.size-2){
            val newValue = rows[x+1].first() - rows[x].first()
            rows[x+1].add(0, newValue)
        }
        return rows.last().first()
    }
}