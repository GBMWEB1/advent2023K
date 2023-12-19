class Day19(val list: List<String>, private val maxValue: Int = 4000) {

    val parts = buildParts(list)
    val workflows = buildWorkflows(list)

    private fun buildParts(list: List<String>): List<Part> {
        val partsIndex = list.indexOfFirst { it.isEmpty() }
        if (partsIndex==-1){
            return emptyList()
        }
        val partList = list.drop(partsIndex+1)
        return partList.map { Part.of(it) }
    }
    private fun buildWorkflows(list: List<String>): List<Workflow> {
        val index = list.indexOfFirst { it.isEmpty() }
        if (index==-1){
            return list.map { Workflow.of(it) }
        }
        val workList = list.take(index)
        return workList.map { Workflow.of(it) }
    }

    // Part 1
    fun sumAccepted(): Int {
        return parts.sumOf { it.getRating(workflows) }
    }

    // Part 2
    fun evaluateWorkflowOptions(): Long {
        var options = listOf(Option.new("in", maxValue))

        while (options.any { !Workflow.isAccepted(it.nextTarget) && !Workflow.isRejected(it.nextTarget) }){
            options = options.flatMap { it.evaluate(workflows) }
        }
        val accepted = options.filter { it.nextTarget=="A" }

        return accepted.sumOf { it.getCombinations() }
    }

    data class Range(var lessThan: Int=4001, var greaterThan: Int=0) {
        private val maxStart = 4000
        fun count(): Int {
            if (lessThan>greaterThan){
                return lessThan - greaterThan - 1
            }
            return (lessThan - 1) + maxStart - greaterThan
        }
    }

    data class Option(var nextTarget:String,
                      var xRange:Range,
                      val mRange: Range,
                      val aRange:Range,
                      val sRange:Range,
                      val previous: MutableList<String> = mutableListOf()
    ) {
        fun evaluate(workflows: List<Workflow>): List<Option> {
            if (nextTarget == "A" || nextTarget == "R") {
                return listOf(this)
            }
            // follow the target
            val workflow = workflows.find { it.id == this.nextTarget }!!

            val options = workflow.rules.map { this.clone(it.getTarget()) }

            options.forEachIndexed { index, option ->
                workflow.rules[index].updateOption(option, workflow.rules.take(index))
            }
            return options
        }



        private fun clone(target: String): Option {
            return Option(
                target,
                Range(this.xRange.lessThan, this.xRange.greaterThan),
                Range(this.mRange.lessThan, this.mRange.greaterThan),
                Range(this.aRange.lessThan, this.aRange.greaterThan),
                Range(this.sRange.lessThan, this.sRange.greaterThan),
                this.previous.toList().toMutableList()
            )
        }

        fun getCombinations(): Long {
            val x = getXCount().toLong()
            val m = getMCount()
            val a = getACount()
            val s = getSCount()

            return x*m*a*s
        }

        private fun getXCount(): Int {
            return this.xRange.count()
        }
        private fun getMCount(): Int {
            return this.mRange.count()
        }
        private fun getACount(): Int {
            return this.aRange.count()
        }
        private fun getSCount(): Int {
            return this.sRange.count()
        }

        companion object{
            fun new(target: String, maxValue: Int): Option {
                return Option(
                    target,
                    Range(maxValue+1),
                    Range(maxValue+1),
                    Range(maxValue+1),
                    Range(maxValue+1),
                )
            }
        }
    }

    data class Part(val x: Int, val m: Int, val a: Int, val s: Int){

        private fun evaluate(workflows: List<Workflow>): Boolean{
            var workflow = workflows.first { it.id=="in" }
            var target = workflow.evaluate(this)
            print("$this in ->$target")

            while (!Workflow.isAccepted(target) && !Workflow.isRejected(target) ){
                // get the workflow from the target
                workflow = workflows.first { it.id==target }
                target = workflow.evaluate(this)
                print("->"+target)
            }
            println()
            return Workflow.isAccepted(target)
        }

        fun getRating(workflows: List<Workflow>):Int{
            if (evaluate(workflows)){
                return a+x+m+s
            }
            return 0
        }


        companion object {
            fun of(line:String): Part{
                return Part(
                    line.substringAfter("x=").substringBefore(",m").toInt(),
                    line.substringAfter("m=").substringBefore(",a").toInt(),
                    line.substringAfter("a=").substringBefore(",s").toInt(),
                    line.substringAfter("s=").substringBefore("}").toInt(),
                    )
            }
        }
    }

    data class Rule(val instruction:String){
        fun isTrue(part: Part): Boolean {
            val x = when (instruction[0]){
                's' -> part.s
                'm' -> part.m
                'x' -> part.x
                else -> part.a
            }
            if (instruction.contains(">")){
                val compareBit = instruction.substringAfter(">")
                    .substringBefore(":")
                    .toInt()
                return (x> compareBit)
            } else if (instruction.contains("<")){
                val compareBit = instruction.substringAfter("<")
                    .substringBefore(":")
                    .toInt()
                return (x< compareBit)
            } else {
                return true
            }
        }

        private fun applyReverse(option: Option){
            option.previous.add("!$instruction")
            val range = when (instruction[0]){
                's' -> option.sRange
                'm' -> option.mRange
                'x' -> option.xRange
                else -> option.aRange
            }
            if (instruction.contains(">")){
                val compareBit = instruction.substringAfter(">")
                    .substringBefore(":")
                    .toInt()
                range.lessThan= compareBit+1
            } else if (instruction.contains("<")){
                val compareBit = instruction.substringAfter("<")
                    .substringBefore(":")
                    .toInt()
                range.greaterThan= compareBit-1
            }
        }


        fun updateOption(option: Option, previousFailedRules: List<Rule>) {
            previousFailedRules.forEach { it.applyReverse(option) }
            option.previous.add(instruction)
            val range = when (instruction[0]){
                's' -> option.sRange
                'm' -> option.mRange
                'x' -> option.xRange
                else -> option.aRange
            }
            if (instruction.contains(">")){
                val compareBit = instruction.substringAfter(">")
                    .substringBefore(":")
                    .toInt()
                range.greaterThan = compareBit
            } else if (instruction.contains("<")){
                val compareBit = instruction.substringAfter("<")
                    .substringBefore(":")
                    .toInt()
                range.lessThan = compareBit
            }
        }

        fun getTarget():String{
            if (instruction.contains(":")){
                return instruction.substringAfter(":")
            }
            return instruction
        }


    }


    data class Workflow(val id: String, val rules:List<Rule>) {
        fun evaluate(part: Part): String {
            val rule = rules.first { it.isTrue(part) }
            return rule.getTarget()
        }

        companion object{

            fun isAccepted(target:String): Boolean {
                return (target=="A")
            }
            fun isRejected(target:String): Boolean {
                return (target=="R")
            }

            fun of(line: String): Workflow{
                val rules = line
                    .substringAfter("{")
                    .substringBefore("}")
                    .split(",")
                    .map { Rule(it) }

                return Workflow(
                    line.substringBefore("{"),
                    rules
                )
            }
        }
    }

}