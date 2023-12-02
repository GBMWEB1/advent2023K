
class Day2 {
    enum class Cube(val maxValue: Int){
        RED(12),
        BLUE( 14),
        GREEN(13);
    }

    companion object {

        // Part 1
        fun sumValidGames(games: List<String>): Int {
            return games
                .filter { isGameValid(it) }
                .sumOf { getGameId(it) }
        }

        fun isGameValid(game: String): Boolean {
            return game.getRounds().all { isRoundValid(it) }
        }

        private fun String.getRounds(): List<String> {
            return this.substringAfter(":").split(";")
        }

        private fun isRoundValid(round: String): Boolean{
            return round
                .split(",") // colours
                .map { it.trim() }
                .all {
                    val colour = it.substringAfter(" ")
                    val quantity = it.substringBefore(" ").toInt()
                    val cube = Cube.valueOf(colour.uppercase())
                    quantity <= cube.maxValue
                }
        }

        private fun getGameId(game: String): Int{
            return  game.substringAfter("Game ").substringBefore(":").toInt()
        }

        // Part 2
        fun sumGamePowers(games: List<String>): Int {
            return games.sumOf { getGamePower(it) }
        }

        fun getGamePower(game: String): Int {
            return game.getCubes(Cube.RED).max() *
                    game.getCubes(Cube.BLUE).max() *
                    game.getCubes(Cube.GREEN).max()
        }

        fun String.getCubes(cube: Cube): List<Int> {
            return this
                .split(cube.name.lowercase())
                .dropLast(1)
                .map { it.trimEnd().substringAfterLast(" ").toInt()}
        }
    }
}