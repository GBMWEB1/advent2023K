class Day6 {
    companion object
    {
        fun calcTotalDistance(hold: Long, distance: Long): Long{
            return hold * (distance-hold)
        }

        fun calculateWinningNumbers(time: Long, distance: Long): Long{
            var wins=0L
            for (hold in 0..time){
                if (calcTotalDistance(hold, time)> distance){
                    wins++
                }
            }
            return wins
        }
    }
}