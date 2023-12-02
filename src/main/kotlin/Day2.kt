class Day2 {

    companion object {
        private const val MAX_RED = 12
        private const val MAX_BLUE = 14
        private const val MAX_GREEN = 13

        //Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        fun isGameValid(game: String): Boolean {
            game.substringAfter(":").split(";").forEach {
                if (!isRoundValid(it)) {
                    return false
                }
            }
            return true
        }

        private fun isRoundValid(round: String): Boolean{
            val colours = round.split(",").map { it.trim() }
            colours.forEach {
                val colour = it.substringAfter(" ")
                val quantity = it.substringBefore(" ").toInt()
                when (colour) {
                    "red" -> if (quantity> MAX_RED) return false
                    "green" -> if (quantity> MAX_GREEN) return false
                    "blue" -> if (quantity> MAX_BLUE) return false
                }
            }
            return true
        }

        private fun getGameId(game: String): Int{
            return  game.substringAfter("Game ").substringBefore(":").toInt()
        }

        fun sumValidGames(games: List<String>): Int {
            return games
                .filter { isGameValid(it) }
                .sumOf { getGameId(it) }
        }

        fun getMaxColour(line: String, colour: String): Int{
            val instances = line.split(colour)
            return instances.dropLast(1).maxOf {
                it.trimEnd().substringAfterLast(" ").toInt()
            }
        }

        fun getGamePower(game: String): Int {
            return getMaxColour(game, "red") *
                    getMaxColour(game, "blue") *
                    getMaxColour(game, "green")
        }

        fun sumGamePowers(games: List<String>): Int {
            return games.sumOf { getGamePower(it) }
        }
    }
}