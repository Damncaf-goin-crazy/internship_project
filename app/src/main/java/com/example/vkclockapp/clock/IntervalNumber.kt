package com.example.vkclockapp.clock



class IntervalNumber {

    var number: String = ""

    var coordinates = Coordinates()

    fun setCoordinates(x: Float, y: Float) {
        this.coordinates.cordX = x
        this.coordinates.cordY = y
    }
}