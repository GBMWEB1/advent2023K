import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day18Test {

    val sample = listOf(
        ".|...\\....",
        "|.-.\\.....",
        ".....|-...",
        "........|.",
        "..........",
        ".........\\",
        "..../.\\\\..",
        ".-.-/..|..",
        ".|....-|.\\",
        "..//.|...."
    )
    @Test
    fun sample(){

    }


    @Test // takes 48 seconds to run
    fun part1() {
        val data = Util().readData("day16.txt")
        assertEquals(51, Day16.findHighestEnergy(data))
    }

}