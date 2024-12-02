package bernar.aoc2024.day2

import bernar.aoc2024.files.FileUtils
import kotlin.math.abs

fun main() {
    val input = FileUtils.readFile("src/bernar/aoc2024/day2/input.txt")

    val reports = input.map { it.split("\\s+".toRegex()) }.map { it.map { char -> char.toInt() } }

    // part 1

    val differences = reports.map { report -> calculateDifferences(report) }

    val safe = differences.count { difference -> differencesAreSafe(difference) }

    println(safe)

    // part 2

    val differencesWithRemoval = reports.map {
        report -> report.withIndex().map {
            calculateDifferences(report.subList(0, it.index) + report.subList(it.index + 1, report.size))
        }
    }

    val safeWithRemoval = differencesWithRemoval.count { it.any { difference -> differencesAreSafe(difference) } }

    println(safeWithRemoval)
}

private fun differencesAreSafe(it: List<Int>) =
    (it.all { eval -> eval > 0 } || it.all { eval -> eval < 0 }) && it.all { eval -> abs(eval) in 1..3 }

private fun calculateDifferences(it: List<Int>) =
    it.zipWithNext().map { eval -> eval.second - eval.first }