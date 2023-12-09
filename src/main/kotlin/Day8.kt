class Day8(private val instructions:  String, private val nodes : Map<String, Pair<String, String>>) {

    companion object {
        fun of(data: List<String>): Day8 {
            val instructstion = data[0]
            val nodes = mutableMapOf<String, Pair<String, String>>()

            data.drop(2).forEach {
                nodes[it.substringBefore(" =")] = Pair(
                    it.substringAfter("(").substringBefore(", "),
                    it.substringAfter(", ").substringBefore(")") )
            }
            return Day8(instructstion, nodes)
        }
    }

    // Part 1
    fun navigateToEnd(startingNode: String = "AAA"): Int{
        var steps = 0
        var node = startingNode
        while (!node.endsWith("Z")){
            val instructionIndex = steps.mod(instructions.length)
            node = navigate(node, instructions[instructionIndex])
            steps++
        }
        return steps
    }

    private fun navigate(node: String, instruction: Char): String {
        if (instruction=='L'){
            return nodes[node]!!.first
        }
        return nodes[node]!!.second
    }

    /// Part 2
    fun navigateGhosts(): Long {
        return getStartingNodes()
            .map { navigateToEnd(it).toLong() }
            .let { findLeastCommonNumber(it) }
    }

    fun getStartingNodes(): List<String> {
        return nodes.keys.filter { it.endsWith("A") }
    }

    private fun findLeastCommonNumber(numbers : List<Long>): Long{
        for (x in numbers.max() .. Long.MAX_VALUE step numbers.max()) {
            if (numbers.all { x.mod(it) ==0L }){
                return x
            }
        }
        return 0
    }
}