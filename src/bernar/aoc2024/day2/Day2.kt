package bernar.aoc2024.day2

import bernar.aoc2024.files.FileUtils
import kotlin.math.abs

fun main() {
    val input = FileUtils.readFile("src/bernar/aoc2024/day2/input.txt")

    val reports = input.map { it.split("\\s+".toRegex()) }.map { it.map { char -> char.toInt() } }

    // part 1

    val differences = reports.map { report -> calculateDifferences(report) }

    val safe = differences.count { difference -> differenceIsSafe(difference) }

    println(safe)

    // part 2

    val differencesWithRemoval = reports.map {
        report -> report.indices.map {
            calculateDifferences(report.subList(0, it) + report.subList(it + 1, report.size))
        }
    }

    val safeWithRemoval = differencesWithRemoval.count { it.any { difference -> differenceIsSafe(difference) } }

    println(safeWithRemoval)
}

private fun differenceIsSafe(difference: List<Int>) =
    (difference.all { eval -> eval > 0 } || difference.all { eval -> eval < 0 }) && difference.all { eval -> abs(eval) in 1..3 }

private fun calculateDifferences(report: List<Int>) =
    report.zipWithNext().map { eval -> eval.second - eval.first }