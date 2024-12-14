package bernar.aoc2024.day14

import bernar.aoc2024.files.FileUtils

data class Point(val x: Int, val y: Int) {
    override fun toString(): String {
        return "(${x},${y})"
    }
}

class Robot(val p: Point, val v: Point) {
    var currentPosition = p

    fun move() {
        currentPosition =
            Point(
                (currentPosition.x + v.x).mod(RobotMap.WIDTH),
                (currentPosition.y + v.y).mod(RobotMap.HEIGHT)
            )
    }

    fun reset() {
        currentPosition = p
    }

    fun cycleLength(): Int {
        reset()
        move()
        var i = 1
        while (p != currentPosition) {
            move()
            i += 1
        }
        return i
    }

    override fun toString(): String {
        return "Robot p=${p}, v=${v}. Current position=${currentPosition}"
    }
}

class RobotMap(robots: List<Robot>) {
    companion object {
        const val WIDTH = 101
        const val HEIGHT = 103
    }

    private val content: List<List<Int>>

    init {
        content = ArrayList()
        for (i in 0..<HEIGHT) {
            val row = ArrayList<Int>()
            for (j in 0..<WIDTH) {
                row.add(robots.count { it.currentPosition.y == i && it.currentPosition.x == j })
            }
            content.add(row)
        }
    }

    override fun toString(): String {
        val stringBuilder: StringBuilder = StringBuilder()
        for (row in content) {
            for (amount in row) {
                stringBuilder.append(if (amount == 0) '.' else '#')
            }
            stringBuilder.append('\n')
        }
        return stringBuilder.toString()
    }
}

fun main() {

    val input = FileUtils.readFile("src/bernar/aoc2024/day14/input.txt")

    // part 1

    val robots = input.map {
        val lineSplit = it.split(" ")
        val positionSplit = lineSplit[0].split("=")
        val velocitySplit = lineSplit[1].split("=")

        val coordinatePositionSplit = positionSplit[1].split(",")
        val coordinateVelocitySplit = velocitySplit[1].split(",")

        Robot(
            Point(coordinatePositionSplit[0].toInt(), coordinatePositionSplit[1].toInt()),
            Point(coordinateVelocitySplit[0].toInt(), coordinateVelocitySplit[1].toInt())
        )
    }

    robots.forEach {
        for (i in 0..99) {
            it.move()
        }
    }

    val firstQuadrant =
        robots.count { it.currentPosition.x in 0..<RobotMap.WIDTH / 2 && it.currentPosition.y in 0..<RobotMap.HEIGHT / 2 }
    val secondQuadrant =
        robots.count { it.currentPosition.x in RobotMap.WIDTH / 2 + 1..<RobotMap.WIDTH && it.currentPosition.y in 0..<RobotMap.HEIGHT / 2 }
    val thirdQuadrant =
        robots.count { it.currentPosition.x in RobotMap.WIDTH / 2 + 1..<RobotMap.WIDTH && it.currentPosition.y in RobotMap.HEIGHT / 2 + 1..<RobotMap.HEIGHT }
    val fourthQuadrant =
        robots.count { it.currentPosition.x in 0..<RobotMap.WIDTH / 2 && it.currentPosition.y in RobotMap.HEIGHT / 2 + 1..<RobotMap.HEIGHT }

    println(firstQuadrant * secondQuadrant * thirdQuadrant * fourthQuadrant)

    // part 2

    val cycleLength = robots[0].cycleLength() // It's the same for every robot

    robots.forEach { it.reset() }
    RobotMap(robots)

    for (i in 1..cycleLength) {
        robots.forEach { it.move() }
        if (i == 6285) {
            println(RobotMap(robots))
        }
    }
}
