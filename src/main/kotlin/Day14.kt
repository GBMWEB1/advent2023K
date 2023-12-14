
class Day14(var grid: MutableList<String>) {


    private fun cycle() {
        tiltAllNorth()
        tiltAllWest()
        tiltAllSouth()
        tiltAllEast()
    }

    // Part 1
    fun tiltAllNorth() {
        grid.forEachIndexed { index, _ ->
            tiltNorth(index)
        }
    }
    fun tiltNorth(row: Int) {
        for (col in 0..<grid[row].length){
            if (grid[row][col]=='O'){
                moveRockNorth(row, col)
            }
        }
    }

    private fun moveRockNorth(row: Int, c: Int) {
        var nextPos = row
        for (checkPos in row-1 downTo 0){
            if (grid[checkPos][c] == '.' ){
                nextPos = checkPos
            } else{
                break
            }
        }
        replaceRockInRow(row, nextPos, c)
    }

    private fun replaceRockInRow(row: Int, nextPos: Int, c : Int){
        if (nextPos!= row){
            this.grid[row] = grid[row].substring(0, c) + '.'+grid[row].substring(c+1)
            this.grid[nextPos] = grid[nextPos].substring(0, c) + 'O'+grid[nextPos].substring(c+1)
        }
    }

    fun tiltAllSouth() {
        // starting at the bottom -1 all the way to the top
        for (row in grid.size-2 downTo 0){
           tiltSouth(row)
        }
    }

    private fun tiltSouth(row: Int) {
        for (c in 0..<grid[row].length){
            // for each column
            if (grid[row][c]=='O'){
                moveRockSouth(row, c)
            }
        }
    }

    private fun moveRockSouth(row: Int, c: Int) {
        var nextPos = row
        for (checkPos in row+1..<grid.size){
            if (grid[checkPos][c] == '.' ){
                nextPos = checkPos
            } else{
                break
            }
        }
        replaceRockInRow(row, nextPos, c)
    }

    fun tiltAllEast() {
        for (col in grid[0].length-2 downTo 0 ){
            tiltEastCol(col)
        }
    }

    private fun tiltEastCol(col: Int) {
        for (row in 0..<grid.size){
            // for each row
            if (grid[row][col]=='O'){
                moveRockEast(row, col)
            }
        }
    }

    private fun moveRockEast(row: Int, col: Int) {
        var nextPos = col
        for (checkPos in col+1..<grid[0].length){
            if (grid[row][checkPos] == '.' ){
                nextPos = checkPos
            } else{
                break
            }
        }
        replaceRockInColumn(col, nextPos, row)
    }

    fun replaceRockInColumn(col: Int, nextCol: Int, row : Int){
        if (nextCol> col){
            this.grid[row] = grid[row].substring(0, col) + '.'+grid[row].substring(col+1, nextCol) + 'O'+grid[row].substring(nextCol+1)
        }
        else if (nextCol< col){
            this.grid[row] = grid[row].substring(0, nextCol) + 'O'+grid[row].substring(nextCol+1, col) + '.'+grid[row].substring(col+1)
        }
    }


    fun tiltAllWest() {
        for (col in 1..<grid[0].length){
            tiltWestCol(col)
        }
    }

    private fun tiltWestCol(col: Int) {

        for (row in 0..<grid.size){
            // for each row
            if (grid[row][col]=='O'){
                moveRockWest(row, col)
            }
        }
    }

    private fun moveRockWest(row: Int, col: Int) {
        var nextPos = col
        for (checkPos in col-1 downTo 0){
            if (grid[row][checkPos] == '.' ){
                nextPos = checkPos
            } else{
                break
            }
        }
        replaceRockInColumn(col, nextPos, row)
    }

    fun getScore(): Int {
        var total = 0
        grid.forEachIndexed { rowIndex, _ ->
            val rocksOnRow = grid[rowIndex].count { it=='O'}
            val value = grid.size-rowIndex
            total += (rocksOnRow * value)
        }
        return total
    }

    private fun cycleTimes(count: Int): List<Int> {
        val scores = mutableListOf<Int>()
        (1..count).forEach {
            cycle()
            scores.add(getScore())
        }
        return scores.toList()
    }

    private fun findRepetition(scores: List<Int>): MutableList<Int> {
        val rep = mutableListOf<Int>()
        (1..<scores.size).forEach { x ->
            if (scores[x] == scores[0] &&
                scores[x+1]== scores[1] &&
                scores[x+2]== scores[2] &&
                scores[x+3]== scores[3] &&
                scores[x+4]== scores[4] &&
                scores[x+5]== scores[5] &&
                scores[x+6]== scores[6] &&
                scores[x+7]== scores[7] &&
                scores[x+8]== scores[8] &&
                scores[x+9]== scores[9] &&
                scores[x+10]== scores[10] &&
                scores[x+11]== scores[11] &&
                scores[x+12]== scores[12] &&
                scores[x+13]== scores[13] &&
                scores[x+14]== scores[14] &&
                scores[x+15]== scores[15]
            ){
                for (score in 0..<x){
                    rep.add(scores[score])
                }
                return rep
            }
        }
        return rep
    }

    fun findScore(times: Int): Int{
        val sampleStart = 1000
        val sampleSize = 200

        cycleTimes(sampleStart-1)
        val rep = findRepetition(cycleTimes(sampleSize))
        val targetValue = times - sampleStart
        val targetPos = targetValue.mod(rep.size)
        return rep[targetPos]
    }
}