import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day16Test {

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
        var grid = Day16.Grid.from(sample)
        assertEquals(10, grid.tiles.size)

        grid.display()
        grid.navigateToEnd()
        grid.display()
        grid.displayEnergy()
        assertEquals(46, grid.getEnergyCount())

        // part 2
        assertEquals(51, Day16.findHighestEnergy(sample))


    }

    @Test
    fun testSpace(){
        var grid = Day16.Grid.from(listOf(
            "...",
            "...",
            "..."
        ))
        var beam = Day16.Beam(1, 1, Day16.Direction.EAST)
        beam = beam.navigate(grid).first()
        assertEquals(1,beam.row)
        assertEquals(2,beam.col)

        beam = Day16.Beam(1, 1, Day16.Direction.SOUTH)
        beam = beam.navigate(grid).first()
        assertEquals(2,beam.row)
        assertEquals(1,beam.col)

        beam = Day16.Beam(1, 1, Day16.Direction.NORTH)
        beam = beam.navigate(grid).first()
        assertEquals(0,beam.row)
        assertEquals(1,beam.col)

        beam = Day16.Beam(1, 1, Day16.Direction.WEST)
        beam = beam.navigate(grid).first()
        assertEquals(1,beam.row)
        assertEquals(0,beam.col)
    }

    @Test
    fun testVerticalSplit(){
        var grid = Day16.Grid.from(listOf(
            "...",
            ".|.",
            "..."
        ))
        var beam = Day16.Beam(1, 1, Day16.Direction.NORTH)
        beam = beam.navigate(grid).first()
        assertEquals(0,beam.row)
        assertEquals(1,beam.col)
        assertEquals(Day16.Direction.NORTH,beam.direction)

        beam = Day16.Beam(1, 1, Day16.Direction.SOUTH)
        beam = beam.navigate(grid).first()
        assertEquals(2,beam.row)
        assertEquals(1,beam.col)
        assertEquals(Day16.Direction.SOUTH,beam.direction)

        beam = Day16.Beam(1, 1, Day16.Direction.EAST)
        var result = beam.navigate(grid)
        assertEquals(0,result[0].row)
        assertEquals(1,result[0].col)
        assertEquals(Day16.Direction.NORTH,result[0].direction)

        assertEquals(2,result[1].row)
        assertEquals(1,result[1].col)
        assertEquals(Day16.Direction.SOUTH,result[1].direction)

        beam = Day16.Beam(1, 1, Day16.Direction.WEST)
        result = beam.navigate(grid)
        assertEquals(0,result[0].row)
        assertEquals(1,result[0].col)
        assertEquals(Day16.Direction.NORTH,result[0].direction)
        assertEquals(2,result[1].row)
        assertEquals(1,result[1].col)
        assertEquals(Day16.Direction.SOUTH,result[1].direction)
    }

    @Test
    fun testHorizontalSplit(){
        var grid = Day16.Grid.from(listOf(
            "...",
            ".-.",
            "..."
        ))
        var beam = Day16.Beam(1, 1, Day16.Direction.NORTH)
        var result = beam.navigate(grid)
        assertEquals(1,result[0].row)
        assertEquals(0,result[0].col)
        assertEquals(Day16.Direction.WEST,result[0].direction)
        assertEquals(1,result[1].row)
        assertEquals(2,result[1].col)
        assertEquals(Day16.Direction.EAST,result[1].direction)

        beam = Day16.Beam(1, 1, Day16.Direction.SOUTH)
        result = beam.navigate(grid)
        assertEquals(1,result[0].row)
        assertEquals(0,result[0].col)
        assertEquals(Day16.Direction.WEST,result[0].direction)
        assertEquals(1,result[1].row)
        assertEquals(2,result[1].col)
        assertEquals(Day16.Direction.EAST,result[1].direction)

        beam = Day16.Beam(1, 1, Day16.Direction.EAST)
        result = beam.navigate(grid)
        assertEquals(1,result[0].row)
        assertEquals(2,result[0].col)
        assertEquals(Day16.Direction.EAST,result[0].direction)

        beam = Day16.Beam(1, 1, Day16.Direction.WEST)
        result = beam.navigate(grid)
        assertEquals(1,result[0].row)
        assertEquals(0,result[0].col)
        assertEquals(Day16.Direction.WEST,result[0].direction)
    }

    @Test
    fun testMirrorUp(){
        var grid = Day16.Grid.from(listOf(
            "...",
            "./.",
            "..."
        ))
        var beam = Day16.Beam(1, 1, Day16.Direction.NORTH)
        var result = beam.navigate(grid)
        assertEquals(1,result[0].row)
        assertEquals(2,result[0].col)
        assertEquals(Day16.Direction.EAST, result[0].direction)

        beam = Day16.Beam(1, 1, Day16.Direction.SOUTH)
        result = beam.navigate(grid)
        assertEquals(1,result[0].row)
        assertEquals(0,result[0].col)
        assertEquals(Day16.Direction.WEST, result[0].direction)


        beam = Day16.Beam(1, 1, Day16.Direction.EAST)
        result = beam.navigate(grid)
        assertEquals(0,result[0].row)
        assertEquals(1,result[0].col)
        assertEquals(Day16.Direction.NORTH, result[0].direction)

        beam = Day16.Beam(1, 1, Day16.Direction.WEST)
        result = beam.navigate(grid)
        assertEquals(2,result[0].row)
        assertEquals(1,result[0].col)
        assertEquals(Day16.Direction.SOUTH, result[0].direction)
    }

    @Test
    fun testMirrorDown(){
        var grid = Day16.Grid.from(listOf(
            "...",
            ".\\.",
            "..."
        ))
        var beam = Day16.Beam(1, 1, Day16.Direction.NORTH)
        var result = beam.navigate(grid)
        assertEquals(1,result[0].row)
        assertEquals(0,result[0].col)
        assertEquals(Day16.Direction.WEST, result[0].direction)

        beam = Day16.Beam(1, 1, Day16.Direction.SOUTH)
        result = beam.navigate(grid)
        assertEquals(1,result[0].row)
        assertEquals(2,result[0].col)
        assertEquals(Day16.Direction.EAST, result[0].direction)

        beam = Day16.Beam(1, 1, Day16.Direction.EAST)
        result = beam.navigate(grid)
        assertEquals(2,result[0].row)
        assertEquals(1,result[0].col)
        assertEquals(Day16.Direction.SOUTH, result[0].direction)

        beam = Day16.Beam(1, 1, Day16.Direction.WEST)
        result = beam.navigate(grid)
        assertEquals(0,result[0].row)
        assertEquals(1,result[0].col)
        assertEquals(Day16.Direction.NORTH, result[0].direction)
    }

    @Test // takes 48 seconds to run
    fun part1() {
        val data = Util().readData("day16.txt")
        var grid = Day16.Grid.from(data)
        grid.navigateToEnd()
        grid.displayEnergy()
        assertEquals(7951, grid.getEnergyCount())
        assertEquals(51, Day16.findHighestEnergy(data))
    }

}