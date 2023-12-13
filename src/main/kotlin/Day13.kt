
class Day13 {

    companion object{

        private fun getPatterns(data: List<String>): List<Pattern>{
            var currentPattern = mutableListOf<String>()
            val patterns = mutableListOf<Pattern>()
            data.forEach {
                if (it.isEmpty()){
                    patterns.add(Pattern(currentPattern))
                    currentPattern = mutableListOf()
                } else{
                    currentPattern.add(it)
                }
            }
            if (currentPattern.size>0){
                patterns.add(Pattern(currentPattern))
            }
            return patterns
        }

        fun getTotal(data: List<String>, smudgeCount : Int = 0): Int{
            return getPatterns(data).sumOf { it.getTotal(smudgeCount) }
        }
    }

    class Pattern(var rows : List<String>){
        var cols = calcColumns()

        private fun calcColumns(): List<String> {

            val colomns = mutableListOf<String>()

            for (colIndex in 0..<rows[0].length){
                colomns.add(String(rows.map { it[colIndex] }.toCharArray()))
            }
            return colomns.toList()
        }

        private fun findMirror(data: List<String>, smudgeCount: Int): Int{
            for (x in 0..data.size-2){
                if (isMirror(data, x, smudgeCount)){
                    return x+1
                }
            }
            return 0
        }

        private fun isMirror(data: List<String>, x: Int, smudgeCount: Int): Boolean {
            var differences =0
            for (pos in x downTo 0){
                val reflectionPos = x + 1 + (x-pos)
                if (reflectionPos> data.size-1){
                    return differences == smudgeCount
                }
                val s1 = data[pos]
                val s2 = data[reflectionPos]

                s1.forEachIndexed { index, c ->
                    if (c != s2[index]){
                        differences++
                    }
                }
            }
            return differences == smudgeCount
        }

        fun getTotal(smudgeCount : Int = 0): Int {
            return findMirror(cols, smudgeCount)+ (findMirror(rows, smudgeCount)*100)
        }
    }
}