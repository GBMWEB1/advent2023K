import Day21.Companion.interpolate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day21Test {


    @Test
    fun sample(){
        val sample = Util().readData("day21Sample.txt")
        val grid = Day21.Grid.of(sample)
        assertEquals(5,grid.startY)
        assertEquals(5,grid.startX)
        grid.display()
        val steps = Day21.takeStep(grid, Day21.Step(grid.startX, grid.startY))
        assertEquals(2,steps.size)
        grid.display(steps)
    }

    @Test
    fun sample2Steps(){
        val sample = Util().readData("day21Sample.txt")
        val grid = Day21.Grid.of(sample)
        val steps = Day21.takeSteps(grid, 2)
        assertEquals(4,steps.size)
        grid.display(steps)
    }

    @Test
    fun sample3Steps(){
        val sample = Util().readData("day21Sample.txt")
        val grid = Day21.Grid.of(sample)
        val steps = Day21.takeSteps(grid, 3)
        assertEquals(6,steps.size)
        grid.display(steps)
    }

    @Test
    fun sample6Steps(){
        val sample = Util().readData("day21Sample.txt")
        val grid = Day21.Grid.of(sample)
        val steps = Day21.takeSteps(grid, 6)
        assertEquals(16,steps.size)
        grid.display(steps)
    }

    @Test
    fun part1(){
        val sample = Util().readData("day21.txt")
        val grid = Day21.Grid.of(sample)
        val steps = Day21.takeSteps(grid, 64)
        assertEquals(3639,steps.size)
    }

//    @Test
//    fun part2(){
//        val sample = Util().readData("day21.txt")
//        val grid = Day21.Grid.of(sample)
//        val steps = Day21.takeSteps(grid, 26501365)
//        grid.display(steps)
//
//        assertEquals(3639,steps.size)
//    }

    @Test
    fun sampleExpandNorthGrid(){
        val sample = Util().readData("day21Sample.txt")
        val grid = Day21.Grid.of(sample)
        val steps = Day21.takeSteps(grid, 1)
        grid.expandWest(steps)
        grid.expandNorth(steps)
        grid.display(steps)
    }

    @Test
    fun sampleExpandSouthGrid(){
        val sample = Util().readData("day21Sample.txt")
        val grid = Day21.Grid.of(sample)
        val steps = Day21.takeSteps(grid, 6)
        grid.display(steps)
        grid.expandWest(steps)
        grid.expandSouth()
        grid.display(steps)
    }

    @Test
    fun sampleExpandEast(){
        val sample = Util().readData("day21Sample.txt")
        val grid = Day21.Grid.of(sample)
        val steps = Day21.takeSteps(grid, 6)
        grid.expandSouth()
        grid.display(steps)
        grid.expandEast()
        grid.display(steps)
    }

    @Test
    fun sampleExpandWest(){
        val sample = Util().readData("day21Sample.txt")
        val grid = Day21.Grid.of(sample)
        val steps = Day21.takeSteps(grid, 6)
        grid.expandSouth()
        grid.display(steps)
        grid.expandWest(steps)
        grid.display(steps)
    }

    @Test
    fun sample10Steps(){
        val sample = Util().readData("day21Sample.txt")
        val grid = Day21.Grid.of(sample)
        val steps = Day21.takeSteps(grid, 10)
        grid.display(steps)
        assertEquals(50,steps.size)
        grid.display(steps)
    }

    @Test
    fun sample50Steps(){
        val sample = Util().readData("day21Sample.txt")
        val grid = Day21.Grid.of(sample)
        val steps = Day21.takeSteps(grid, 50)
        assertEquals(1594,steps.size)
    }

    @Test
    fun sample100steps(){
        val sample = Util().readData("day21Sample.txt")
        val grid = Day21.Grid.of(sample)

        // size of grid is 11
        // 39/50 = 50
        // 45/56/67/78/89/100 = 100 (mod 1)
        // 46/57/68/79/90  = 101 (mod 2)
        // 38/49/60/71 = 500 (mode 5)
        val target = 100

        // start the sample at a multiple of the grid (with a mod of the board size)
        val start = (target%11)+(grid.template.size*4)

        val val1 = Day21.takeSteps(grid, start).size
        val val2 = Day21.takeSteps(grid, start+grid.template.size).size
        val val3 = Day21.takeSteps(grid, start+grid.template.size+grid.template.size).size

        val values = listOf(Pair(start, val1), Pair(start+grid.template.size, val2), Pair(start+grid.template.size+grid.template.size, val3))
        val result = interpolate(values,target).toLong()
        assertEquals(6536L, result)
    }

    @Test
    fun part2() {
        val sample = Util().readData("day21.txt")
        val grid = Day21.Grid.of(sample)

        val val1 = Day21.takeSteps(grid, 65).size
        val val2 = Day21.takeSteps(grid, 196).size
        val val3 = Day21.takeSteps(grid, 327).size

        // magic numbers 65, 196, 327 (65, 65+131 + 65+(131*2))
        val values = listOf(Pair(65, val1), Pair(196, val2), Pair(327, val3))

        val result = interpolate(values, 26_501_365).toLong()
        assertEquals(604592315958630, result)
    }


}