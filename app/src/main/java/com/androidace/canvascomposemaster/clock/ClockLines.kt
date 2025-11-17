package com.androidace.canvascomposemaster.clock

sealed class ClockLines{
    data object MinuteLines: ClockLines()
    data object HourLines: ClockLines()
}