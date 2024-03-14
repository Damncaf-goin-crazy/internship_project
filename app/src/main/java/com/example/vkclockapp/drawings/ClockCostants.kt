package com.example.vkclockapp.drawings

object ClockCostants {
    // Смещение угла интервала по умолчанию составляет 0 градусов на оси x.
    // Без смещения угла отсчет начинается с оси x, и часы кажутся опережающими на 90 градусов.
    const val OFFSET_INTERVAL_ANGLE = 90

    // Это максимальное количество интервалов, которое мы хотим видеть на часах.(12)
    const val INTERVALS_COUNT = 12

    const val MAX_DEGREES = 360

    const val MIN_DEGREES = 0

    const val MIN_ROTATION_ANGLE = MAX_DEGREES / 60

    const val MIN_HOUR_ROTATION_ANGLE = MAX_DEGREES / 12

    // Это зависит от INTERVALS_COUNT. Это угол, на который отклоняется каждый тик часовой стрелки
    // например, если INTERVALS_COUNT = 12, то каждому тику потребуется двигаться на 30 градусов,
    // чтобы перейти к следующему интервалу

    const val INTERVAL_ANGLE = MAX_DEGREES / INTERVALS_COUNT

    // Радиус центрального круга по дефолту
    const val CENTER_RADIUS = 5

}