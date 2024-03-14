package com.example.vkclockapp.clock


open class ClockLine {
    var startXY = Coordinates()

    var endXY = Coordinates()

    fun setStartXY(x: Float, y: Float) {
        startXY.cordX = x
        startXY.cordY = y
    }

    fun setEndXY(x: Float, y: Float) {
        endXY.cordX = x
        endXY.cordY = y
    }
}