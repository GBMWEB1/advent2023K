
class Day18 {
    enum class Direction() {
        UP,
        DOWN,
        LEFT,
        RIGHT;

        companion object{
            fun of(ch: Char): Direction {
                return when(ch){
                    'U'-> UP
                    'D'->DOWN
                    'L'->LEFT
                    'R'->RIGHT
                    else -> throw Error("Invalid $ch")
                }
            }
        }
    }

    data class Step(val direction: Direction, val distance:Int){
        companion object{
            fun of(s: String): Step{
                val direction = Direction.of(s[0])
                val distance = s.substringAfter(" ").substringBefore(" (").toInt()
                return Step(direction, distance)
            }

            fun fromList(data: List<String>): List<Step>{
                return data.map { of(it) }
            }
        }
    }

    class Grid() {
        var currentX =0
        var currentY =0

        val cells: MutableList<MutableList<Char>> = mutableListOf(mutableListOf('#'))

        fun followStep(step: Step) {
            repeat(step.distance) {
                when (step.direction) {
                    Direction.RIGHT -> digRight()
                    Direction.LEFT -> digLeft()
                    Direction.UP -> digUp()
                    Direction.DOWN -> digDown()
                }
            }
        }

        private fun digUp() {
            currentY--;
            if (currentY==-1){
                cells.add(0,createRow())
                currentY=0
            }
            cells[currentY][currentX] = '#'
        }

        private fun digDown() {
            currentY++;
            if (cells.size==currentY){
                cells.add(createRow())
            }
            cells[currentY][currentX] = '#'
        }

        private fun createRow(): MutableList<Char> {
            val size = cells[0].size
            var blankRow = List(size,{
                '.'
            } )
            return blankRow.toMutableList();
        }

        private fun digRight() {
            currentX++;
            val row = cells[currentY]
            if (row.size == currentX ){
                addColumnToEnd()
            }
            cells[currentY][currentX] = '#'
        }

        private fun digLeft() {
            currentX--;
            val row = cells[currentY]
            if (currentX ==-1){
                addColumnToStart()
                currentX=0
            }
            cells[currentY][currentX] = '#'
        }

        private fun addColumnToStart() {
            cells.forEach { it.add(0,'.') }
        }

        private fun addColumnToEnd() {
            cells.forEach { it.add('.') }
        }

        fun display() {
            cells.forEach {
                println()
                it.forEach { print(it) }
            }

        }

        fun expand() {
            addColumnToEnd()
            addColumnToStart()
            cells.add(0,createRow())
            cells.add(createRow())
        }

        fun markOuter() {
            // starting in the top left hand corner
            // expand in 4 directions
            var fill = listOf(Pair(0,0))
            while (fill.isNotEmpty()){
                fill = fill.flatMap { mark(it) }
            }
        }

        private fun mark(current: Pair<Int, Int>): List<Pair<Int, Int>> {
            // using the current row column
            var row = current.first
            var col = current.second
            var fills = mutableListOf<Pair<Int, Int>>()
            if (row>0 && cells[row-1][col] == '.'){
                 fills.add(Pair(row-1, col))
            }
            if (row<cells.size-1 && cells[row+1][col] == '.'){
                fills.add(Pair(row+1, col))
            }
            if (col>0 && cells[row][col-1] == '.'){
                fills.add(Pair(row, col-1))
            }
            if (col<cells[row].size-1 && cells[row][col+1] == '.'){
                fills.add(Pair(row, col+1))
            }
            fills.forEach { cells[it.first][it.second]='-'}
            return fills
        }

        fun fullInner() {
            for (row in 0.. cells.size-1){
                for (col in 0.. cells[row].size-1){
                    if (cells[row][col]=='.'){
                        cells[row][col]='#'
                    }
                }
            }
        }

        fun getLava(): Int {
            return cells.flatten().count { it == '#' }
        }
    }

}