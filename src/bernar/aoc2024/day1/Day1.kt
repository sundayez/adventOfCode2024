package bernar.aoc2024.day1

import bernar.aoc2024.files.FileUtils
import kotlin.math.abs

fun main() {
    val input = FileUtils.readFile("src/bernar/aoc2024/day1/input.txt")
    val firstList = input.map { it.split("\\s+".toRegex())[0].toInt() }
    val secondList = input.map { it.split("\\s+".toRegex())[1].toInt() }

    // part 1

    println(firstList.sorted()
        .zip(secondList.sorted()).sumOf { abs(it.first - it.second) })

    // part 2
    val score = firstList.sumOf { it * secondList.count { element -> element == it } }
    println(score)
}