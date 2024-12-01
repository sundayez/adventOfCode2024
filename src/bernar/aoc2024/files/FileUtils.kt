package bernar.aoc2024.files

import java.io.File

object FileUtils {
    fun readFile(fileName: String): List<String> {
        return File(fileName).useLines { it.toList() }
    }
}