package com.example.rockscissorspaper

fun main() {
    val rock = "바위"
    val scissors = "가위"
    val paper = "보"

    val random =  (1 .. 3).random()
    var computer = ""

    when (random) {
        1 -> {
            computer = rock
        }
        2 -> {
            computer = scissors
        }
        3 -> {
            computer = paper
        }
    }

    var player = ""
    val inputs = listOf(rock, scissors, paper)

    println("가위, 바위, 보 중 하나를 입력해 주세요")

    while (true) {
        val input = readln()

        if (input in inputs) {
            player = input
            println("플레이어: $player 컴퓨터: $computer")

            when (player) {
                computer -> {
                    println("비겼습니다.")
                }
                rock -> {
                    if (computer == scissors) println("이겼습니다.") else println("졌습니다.")
                }
                scissors -> {
                    if (computer == paper) println("이겼습니다.") else println("졌습니다.")
                }
                paper -> {
                    if (computer == rock) println("이겼습니다.") else println("졌습니다.")
                }
            }
            break
        } else {
            println("올바르지 않은 입력값입니다! 다시 입력해주세요.")
        }
    }
}