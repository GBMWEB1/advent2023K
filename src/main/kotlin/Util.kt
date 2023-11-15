import java.io.File

class Util {

    fun readData(file: String): List<String> {
        return File("src/test/resources/${file}").readText().lines()
    }
}