package com.example.vkclockapp.drawings

import kotlin.math.cos
import kotlin.math.sin

object ClockGeometry {

    fun getRadianAngle(angle: Float): Double {
        return angle * (Math.PI / 180)
    }

    fun getRadianAngle(angle: Int): Double {
        return angle * (Math.PI / 180)
    }

    fun calculateX(cordX: Float, radius: Int, rotationRadian: Double): Float {
        return ((cordX + (radius * cos(rotationRadian))).toFloat())
    }

    fun calculateY(cordY: Float, radius: Int, rotationRadian: Double): Float {
        return ((cordY + (radius.times(sin(rotationRadian)))).toFloat())
    }
}