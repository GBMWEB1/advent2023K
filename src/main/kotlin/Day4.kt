import kotlin.math.pow

class Day4(lines: List<String>) {
    private val cards = lines.map { Card.fromLine(it) }

    data class Card(val matches: Int, var count: Int = 1){
        fun incrementCount(valueToIncrement: Int){
            count += valueToIncrement
        }

        fun getPoints(): Int {
            return 2.0.pow(matches.toDouble() - 1).toInt()
        }

        companion object{
            fun fromLine(line: String): Card {
                val winningNumbers = line
                    .substringAfter(":")
                    .substringBefore("|")
                    .split(" ")
                    .filter { it.isNotEmpty() }
                    .map { it.toInt() }
                val cardNumbers = line
                    .substringAfter("|")
                    .split(" ")
                    .filter { it.isNotEmpty() }
                    .map { it.toInt() }
                return Card(winningNumbers.intersect(cardNumbers).size)
            }
        }
    }

    // part 1
    fun sumScore(): Int{
        return cards.sumOf { it.getPoints() }
    }

    fun part2(): Int {
        cards.forEachIndexed { index, card ->
            for (x in 1..card.matches ){
                cards[index+x].incrementCount(card.count)
            }
        }
        return cards.sumOf { it.count }
    }
}