class Day4(private val lines: List<String>) {

    data class Card(val winningNumbers: List<Int>, val numbers: List<Int>, val matches: Int){

        companion object{
            fun fromLine(line: String): Card {
                val winningNumbers = line.substringAfter(": ").substringBefore(" |").split(" ")
                    .filter { it.length>0 }
                    .map { it.toInt() }
                val cardNumbers = line.substringAfter("| ").split(" ")
                    .filter { it.length>0 }
                    .map { it.toInt() }
                val score = winningNumbers.intersect(cardNumbers)
                return Card(winningNumbers, cardNumbers, score.size);
            }
        }
    }

    companion object{

        // part 1
        fun getPoints(card: String): Int {
            val card = Card.fromLine(card);

            if (card.matches==0){
                return 0;
            }
            var total = 1;
            for (fred in  2..card.matches){
                total= total*2
            }
            return total;
        }
        fun sumScore(cards: List<String>): Int{
            return cards.sumOf { getPoints(it) }
        }

        fun part2(data: List<String>): Int {
            val cards = data.map { Card.fromLine(it) }
            // totals contain 1 of each card
            val totals = cards.map { 1 }.toIntArray()

            cards.forEachIndexed { index, card ->
                val score = card.matches;
                if (score>0) {
                    for (nextIndex in index+1..index+score ){
                        val numberOfThisCard = totals[index]
                        val nextScore = totals.get(nextIndex)+numberOfThisCard
                        totals.set(nextIndex, nextScore)
                    }
                }
            }
            return totals.sum()
        }

    }


}