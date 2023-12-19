import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day18Test {

    @Test
    fun sample(){
        val grid = Day18.Grid()
        grid.followStep(Day18.Step.of("R 6 (#70c710)"))
        assertEquals(1, grid.cells.size)
        assertEquals(7, grid.cells[0].size)

        grid.followStep(Day18.Step.of("D 5 (#0dc571)"))
        assertEquals(6, grid.cells.size)
        assertEquals(7, grid.cells[1].size)

        grid.followStep(Day18.Step.of("L 2 (#5713f0)"))
        grid.followStep(Day18.Step.of("D 2 (#d2c081))"))
        grid.followStep(Day18.Step.of("R 2 (#59c680)"))
        grid.followStep(Day18.Step.of("D 2 (#411b91)"))
        grid.followStep(Day18.Step.of("L 5 (#8ceee2)"))
        grid.display()
    }

    @Test
    fun fullSample(){
        val data = Util().readData("day18Sample.txt")
        val grid = Day18.Grid()
        data.forEach {
            grid.followStep(Day18.Step.of(it))
        }
        grid.display()
        grid.expand()
        grid.display()
        grid.markOuter()
        grid.display()
        grid.fullInner()
        grid.display()
        assertEquals(62, grid.getLava())

    }


    @Test // takes 48 seconds to run
    fun part1() {
        val data = Util().readData("day18.txt")
        val grid = Day18.Grid()
        data.forEach {
            grid.followStep(Day18.Step.of(it))
        }
        grid.expand()
        grid.markOuter()
        grid.fullInner()
        assertEquals(50465, grid.getLava())
    }

}