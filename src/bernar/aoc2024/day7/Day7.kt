package bernar.aoc2024.day7

import bernar.aoc2024.files.FileUtils

enum class Operator {
    SUM, PROD
}

enum class OperatorExtended {
    SUM, PROD, CONCAT
}

data class Operation(val result: Long, val numbers: List<Long>)

fun main() {

    val input = FileUtils.readFile("src/bernar/aoc2024/day7/input.txt")

    val operations = input.map { line ->
        val (left, right) = line.split(":")
        val result = left.toLong()
        val numbers = right.trim().split(" ").map { it.toLong() }
        Operation(result, numbers)
    }

    // part 1

    fun combineOperators(size: Int): List<List<Operator>> {
        if (size == 1) {
            return listOf(listOf(Operator.SUM), listOf(Operator.PROD))
        }
        val previousCombinations = combineOperators(size - 1)
        return previousCombinations.flatMap {
            combination -> listOf(combination + Operator.SUM, combination + Operator.PROD)
        }
    }

    fun calculateOperation(numbers: List<Long>, operators: List<Operator>): Long {
        return numbers.withIndex().reduce { partialIndexedResult, currentIndexedOperator ->
            IndexedValue(currentIndexedOperator.index,
                when(operators[currentIndexedOperator.index]) {
                    Operator.SUM -> partialIndexedResult.value + currentIndexedOperator.value
                    Operator.PROD -> partialIndexedResult.value * currentIndexedOperator.value
                })
        }.value
    }

    val result = operations.sumOf {
        val combinations = combineOperators(it.numbers.size)
        if (combinations.any { combination ->
                calculateOperation(
                    it.numbers,
                    combination
                ) == it.result
            }) it.result else 0
    }

    println(result)

    // part 2

    fun combineOperatorsExtended(size: Int): List<List<OperatorExtended>> {
        if (size == 1) {
            return listOf(listOf(OperatorExtended.SUM), listOf(OperatorExtended.PROD), listOf(OperatorExtended.CONCAT))
        }
        val previousCombinations = combineOperatorsExtended(size - 1)
        return previousCombinations.flatMap {
                combination -> listOf(combination + OperatorExtended.SUM, combination + OperatorExtended.PROD, combination + OperatorExtended.CONCAT)
        }
    }

    fun calculateOperationExtended(numbers: List<Long>, operators: List<OperatorExtended>): Long {
        return numbers.withIndex().reduce { partialIndexedResult, currentIndexedOperator ->
            IndexedValue(currentIndexedOperator.index,
                when(operators[currentIndexedOperator.index]) {
                    OperatorExtended.SUM -> partialIndexedResult.value + currentIndexedOperator.value
                    OperatorExtended.PROD -> partialIndexedResult.value * currentIndexedOperator.value
                    OperatorExtended.CONCAT -> (partialIndexedResult.value.toString() + currentIndexedOperator.value.toString()).toLong()
                })
        }.value
    }

    val resultExtended = operations.sumOf {
        val combinations = combineOperatorsExtended(it.numbers.size)
        if (combinations.any { combination ->
                calculateOperationExtended(
                    it.numbers,
                    combination
                ) == it.result
            }) it.result else 0
    }

    println(resultExtended)
}
