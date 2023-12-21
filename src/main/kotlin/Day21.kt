class Day21 {

    data class Step(var x:Int, var y:Int)


    class Grid(var garden: List<String>, val startX:Int, val startY: Int){
        val template = garden.toMutableList().toList()
        fun display(steps: Set<Step> = setOf()) {

            for (y in garden.indices){
                println()
                for (x in 0..<garden[y].length){
                    val plot = garden[y][x]
                    if (steps.toList().contains(Step(x,y))){
                        print('O')
                    } else {
                        print(plot)
                    }
                }
            }

        }

        fun expandNorth(steps: Set<Step>) {

            val times = garden[0].length / template.size

            val newGarden = template.toMutableList()
            repeat(times-1){
                for (y in template.indices){
                   newGarden[y] = newGarden[y] + template[y]
                }
            }
            newGarden.addAll(garden)

            steps.forEach { it.y += template.size }
            garden = newGarden.toList()
        }

        fun expandSouth() {
            val times = garden[0].length / template.size

            val southGarden = template.toMutableList()
            repeat(times-1){
                for (y in template.indices){
                    southGarden[y] = southGarden[y] + template[y]
                }
            }
            val newGarden = garden.toMutableList()

            newGarden.addAll(southGarden)
            garden = newGarden.toList()
        }

        fun expandEast() {
            val newGarden =
                garden.toMutableList()

            for (y in garden.indices){
                newGarden[y]= newGarden[y] + template[y.mod(template.size)]
            }
            garden = newGarden.toList()
        }

        fun expandWest(steps: Set<Step>) {
            val newGarden =
                garden.toMutableList()

            for (y in 0..<newGarden.size){
                newGarden[y]= template[y.mod(template.size)]+newGarden[y]
            }

            steps.forEach { it.x += template[0].length }
            garden = newGarden.toList()
        }


        companion object{
            fun of(data: List<String>): Grid{
                val garden = data.toMutableList()
                val startingY = data.indexOfFirst { it.contains("S") }
                val startingX = data[startingY].indexOfFirst { it=='S' }
                garden[startingY] = garden[startingY].substringBefore("S") + "." + garden[startingY].substring(startingX+1)
                return Grid(garden.toList(), startingX, startingY)
            }
        }
    }


    companion object {
        fun takeStep(grid: Grid, step: Step): Set<Step> {
            val steps = mutableSetOf<Step>()

            if (grid.garden[step.y][step.x - 1] != '#') {
                steps.add(Step(step.x - 1, step.y))
            }

            if (grid.garden[step.y][step.x + 1] != '#') {
                steps.add(Step(step.x + 1, step.y))
            }
            // can go north
            if (grid.garden[step.y - 1][step.x] != '#') {
                steps.add(Step(step.x, step.y - 1))
            }
            // can go south
            if (grid.garden[step.y + 1][step.x] != '#') {
                steps.add(Step(step.x, step.y + 1))
            }
            return steps
        }

        fun interpolate(data : List<Pair<Int, Int>>, target:Int): Double {
            var result = 0.0

            for (i in data.indices)
            {
                // Compute individual terms of above formula
                var term = data[i].second.toDouble()
                for (j in data.indices)
                {
                    if (j!=i)
                        term = term*(target - data[j].first)/  (data[i].first - data[j].first).toDouble()
                }
                result += term
            }
            return result
        }

        fun takeSteps(grid: Grid, times: Int): Set<Step> {
            var steps = setOf(Step(grid.startX, grid.startY))
            repeat(times) {
                steps = steps.flatMap { takeStep(grid, it).toSet() }.toSet()
                if (steps.any { it.x==0 || it.y==0 || it.y==grid.garden.size-2 || it.x==grid.garden[0].length-2  }){
                    grid.expandWest(steps)
                    grid.expandNorth(steps)
                    grid.expandSouth()
                    grid.expandEast()
                }
            }
            return steps
        }
    }

}