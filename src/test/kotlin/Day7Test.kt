import Day5.Range
import Day7.Hand.Companion
import Day7.HandType.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day7Test {

    val hands = listOf<String>(
        "AAAAA 0",// Five
        "ABAAA 0", // Four
        "23332 0", // Full house
        "TTT98 0", // Three of kind
        "23432 0", // two pair
        "A23A4 0", // one pair
        "23456 0"
    )



    @Test
    fun fiveOfKind(){
        assertEquals(FIVE_OF_KIND, Day7.Hand("AAAAA", 0).type)
    }

    @Test
    fun four(){
        assertEquals(FOUR_OF_KIND, Day7.Hand("ABAAA", 0).type)
    }

    @Test
    fun fullHouse(){
        assertEquals(FULL_HOUSE, Day7.Hand("23332", 0).type)
    }

    @Test
    fun three(){
        assertEquals(THREE_OF_KIND, Day7.Hand("TTT98", 0).type)
    }

    @Test
    fun twoPair(){
        assertEquals(TWO_PAIR, Day7.Hand("23432", 0).type)
    }
    @Test
    fun onePair(){
        assertEquals(ONE_PAIR, Day7.Hand("A23A4", 0).type)
    }
    @Test
    fun highCard(){
        assertEquals(HIGH_CARD, Day7.Hand("23456", 0).type)
    }

//    @Test
//    fun sortCards(){
//        assertEquals(hands, Day7.sortHands(hands.reversed()).map { it.cards })
//    }

    val sample = listOf<String>(
        "32T3K 765",
        "T55J5 684",
        "KK677 28",
        "KTJJT 220",
        "QQQJA 483"
    )
    @Test fun samplePart1a(){
        var cards = Day7.Hand.of(sample)
        assertEquals(1, cards.find { it.cards.equals("32T3K") }?.rank)
        assertEquals(2, cards.find { it.cards.equals("KTJJT") }?.rank)
        assertEquals(3, cards.find { it.cards.equals("KK677") }?.rank)
        assertEquals(4, cards.find { it.cards.equals("T55J5") }?.rank)
        assertEquals(5, cards.find { it.cards.equals("QQQJA") }?.rank)

        assertEquals(6440, Day7.Hand.of(sample).sumOf { it.getBidScore() })
    }
    @Test fun samplePart1Debug() {

        val hands = Day7.Hand.of(listOf("AAAA2 0", "AAAA3 0"));
        var card1 = hands[0].getScore(4);
        var card2 = hands[1].getScore(4);

        assertEquals(1, hands[0].rank)
        assertEquals(2, hands[1].rank)


    }


        @Test
    fun part1() {
        val data = Util().readData("day7.txt")
        val cards = Day7.Hand.of(data);
        assertEquals(247961593, cards.sumOf { it.getBidScore() })
    }

    @Test
    fun part2sample() {
        // Five of a kind
    //    assertEquals(FIVE_OF_KIND, Companion.getTypeUsingJokers(Day7.Hand("AAAAA", 0, true)))
   //     assertEquals(FIVE_OF_KIND, Companion.getTypeUsingJokers(Day7.Hand("AAAAJ", 0, true)))
        assertEquals(FIVE_OF_KIND, Day7.Hand("AAAJJ", 0, true).getTypeUsingJokers())
        assertEquals(FIVE_OF_KIND, Day7.Hand("AAJJJ", 0, true).getTypeUsingJokers())
        assertEquals(FIVE_OF_KIND, Day7.Hand("JJJJJ", 0, true).getTypeUsingJokers())
        assertEquals(FIVE_OF_KIND, Day7.Hand("AJJJJ", 0, true).getTypeUsingJokers())



//        // 4 of a kind
        assertEquals(FOUR_OF_KIND, Day7.Hand("AAAAB", 0, true).getTypeUsingJokers())
        assertEquals(FOUR_OF_KIND, Day7.Hand("AAAJB", 0, true).getTypeUsingJokers())
        assertEquals(FOUR_OF_KIND, Day7.Hand("AAJJB", 0, true).getTypeUsingJokers())
        assertEquals(FOUR_OF_KIND, Day7.Hand("JJJAB", 0, true).getTypeUsingJokers())

        // FUll Houses
        assertEquals(FULL_HOUSE, Day7.Hand("AABBB", 0, true).getTypeUsingJokers())
        assertEquals(FULL_HOUSE, Day7.Hand("AABBJ", 0, true).getTypeUsingJokers())

        // THREE of a kind
        assertEquals(THREE_OF_KIND, Day7.Hand("AAA1B", 0, true).getTypeUsingJokers())
        assertEquals(THREE_OF_KIND, Day7.Hand("AAJ23", 0, true).getTypeUsingJokers())
        assertEquals(THREE_OF_KIND, Day7.Hand("234JJ", 0, true).getTypeUsingJokers())

        assertEquals(ONE_PAIR, Day7.Hand("AA123", 0, true).getTypeUsingJokers())
        assertEquals(ONE_PAIR, Day7.Hand("2345J", 0, true).getTypeUsingJokers())
    }

    @Test fun samplePart2a(){
        var cards = Day7.Hand.of(sample, true)
        assertEquals(1, cards.find { it.cards.equals("32T3K") }?.rank)
        assertEquals(5, cards.find { it.cards.equals("KTJJT") }?.rank)
        assertEquals(2, cards.find { it.cards.equals("KK677") }?.rank)
        assertEquals(3, cards.find { it.cards.equals("T55J5") }?.rank)
        assertEquals(4, cards.find { it.cards.equals("QQQJA") }?.rank)

        assertEquals(5905, Day7.Hand.of(sample, true).sumOf { it.getBidScore() })
    }
    @Test
    fun part2() {
        val data = Util().readData("day7.txt")
        val cards = Day7.Hand.of(data, true);
        assertEquals(248750699, cards.sumOf { it.getBidScore() })
    }
}