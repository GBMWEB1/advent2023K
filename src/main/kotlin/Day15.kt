
class Day15 {

    private var boxes = mutableListOf<Box>()

    init {
        var id = 0
        repeat(256){
            boxes.add(Box(id))
            id++
        }
    }

    class Box(private val id: Int){
        val slots = mutableListOf<String>()

        fun processLense(lense: String){
            if (lense.contains('=')){
                // if the lense is already in the box
                val label = lense.substringBefore("=")
                val pos = slots.indexOfFirst { it.startsWith(label) }
                if (pos==-1){
                    slots.add(lense)
                } else{
                    slots.removeAt(pos)
                    slots.add(pos, lense)
                }
            } else{ // remove lines
                val label = lense.substringBefore("-")
                val pos = slots.indexOfFirst { it.startsWith(label) }
                if (pos>-1){
                    slots.removeAt(pos)
                }
            }
        }

        fun display() {
            println( "Box $id: $slots")
        }

        fun sumFocusPower() : Int {
            var boxPower = 0
            slots.forEachIndexed { index, s ->
                val length = s.substringAfter("=").toInt()
                val slot = index+1
                val boxNumber = this.id + 1
                boxPower += (boxNumber * slot * length)
            }
            return boxPower
        }
    }

    fun processLense(step: String){
        val boxNumber = if (step.contains('=')){
            runStep(step.substringBefore("="))
        } else{
            runStep(step.substringBefore("-"))
        }
        boxes[boxNumber].processLense(step)
    }

    fun display(){
        boxes.filter { it.slots.isNotEmpty() }.forEach { it.display() }
    }

    fun sumFocusPower(): Int {
        return boxes.filter { it.slots.isNotEmpty() }.sumOf { it.sumFocusPower() }
    }

    companion object{
        fun initSequence(ch : Char, existingValue: Int): Int{
            return ((existingValue+ch.code)*17).mod(256)
        }

        fun runStep(s: String): Int {
            var value =0
            s.forEach {
                value = initSequence(it,value)
            }
            return value
        }

        fun sumSequences(data: List<String>): Int {
            return data.sumOf { runStep(it) }
        }
    }
}