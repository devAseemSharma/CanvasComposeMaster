package com.androidace.canvascomposemaster.tictactoe

import androidx.compose.ui.geometry.Rect

data class PlayerMoves(
    val playerMoves: MutableList<Rect>,
    val playerMovePositions: MutableList<Int>
)

data class BoardMove(
    val playerType: PlayerType = PlayerType.Undefined,
    val moveType: MoveType = MoveType.UnPlayed
)

enum class MoveType {
    Played,
    UnPlayed
}

enum class PlayerType {
    PlayerX,
    PlayerO,
    Undefined
}

// Define the animation stages
enum class XAnimateState {
    Start,
    LineOneX,
    LineTwoX,
    Done
}

enum class OAnimateState{
    Start,
    Draw,
    Done
}

private val winCombinations = listOf(
    // Horizontal lines
    listOf(0, 1, 2),
    listOf(3, 4, 5),
    listOf(6, 7, 8),
    // Vertical lines
    listOf(0, 3, 6),
    listOf(1, 4, 7),
    listOf(2, 5, 8),
    // Diagonal lines
    listOf(0, 4, 8),
    listOf(2, 4, 6)
)