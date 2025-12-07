package com.androidace.canvascomposemaster.tictactoe

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


/**
 * Draw tic tac toe lines
 *
 * **/

@Composable
fun TicTacToeGame(modifier: Modifier = Modifier) {

    var animationState: XAnimateState by remember { mutableStateOf(XAnimateState.Start) }
    var circleAnimateState: OAnimateState by remember { mutableStateOf(OAnimateState.Start) }

    var playerMap = remember {
        initPlayerMap()
    }

    var currentPlayer: PlayerType = PlayerType.PlayerX

    var currentRectTapped by remember {
        mutableStateOf(Rect.Zero)
    }

    var topLeftRect by remember {
        mutableStateOf(Rect.Zero)
    }
    var centerTopRect by remember {
        mutableStateOf(Rect.Zero)
    }
    var topRightRect by remember {
        mutableStateOf(Rect.Zero)
    }
    var centerLeftRect by remember {
        mutableStateOf(Rect.Zero)
    }
    var centerRect by remember {
        mutableStateOf(Rect.Zero)
    }
    var centerRightRect by remember {
        mutableStateOf(Rect.Zero)
    }
    var bottomLeftRect by remember {
        mutableStateOf(Rect.Zero)
    }
    var bottomCenterRect by remember {
        mutableStateOf(Rect.Zero)
    }
    var bottomRightRect by remember {
        mutableStateOf(Rect.Zero)
    }

    /***
     * Board moves played
     * **/

    var move_1X1 by remember {
        mutableStateOf(BoardMove())
    }
    var move_1X2 by remember {
        mutableStateOf(BoardMove())
    }
    var move_1X3 by remember {
        mutableStateOf(BoardMove())
    }

    var move_2X1 by remember {
        mutableStateOf(BoardMove())
    }
    var move_2X2 by remember {
        mutableStateOf(BoardMove())
    }
    var move_2X3 by remember {
        mutableStateOf(BoardMove())
    }

    var move_3X1 by remember {
        mutableStateOf(BoardMove())
    }
    var move_3X2 by remember {
        mutableStateOf(BoardMove())
    }
    var move_3X3 by remember {
        mutableStateOf(BoardMove())
    }


    /**
     * -------
     * **/

    val context = LocalContext.current

    val progressLineOneX by animateFloatAsState(
        targetValue = if (animationState >= XAnimateState.LineOneX) 1f else 0f,
        animationSpec = tween(durationMillis = 800), label = "progressLineOneX"
    )

    val progressLineTwoX by animateFloatAsState(
        targetValue = if (animationState >= XAnimateState.LineTwoX) 1f else 0f,
        animationSpec = tween(durationMillis = 800), label = "progressLineTwoX"
    )

    val progressO by animateFloatAsState(
        targetValue = if (circleAnimateState >= OAnimateState.Start) 1f else 0f,
        animationSpec = tween(durationMillis = 800), label = "progressO"
    )



    LaunchedEffect(key1 = move_1X1, move_1X2) {
        if (currentPlayer == PlayerType.PlayerX) {
            delay(600)
            animationState = XAnimateState.LineOneX
            delay(600)
            animationState = XAnimateState.LineTwoX
            animationState = XAnimateState.Done
        }

        if (currentPlayer == PlayerType.PlayerO) {
            delay(600)
            circleAnimateState = OAnimateState.Draw
            delay(600)
            circleAnimateState = OAnimateState.Done
        }
    }


    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(true) {
                detectTapGestures {
                    when {
                        topLeftRect.contains(it) -> {
                            playerMap[currentPlayer]?.playerMoves?.add(topLeftRect)
                            playerMap[currentPlayer]?.playerMovePositions?.add(0)
                            currentRectTapped = topLeftRect
                            move_1X1 = move_1X1.copy(
                                playerType = currentPlayer,
                                moveType = MoveType.Played
                            )
                        }

                        centerTopRect.contains(it) -> {
                            playerMap[currentPlayer]?.playerMoves?.add(centerTopRect)
                            playerMap[currentPlayer]?.playerMovePositions?.add(1)
                            currentRectTapped = centerTopRect
                            move_1X2 = move_1X2.copy(
                                playerType = currentPlayer,
                                moveType = MoveType.Played
                            )
                        }

                        topRightRect.contains(it) -> {
                            playerMap[currentPlayer]?.playerMoves?.add(topRightRect)
                            playerMap[currentPlayer]?.playerMovePositions?.add(2)
                            currentRectTapped = topRightRect
                            move_1X3 = move_1X3.copy(
                                playerType = currentPlayer,
                                moveType = MoveType.Played
                            )
                        }

                        centerLeftRect.contains(it) -> {
                            playerMap[currentPlayer]?.playerMoves?.add(centerLeftRect)
                            playerMap[currentPlayer]?.playerMovePositions?.add(3)
                            currentRectTapped = centerLeftRect
                            move_2X1 = move_2X1.copy(
                                playerType = currentPlayer,
                                moveType = MoveType.Played
                            )
                        }

                        centerRect.contains(it) -> {
                            playerMap[currentPlayer]?.playerMoves?.add(centerRect)
                            playerMap[currentPlayer]?.playerMovePositions?.add(4)
                            currentRectTapped = centerRect
                            move_2X2 = move_2X2.copy(
                                playerType = currentPlayer,
                                moveType = MoveType.Played
                            )
                        }

                        centerRightRect.contains(it) -> {
                            playerMap[currentPlayer]?.playerMoves?.add(centerRightRect)
                            playerMap[currentPlayer]?.playerMovePositions?.add(5)
                            currentRectTapped = centerRightRect
                            move_2X3 = move_2X3.copy(
                                playerType = currentPlayer,
                                moveType = MoveType.Played
                            )
                        }

                        bottomLeftRect.contains(it) -> {
                            playerMap[currentPlayer]?.playerMoves?.add(bottomLeftRect)
                            playerMap[currentPlayer]?.playerMovePositions?.add(6)
                            currentRectTapped = bottomLeftRect
                            move_3X1 = move_3X1.copy(
                                playerType = currentPlayer,
                                moveType = MoveType.Played
                            )
                        }

                        bottomCenterRect.contains(it) -> {
                            playerMap[currentPlayer]?.playerMoves?.add(bottomCenterRect)
                            playerMap[currentPlayer]?.playerMovePositions?.add(7)
                            currentRectTapped = bottomCenterRect
                            move_3X2 = move_3X2.copy(
                                playerType = currentPlayer,
                                moveType = MoveType.Played
                            )
                        }

                        bottomRightRect.contains(it) -> {
                            playerMap[currentPlayer]?.playerMoves?.add(bottomRightRect)
                            playerMap[currentPlayer]?.playerMovePositions?.add(8)
                            currentRectTapped = bottomRightRect
                            move_3X3 = move_3X3.copy(
                                playerType = currentPlayer,
                                moveType = MoveType.Played
                            )
                        }
                    }

                    currentPlayer = if (currentPlayer == PlayerType.PlayerX) {
                        PlayerType.PlayerO
                    } else {
                        PlayerType.PlayerX
                    }
                }
            }) {
        val boardWidth = size.width - 10.dp.toPx()
        val boardHeight = size.height / 2
        val mainBoardOffset = Offset(x = 5.dp.toPx(), y = center.y / 2)

        val mainBoardRect = Rect(
            offset = mainBoardOffset,
            size = Size(boardWidth, boardHeight)
        )

        // (1,1) coordinate Rect
        topLeftRect = Rect(
            offset = mainBoardOffset,
            size = Size(width = boardWidth / 3f, height = boardHeight / 3f)
        )

        // (1,2) coordinate Rect
        centerTopRect = Rect(
            offset = mainBoardOffset.copy(x = topLeftRect.topRight.x, y = topLeftRect.topRight.y),
            size = Size(width = boardWidth / 3f, height = boardHeight / 3f)
        )

        // (1,3) coordinate Rect
        topRightRect = Rect(
            offset = mainBoardOffset.copy(
                x = centerTopRect.topRight.x,
                y = centerTopRect.topRight.y
            ),
            size = Size(width = boardWidth / 3f, height = boardHeight / 3f)
        )

        // (2,1) coordinate Rect
        centerLeftRect = Rect(
            offset = mainBoardOffset.copy(
                x = topLeftRect.bottomLeft.x,
                y = topLeftRect.bottomLeft.y
            ),
            size = Size(width = boardWidth / 3f, height = boardHeight / 3f)
        )

        // (2,2) coordinate Rect
        centerRect = Rect(
            offset = mainBoardOffset.copy(
                x = centerLeftRect.topRight.x,
                y = mainBoardRect.top + boardHeight / 3f
            ),
            size = Size(width = boardWidth / 3f, height = boardHeight / 3f)
        )

        // (2,3) coordinate Rect
        centerRightRect = Rect(
            offset = mainBoardOffset.copy(
                x = centerRect.topRight.x,
                y = centerRect.topRight.y
            ),
            size = Size(width = boardWidth / 3f, height = boardHeight / 3f)
        )

        // (3,1) coordinate Rect
        bottomLeftRect = Rect(
            offset = mainBoardOffset.copy(
                x = centerLeftRect.bottomLeft.x,
                y = centerLeftRect.bottomLeft.y
            ),
            size = Size(width = boardWidth / 3f, height = boardHeight / 3f)
        )

        // (3,2) coordinate Rect
        bottomCenterRect = Rect(
            offset = mainBoardOffset.copy(
                x = bottomLeftRect.topRight.x,
                y = bottomLeftRect.topRight.y
            ),
            size = Size(width = boardWidth / 3f, height = boardHeight / 3f)
        )

        // (3,3) coordinate Rect
        bottomRightRect = Rect(
            offset = mainBoardOffset.copy(
                x = bottomCenterRect.topRight.x,
                y = bottomCenterRect.topRight.y
            ),
            size = Size(width = boardWidth / 3f, height = boardHeight / 3f)
        )

        // -- Main rectangle -- //
        drawRect(
            color = Color.Transparent,
            topLeft = mainBoardRect.topLeft,
            size = mainBoardRect.size,
            style = Stroke(
                width = 2.dp.toPx(),
            )
        )

        // -- Row 1 --//
        drawRect(
            color = Color.Transparent,
            topLeft = topLeftRect.topLeft,
            size = topLeftRect.size
        )

        if (move_1X1.moveType == MoveType.Played && move_1X1.playerType == PlayerType.PlayerX) {
            drawPlayerXAttempt(
                topLeftRect.inflate(-50.dp.toPx()),
                progressLineOneX,
                progressLineTwoX
            )
        } else if (move_1X1.moveType == MoveType.Played && move_1X1.playerType == PlayerType.PlayerO) {
            drawPlayerOAttempt(topLeftRect, progressO)
        }

        drawRect(
            color = Color.Transparent,
            topLeft = centerTopRect.topLeft,
            size = centerTopRect.size
        )

        if (move_1X2.moveType == MoveType.Played && move_1X2.playerType == PlayerType.PlayerX) {
            drawPlayerXAttempt(
                centerTopRect.inflate(-50.dp.toPx()),
                progressLineOneX,
                progressLineTwoX
            )
        } else if (move_1X2.moveType == MoveType.Played && move_1X2.playerType == PlayerType.PlayerO) {
            drawPlayerOAttempt(centerTopRect.inflate(-50.dp.toPx()), progressO)
        }

        drawRect(
            color = Color.Transparent,
            topLeft = topRightRect.topLeft,
            size = topRightRect.size
        )

        // -- Row 2 --//

        drawRect(
            color = Color.Transparent,
            topLeft = centerLeftRect.topLeft,
            size = centerLeftRect.size
        )

        drawRect(
            color = Color.Transparent,
            topLeft = centerRect.topLeft,
            size = centerRect.size
        )

        drawRect(
            color = Color.Transparent,
            topLeft = centerRightRect.topLeft,
            size = centerRightRect.size
        )

        // -- Row 3 --//

        drawRect(
            color = Color.Transparent,
            topLeft = bottomRightRect.topLeft,
            size = bottomRightRect.size
        )

        drawRect(
            color = Color.Transparent,
            topLeft = bottomCenterRect.topLeft,
            size = bottomCenterRect.size
        )

        drawRect(
            color = Color.Transparent,
            topLeft = bottomRightRect.topLeft,
            size = bottomRightRect.size
        )

        /**
         * Drow lines for Tic Toc Toe
         * **/
        translate(left = boardWidth / 3f) {
            drawLine(
                color = Color.Black,
                start = mainBoardRect.topLeft,
                end = mainBoardRect.bottomLeft,
                strokeWidth = 5.dp.toPx()
            )
        }

        translate(left = (boardWidth / 3f) + (boardWidth / 3f)) {
            drawLine(
                color = Color.Black,
                start = mainBoardRect.topLeft,
                end = mainBoardRect.bottomLeft,
                strokeWidth = 5.dp.toPx()
            )
        }

        translate(top = boardHeight / 3f) {
            drawLine(
                color = Color.Black,
                start = mainBoardRect.topLeft,
                end = mainBoardRect.topRight,
                strokeWidth = 5.dp.toPx()
            )
        }

        translate(top = (boardHeight / 3f) + (boardHeight / 3f)) {
            drawLine(
                color = Color.Black,
                start = mainBoardRect.topLeft,
                end = mainBoardRect.topRight,
                strokeWidth = 5.dp.toPx()
            )
        }
    }
}


fun DrawScope.drawPlayerXAttempt(
    rect: Rect,
    animationProgress: Float,
    animationLineTwoProgress: Float,
    color: Color = Color.Black
) {
    drawLine(
        color = Color.Red,
        start = Offset(rect.left, rect.top),
        end = Offset(
            x = rect.left + (rect.width * animationProgress),
            y = rect.top + (rect.height * animationProgress)
        ),
        strokeWidth = 8.dp.toPx()
    )

    drawLine(
        color = Color.Red,
        start = Offset(rect.right, rect.top),
        end = Offset(
            x = rect.right - (rect.width * animationLineTwoProgress),
            y = rect.top + (rect.height * animationLineTwoProgress)
        ),
        strokeWidth = 8.dp.toPx()
    )

}

fun DrawScope.drawPlayerOAttempt(
    rect: Rect,
    animationProgress: Float
) {
    val padding = 16.dp.toPx()
    val startX = padding
    val startY = padding
    val endX = size.width - padding
    val endY = size.height - padding

    val strokeWidth = 12f
    drawArc(
        color = Color.Blue,
        startAngle = -90f,
        sweepAngle = 360f * animationProgress, // Animate from 0 to 360
        useCenter = false,
        topLeft = rect.topLeft,
        size = rect.size,
        style = Stroke(width = strokeWidth)
    )
}

fun initPlayerMap(): Map<PlayerType, PlayerMoves> {
    return mutableStateMapOf(
        Pair(PlayerType.PlayerX, PlayerMoves(mutableListOf(), mutableListOf())),
        Pair(PlayerType.PlayerO, PlayerMoves(mutableListOf(), mutableListOf()))
    )
}