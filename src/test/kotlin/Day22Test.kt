import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day22Test {

    val sample = listOf(
        "1,0,1~1,2,1",//A
        "0,0,2~2,0,2",//B
        "0,2,3~2,2,3",//C
        "0,0,4~0,2,4",//D
        "2,0,5~2,2,5",//E
        "0,1,6~2,1,6",//F
        "1,1,8~1,1,9",//G
    )

    @Test
    fun canDropTest(){
        val brick1 = Day22.Brick.ofLine("0,0,1~0,0,1")
        val brick2 = Day22.Brick.ofLine("0,0,2~0,0,2")
        val brick3 = Day22.Brick.ofLine("0,0,3~0,0,3")
        var stack = Day22(listOf(brick1, brick3))
        assertTrue(stack.canDrop(brick3))
        stack = Day22(listOf(brick1, brick2))
        assertFalse(stack.canDrop(brick2))
        stack.linkBricks()
        assertTrue(brick1.supports.contains(brick2))
        assertTrue(brick2.supportedBy.contains(brick1))
    }

    @Test
    fun canDropDropGap(){
        val brick1 = Day22.Brick.ofLine("0,0,1~0,0,1")
        val brick2 = Day22.Brick.ofLine("1,1,2~1,1,2")
        var stack = Day22(listOf(brick1, brick2))
        assertTrue(stack.canDrop(brick2))
        brick2.drop()
        assertEquals(1, brick2.start[2])
        assertEquals(1, brick2.end[2])
    }

    @Test
    fun supportTest(){
        val brick1 = Day22.Brick.ofLine("0,0,1~0,0,1")
        val brick2 = Day22.Brick.ofLine("1,0,1~1,0,1")
        val brick3 = Day22.Brick.ofLine("0,0,2~1,1,2")
        var stack = Day22(listOf(brick1, brick2, brick3))
        stack.linkBricks()
        assertTrue(brick1.supports.contains(brick3))
        assertTrue(brick2.supports.contains(brick3))
        assertTrue(brick3.supportedBy.contains(brick1))
        assertTrue(brick3.supportedBy.contains(brick2))
    }

    @Test
    fun sample(){
        //val data = Util().readData("day21Sample.txt")
        var stack = Day22(Day22.Brick.of(sample))
        assertEquals(9, stack.bricks.maxOf { it.end[2] })
        stack.settle()
        assertEquals(6, stack.bricks.maxOf { it.end[2] })
        stack.linkBricks()
        assertEquals(5, stack.countBricksToBeDisintigrated())
    }

    @Test
    fun part1() {
        val data = Util().readData("day22.txt")
        var stack = Day22(Day22.Brick.of(data))
        assertEquals(256, stack.bricks.maxOf { it.end[2] })
        stack.settle()
        assertEquals(130, stack.bricks.maxOf { it.end[2] })
        stack.linkBricks()
        assertEquals(428, stack.countBricksToBeDisintigrated())
    }

    // if I remove block x, find all its supporting bricks
    // if they do not support any other blocks then they can be removed(cascade)
    // for all other supporting block, ask if they
    // If the are supported by that brick
    // Carry on up the tree.
    // If you find a brick. Use a flat map

    @Test
    fun part2Sample() {

        var stack = Day22(Day22.Brick.of(sample))
        stack.settle()
        stack.linkBricks()

        assertEquals(7, stack.countCascadingBricks())
    }

    @Test
    fun part2() {
        val data = Util().readData("day22.txt")
        var stack = Day22(Day22.Brick.of(data))
        stack.settle()
        stack.linkBricks()
        //1173 is too low
        assertEquals(7, stack.countCascadingBricks())
    }
}