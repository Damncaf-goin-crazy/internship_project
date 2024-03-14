package com.example.vkclockapp.clock

import android.graphics.Path
import android.graphics.RectF
import com.example.vkclockapp.drawings.ClockCostants


class ClockCover {
    // Граница круга
    val path = Path()

    private val rectF = RectF()

    fun setRect(left: Int, top: Int, bottom: Int, right: Int) {
        rectF.set(left.toFloat(), top.toFloat(), bottom.toFloat(), right.toFloat())
    }

    // Должен быть вызван после setRect
    fun setPath() {
        path.arcTo(rectF, ClockCostants.MIN_DEGREES.toFloat(), (ClockCostants.MAX_DEGREES - 1).toFloat(), true)
    }

}

