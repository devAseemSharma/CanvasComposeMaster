package com.androidace.canvascomposemaster.tictactoe

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


/**
 * Draw tic tac toe lines
 *
 * **/

@Composable
fun TicTacToeGame(modifier: Modifier = Modifier) {
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

    val context = LocalContext.current


    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(true) {
                detectTapGestures {
                    when {
                        topLeftRect.contains(it) -> {
                            Toast.makeText(context, "Rect 1X1 clicked", Toast.LENGTH_SHORT).show()
                        }
                        centerTopRect.contains(it) -> {
                            Toast.makeText(context, "Rect 1X2 clicked", Toast.LENGTH_SHORT).show()
                        }
                        topRightRect.contains(it) -> {
                            Toast.makeText(context, "Rect 1X3 clicked", Toast.LENGTH_SHORT).show()
                        }
                        centerLeftRect.contains(it) -> {
                            Toast.makeText(context, "Rect 2X1 clicked", Toast.LENGTH_SHORT).show()
                        }
                        centerRect.contains(it) -> {
                            Toast.makeText(context, "Rect 2X2 clicked", Toast.LENGTH_SHORT).show()
                        }
                        centerRightRect.contains(it) -> {
                            Toast.makeText(context, "Rect 2X3 clicked", Toast.LENGTH_SHORT).show()
                        }
                        bottomLeftRect.contains(it) -> {
                            Toast.makeText(context, "Rect 3X1 clicked", Toast.LENGTH_SHORT).show()
                        }
                        bottomCenterRect.contains(it) -> {
                            Toast.makeText(context, "Rect 3X2 clicked", Toast.LENGTH_SHORT).show()
                        }
                        bottomRightRect.contains(it) -> {
                            Toast.makeText(context, "Rect 3X3 clicked", Toast.LENGTH_SHORT).show()
                        }
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

        drawRect(
            color = Color.Transparent,
            topLeft = centerTopRect.topLeft,
            size = centerTopRect.size
        )

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
            color =Color.Transparent,
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

        /**
         * Drow lines for Tic Toc Toe Section ends
         * **/


    }
}