import Day7.HandType.*
import Day7.HandType.HIGH_CARD
import java.lang.Error

class Day7 {
    enum class HandType {
        FIVE_OF_KIND,
        FOUR_OF_KIND,
        FULL_HOUSE,
        THREE_OF_KIND,
        TWO_PAIR,
        ONE_PAIR,
        HIGH_CARD
    }

    data class Hand(val cards: String, val bid: Int, val useJokers : Boolean = false){
        var type = calcType()
        var rank = 0

        private fun calcType(): HandType{
            val count = countCards()
            if (count.any { it.value==5 }) return FIVE_OF_KIND
            if (count.any { it.value==4 }) return FOUR_OF_KIND
            if (count.any { it.value==3 } && count.any { it.value==2 }) return FULL_HOUSE
            if (count.any { it.value==3 } ) return THREE_OF_KIND
            if (count.count { it.value==2 } == 2) return TWO_PAIR
            if (count.any { it.value==2 }) return ONE_PAIR
            return HIGH_CARD
        }

        private fun countCards(): Map<Char, Int>{
            var cardsToCount = cards
            if (useJokers){
                cardsToCount = cardsToCount.filter { it!='J' }
            }
            return cardsToCount.groupingBy { it }.eachCount()
        }

        fun getScore(pos: Int): Int{
            var cardValues = "AKQJT98765432"
            if (useJokers){
                cardValues = "AKQT98765432J"
            }
            return  cardValues.indexOf(cards[pos])
        }


        fun getTypeUsingJokers(): HandType{
            when (cards.count { it == 'J' }) {
                0 -> return type
                1 -> {
                    if (type == FOUR_OF_KIND) return FIVE_OF_KIND
                    if (type == THREE_OF_KIND) return FOUR_OF_KIND
                    if (type == TWO_PAIR) return FULL_HOUSE
                    if (type == ONE_PAIR) return THREE_OF_KIND
                    if (type == HIGH_CARD) return ONE_PAIR
                }
                2 -> {
                    if (type == THREE_OF_KIND) return FIVE_OF_KIND
                    if (type == FULL_HOUSE) return FIVE_OF_KIND
                    if (type == ONE_PAIR) return FOUR_OF_KIND
                    if (type == HIGH_CARD) return THREE_OF_KIND
                }
                3 -> {
                    if (type == ONE_PAIR) return FIVE_OF_KIND
                    if (type == HIGH_CARD) return FOUR_OF_KIND
                }
                5, 4 -> return FIVE_OF_KIND
            }
            throw Error("Unknown type using jokers $cards")
        }

        fun getBidScore(): Int {
            return bid * rank
        }

        companion object{
            fun of (input: List<String>, useJokers: Boolean = false): List<Hand>{
                val hands = input.map {
                    Hand(it.substringBefore(" "),it.substringAfter(" ").toInt(), useJokers )
                }
                setRanks(hands, useJokers)
                return hands
            }

            private fun setRanks(hands: List<Hand>, ignoreJokers: Boolean = false){

                var sorted =
                    hands.sortedWith(
                        compareBy<Hand> { it.type }
                            .thenBy { it.getScore(0) }
                            .thenBy { it.getScore(1) }
                            .thenBy { it.getScore(2) }
                            .thenBy { it.getScore(3) }
                            .thenBy { it.getScore(4) })

                if (ignoreJokers){
                    sorted =
                        hands.sortedWith(
                            compareBy<Hand> { it.getTypeUsingJokers() }
                                .thenBy { it.getScore(0) }
                                .thenBy { it.getScore(1) }
                                .thenBy { it.getScore(2) }
                                .thenBy { it.getScore(3) }
                                .thenBy { it.getScore(4) })
                }

                sorted.forEachIndexed { index, hand ->
                    hand.rank = hands.size - index
                }
            }
        }
    }
}