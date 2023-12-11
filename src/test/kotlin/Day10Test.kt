import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Day10Test {

    var sample1 = listOf(
        ".....",
        ".S-7.",
        ".|.|.",
        ".L-J.",
        "....."
    )
    @Test
    fun sample1(){
    val grid = Day10.of(sample1)
        grid.joinPipes()
        var pipe = grid.getStartingPipe();
        assertEquals(2, pipe.prev().x)
        assertEquals(1, pipe.prev().y)

        pipe = grid.getPipe(1, 2)!!
        assertEquals('|', pipe.type)
        assertEquals('S', pipe.prev().type)
        assertEquals('L', pipe.next().type)

        pipe = pipe.next()
        assertEquals('L', pipe.type)
        assertEquals('|', pipe.prev().type)
        assertEquals('-', pipe.next().type)

        pipe = pipe.next()
        assertEquals('-', pipe.type)
        assertEquals('L', pipe.prev().type)
        assertEquals('J', pipe.next().type)

        pipe = pipe.next()
        assertEquals('J', pipe.type)
        assertEquals('|', pipe.prev().type)
        assertEquals('-', pipe.next().type)

        pipe = grid.getPipe(3, 1)!!
        assertEquals('7', pipe.type)
        assertEquals('-', pipe.prev().type)
        assertEquals('|', pipe.next().type)
//        assertEquals(1, grid.startX);
//        assertEquals(1, grid.startY);
//        val startPositions = grid.getStartPositions(1,1)
//        assertEquals(2, startPositions.size)
//        assertTrue(startPositions.contains(Pair(2,1)))
//        assertTrue(startPositions.contains(Pair(1,2)))
    }

    @Test
    fun sample1Navigate() {
        val grid = Day10.of(sample1)
        grid.joinPipes()
        assertEquals(4, grid.navigate())
    }

    @Test
    fun Navigate() {
        val grid = Day10.of(sample1)
        grid.joinPipes()
        assertEquals(4, grid.navigate())
    }
    @Test
    fun part1() {
        val data = Util().readData("day10.txt")
        val grid = Day10.of(data)
        grid.joinPipes()
        assertEquals(6897, grid.navigate())
        grid.display()
    }
    @Test
    fun samplePart2(){
        var grid = Day10.of(listOf(
            "...........",
            ".S-------7.",
            ".|F-----7|.",
            ".||.....||.",
            ".||.....||.",
            ".|L-7.F-J|.",
            ".|..|.|..|.",
            ".L--J.L--J.",
            "..........."
        ))
        grid.joinPipes()
        grid.navigate()
        grid.display()
        grid.startInfection();
        grid.display()
        grid.spreadInfection();
        grid.display()
        grid.spreadInfection();
        grid.display()
        grid.spreadInfection();
        grid.display()
        grid.spreadToEnd()
        grid.display()
        assertEquals(4, grid.getEnclosedTiles())
    }

    @Test
    fun samplePart2GapSpreadUp(){
        var grid = Day10.of(listOf(
            "..........",
            ".S------7.",
            ".|F----7|.",
            ".||....||.",
            ".||....||.",
            ".|L-7F-J|.",
            ".|..||..|.",
            ".L--JL--J.",
            ".........."
        ))
        grid.joinPipes()
        grid.navigate()
        grid.startInfection();
        grid.spreadToEnd()
        grid.display()
        assertEquals(4, grid.getEnclosedTiles())
    }

    @Test
    fun samplePart2GapSpreadLeft(){
        var grid = Day10.of(listOf(
            "..........",
            ".S------7.",
            ".|.F---7|.",
            ".L-J...||.",
            ".F-7...||.",
            ".|.L---J|.",
            ".L------J.",
            ".........."
        ))
        grid.display()
        grid.joinPipes()
        grid.navigate()
        grid.startInfection();
        grid.spreadInfection()
        grid.display()
        grid.spreadInfection()
        grid.display()
        grid.spreadInfection()
        grid.display()

        grid.spreadToEnd()
        grid.display()
        assertEquals(2, grid.getEnclosedTiles())
    }
    @Test
    fun samplePart2Gap2() {
        var grid = Day10.of(
            listOf(
                ".F----7F7F7F7F-7....",
                ".|F--7||||||||FJ....",
                ".||.FJ||||||||L7....",
                "FJL7L7LJLJ||LJ.L-7..",
                "L--J.L7...LJS7F-7L7.",
                "....F-J..F7FJ|L7L7L7",
                "....L7.F7||L7|.L7L7|",
                ".....|FJLJ|FJ|F7|.LJ",
                "....FJL-7.||.||||...",
                "....L---J.LJ.LJLJ..."
            )
        )
        grid.joinPipes()
        grid.navigate()
        grid.startInfection();
        grid.display()
        grid.spreadInfection()
        grid.display()
        grid.spreadInfection()
        grid.display()
        grid.spreadInfection()
        grid.display()
        grid.spreadInfection()
        grid.display()
        grid.spreadInfection()
        grid.display()
        grid.spreadInfection()
        grid.display()
        assertEquals(8, grid.getEnclosedTiles())
    }
    @Test
    fun samplePart2Gap3() {
        var grid = Day10.of(
            listOf(
                "FF7FSF7F7F7F7F7F---7",
                "L|LJ||||||||||||F--J",
                "FL-7LJLJ||||||LJL-77",
                "F--JF--7||LJLJ7F7FJ-",
                "L---JF-JLJ.||-FJLJJ7",
                "|F|F-JF---7F7-L7L|7|",
                "|FFJF7L7F-JF7|JL---7",
                "7-L-JL7||F7|L7F-7F7|",
                "L.L7LFJ|||||FJL7||LJ",
                "L7JLJL-JLJLJL--JLJ.L"
            )
        )
        grid.joinPipes()
        grid.navigate()
        grid.startInfection();
        grid.spreadToEnd()
        grid.display()
        assertEquals(10, grid.getEnclosedTiles())
    }
    @Test
    fun part2() {
        val data = Util().readData("day10.txt")
        val grid = Day10.of(data)
        grid.joinPipes()
        grid.navigate()
        grid.display()
        grid.startInfection()
        grid.spreadToEnd()
        grid.display()

        //681 (Too high)
        //653 too high
        assertEquals(653, grid.getEnclosedTiles())
    }
}