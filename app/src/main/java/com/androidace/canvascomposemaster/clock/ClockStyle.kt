package com.androidace.canvascomposemaster.clock

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ClockStyle(
    val scaleWidth: Dp = 80.dp,
    val radius: Dp = 200.dp,
    val minuteLineColor: Color = Color.LightGray,
    val hourLineColor: Color = Color.Black,
    val minuteLineLength: Dp = 15.dp,
    val hourLineLength: Dp = 25.dp,
    val minuteHandColor: Color = Color.Black,
    val hourHandColor: Color = Color.Black,
    val secondHandColor: Color = Color.Red,
    val textSize: TextUnit = 18.sp
)
