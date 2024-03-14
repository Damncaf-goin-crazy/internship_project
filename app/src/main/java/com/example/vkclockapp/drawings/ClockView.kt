package com.example.vkclockapp.drawings

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.vkclockapp.R
import com.example.vkclockapp.clock.ClockCenter
import com.example.vkclockapp.clock.ClockCover
import com.example.vkclockapp.clock.ClockLine
import com.example.vkclockapp.clock.ClockTimer
import com.example.vkclockapp.clock.Coordinates
import com.example.vkclockapp.clock.Interval
import com.example.vkclockapp.clock.IntervalNumber


class ClockView : View, ClockTimer.OnClockTimerListener {

    companion object {
        private const val MARGIN = 16

        private const val MARGIN_SECOND_VIEW = 80

        private const val MARGIN_MINUTE_VIEW = 120

        private const val MARGIN_HOUR_VIEW = 160
    }

    private var black: Int = 0

    private var pink: Int = 0

    private var mFinalWidth: Int = 0

    private var mFinalHeight: Int = 0

    private var mRadius: Int = 0

    private lateinit var blackPaint: Paint

    private lateinit var mClockCoverPaint: Paint

    private lateinit var mClockCenterPaint: Paint

    private lateinit var mIntervalLinePaint: Paint

    private lateinit var mIntervalNumberPaint: Paint

    private lateinit var mSecondTickerPaint: Paint

    private lateinit var mMinutesTickerPaint: Paint

    private lateinit var mHoursTickerPaint: Paint

    private lateinit var mClockBackgroundPaint: Paint

    private lateinit var mClockOver: ClockCover

    private lateinit var mClockCenter: ClockCenter

    private lateinit var mMinutesTicker: ClockLine

    private lateinit var mSecondsTicker: ClockLine

    private lateinit var mHoursTicker: ClockLine

    private val mIntervals = ArrayList<Interval>()

    private var mSecondsRotation: Double = 0.0

    private var mMinutesRotation: Double = 0.0

    private var mHoursRotation: Double = 0.0

    private lateinit var mClockTimer: ClockTimer

    constructor(context: Context?) : super(context) {
        initPaint()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initPaint()
    }

    private fun initPaint() {
        mClockTimer = ClockTimer(this)
        black = ContextCompat.getColor(context, R.color.black)

        pink = ContextCompat.getColor(context, R.color.pink)

        blackPaint = Paint().apply {
            color = black
            strokeCap = Paint.Cap.ROUND
        }

        mClockCoverPaint = Paint(blackPaint).apply {
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }

        mClockCenterPaint = Paint().apply {
            color = black
            style = Paint.Style.FILL
            strokeWidth = 18f
        }

        mIntervalLinePaint = Paint(blackPaint).apply {
            style = Paint.Style.FILL
            strokeWidth = 8f
        }

        mIntervalNumberPaint = Paint().apply {
            color = black
        }

        mSecondTickerPaint = Paint(blackPaint).apply {
            strokeWidth = 9f
        }

        mMinutesTickerPaint = Paint(blackPaint).apply {
            strokeWidth = 12f
        }

        mHoursTickerPaint = Paint(blackPaint).apply {
            strokeWidth = 14f
        }

        mClockBackgroundPaint = Paint().apply {
            color = pink
        }

        mClockOver = ClockCover()
        mClockCenter = ClockCenter(ClockCostants.CENTER_RADIUS)
        mSecondsTicker = ClockLine()
        mMinutesTicker = ClockLine()
        mHoursTicker = ClockLine()

    }

    private fun initBaseParameter() {
        mClockOver.apply {
            setRect(MARGIN, MARGIN, mFinalWidth.minus(MARGIN), mFinalHeight.minus(MARGIN))//padding
            setPath()
        }

        mClockCenter.apply {
            coordinates = Coordinates(mFinalWidth.div(2f), mFinalHeight.div(2f))
        }

        mSecondsRotation = ClockGeometry.getRadianAngle(-ClockCostants.OFFSET_INTERVAL_ANGLE)
        val coordinatesCenter = mClockCenter.coordinates
        mSecondsTicker.apply {
            setStartXY(coordinatesCenter.cordX, coordinatesCenter.cordY)
            setEndXY(
                ClockGeometry.calculateX(
                    coordinatesCenter.cordX, mRadius.minus(
                        MARGIN_SECOND_VIEW
                    ), mSecondsRotation
                ), ClockGeometry.calculateY(
                    coordinatesCenter.cordY, mRadius.minus(
                        MARGIN_SECOND_VIEW
                    ), mSecondsRotation
                )
            )
        }

        mMinutesTicker.apply {
            setStartXY(coordinatesCenter.cordX, coordinatesCenter.cordY)
            setEndXY(
                ClockGeometry.calculateX(
                    coordinatesCenter.cordX, mRadius.minus(
                        MARGIN_MINUTE_VIEW
                    ), mSecondsRotation
                ), ClockGeometry.calculateY(
                    coordinatesCenter.cordY, mRadius.minus(
                        MARGIN_MINUTE_VIEW
                    ), mSecondsRotation
                )
            )
        }

        mHoursTicker.apply {
            setStartXY(coordinatesCenter.cordX, coordinatesCenter.cordY)
            setEndXY(
                ClockGeometry.calculateX(
                    coordinatesCenter.cordX, mRadius.minus(
                        MARGIN_HOUR_VIEW
                    ), mSecondsRotation
                ), ClockGeometry.calculateY(
                    coordinatesCenter.cordY, mRadius.minus(
                        MARGIN_HOUR_VIEW
                    ), mSecondsRotation
                )
            )
        }

        mIntervalNumberPaint.textSize = 0.07f * mFinalWidth
        if (mIntervals.isEmpty()) {
            for (i in 0 until ClockCostants.INTERVALS_COUNT) {
                val intervalLine = ClockLine()
                val intervalNumber = IntervalNumber()

                mSecondsRotation = ClockGeometry.getRadianAngle(
                    -ClockCostants.OFFSET_INTERVAL_ANGLE + ClockCostants.INTERVAL_ANGLE + (i * ClockCostants.INTERVAL_ANGLE)
                )

                val centerOfCoordinates = mClockCenter.coordinates

                // координаты для линий интервалов
                intervalLine.setStartXY(
                    ClockGeometry.calculateX(
                        centerOfCoordinates.cordX, mRadius.minus(MARGIN), mSecondsRotation
                    ), ClockGeometry.calculateY(
                        centerOfCoordinates.cordY, mRadius.minus(MARGIN), mSecondsRotation
                    )
                )

                intervalLine.setEndXY(
                    ClockGeometry.calculateX(
                        centerOfCoordinates.cordX, mRadius.minus(26), mSecondsRotation
                    ), ClockGeometry.calculateY(
                        centerOfCoordinates.cordY, mRadius.minus(26), mSecondsRotation
                    )
                )

                // координаты для цифр на циферблате
                intervalNumber.number = (i + 1).toString()
                intervalNumber.setCoordinates(
                    ClockGeometry.calculateX(
                        centerOfCoordinates.cordX, mRadius.minus(65), mSecondsRotation
                    ).minus(
                        (mIntervalNumberPaint.measureText(intervalNumber.number).div(2))
                    ), ClockGeometry.calculateY(
                        centerOfCoordinates.cordX, mRadius.minus(65), mSecondsRotation
                    ).minus(
                        (mIntervalNumberPaint.descent()
                            .plus(mIntervalNumberPaint.ascent().div(2))) - 6
                    )
                )
                mIntervals.add(Interval(intervalLine, intervalNumber))
            }
        }
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        mFinalWidth = measuredWidth / 2
//        mFinalHeight = measuredWidth / 2
//        mRadius = mFinalWidth / 2
//        setMeasuredDimension(mFinalWidth, mFinalHeight)
//    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // Минимальное измерение (width or height)
        val minDimension =
            minOf(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))

        // Обновить элементы, в зависимости от minDimension (высоту и ширину сделал одинаковыми,
        // чтобы часы не растягивались, а всегда оставались с фиксированными (равными) сторонами
        mFinalWidth = minDimension / 2
        mFinalHeight = minDimension / 2
        mRadius = mFinalWidth / 2

        setMeasuredDimension(mFinalWidth, mFinalHeight)


        initBaseParameter()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        initBaseParameter()
        start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Отрисовка цвета заднего фона
        canvas.drawCircle(
            mClockCenter.coordinates.cordX,
            mClockCenter.coordinates.cordY,
            (mRadius * 0.96).toFloat(),
            mClockBackgroundPaint
        )
        // Отрисовка круглой рамки
        mClockOver.path.let { mClockCoverPaint.let { it1 -> canvas.drawPath(it, it1) } }

        mClockCenter.coordinates.let {
            mClockCenterPaint.let { it1 ->
                canvas.drawCircle(
                    it.cordX, it.cordY, mClockCenter.radius.toFloat(), it1
                )
            }
        }

        mIntervals.forEach {
            val intervalNumber = it.intervalNumber
            val intervalLine = it.intervalLine

            // Линии интервалов
            canvas.drawLine(
                intervalLine.startXY.cordX,
                intervalLine.startXY.cordY,
                intervalLine.endXY.cordX,
                intervalLine.endXY.cordY,
                mIntervalLinePaint
            )


            // Отрисовка цифр на циферблате
            canvas.drawText(
                intervalNumber.number,
                intervalNumber.coordinates.cordX,
                intervalNumber.coordinates.cordY, //выравнивание цифр
                mIntervalNumberPaint
            )

        }

        // Отрисовка секундной стрелки
        canvas.drawLine(
            mSecondsTicker.startXY.cordX,
            mSecondsTicker.startXY.cordY,
            mSecondsTicker.endXY.cordX,
            mSecondsTicker.endXY.cordY,
            mSecondTickerPaint
        )


        // Отрисовка минутной стрелки
        canvas.drawLine(
            mMinutesTicker.startXY.cordX,
            mMinutesTicker.startXY.cordY,
            mMinutesTicker.endXY.cordX,
            mMinutesTicker.endXY.cordY,
            mMinutesTickerPaint
        )


        // Отрисовка часовой стрелки
        canvas.drawLine(
            mHoursTicker.startXY.cordX,
            mHoursTicker.startXY.cordY,
            mHoursTicker.endXY.cordX,
            mHoursTicker.endXY.cordY,
            mHoursTickerPaint
        )

    }

    override fun onNextSecond(second: Int) {
        mSecondsRotation = ClockGeometry.getRadianAngle(
            second.times(ClockCostants.MIN_ROTATION_ANGLE) - ClockCostants.OFFSET_INTERVAL_ANGLE
        )
        mSecondsTicker.apply {
            setEndXY(
                ClockGeometry.calculateX(
                    mClockCenter.coordinates.cordX, mRadius.minus(
                        MARGIN_SECOND_VIEW
                    ), mSecondsRotation
                ), ClockGeometry.calculateY(
                    mClockCenter.coordinates.cordY, mRadius.minus(
                        MARGIN_SECOND_VIEW
                    ), mSecondsRotation
                )
            )
        }
    }

    override fun onNextMinute(minute: Int) {
        mMinutesRotation = ClockGeometry.getRadianAngle(
            minute.times(ClockCostants.MIN_ROTATION_ANGLE) - ClockCostants.OFFSET_INTERVAL_ANGLE
        )
        mMinutesTicker.apply {
            setEndXY(
                ClockGeometry.calculateX(
                    mClockCenter.coordinates.cordX, mRadius.minus(
                        MARGIN_MINUTE_VIEW
                    ), mMinutesRotation
                ), ClockGeometry.calculateY(
                    mClockCenter.coordinates.cordY, mRadius.minus(
                        MARGIN_MINUTE_VIEW
                    ), mMinutesRotation
                )
            )
        }
    }

    override fun onNextHour(hour: Float) {
        mHoursRotation = ClockGeometry.getRadianAngle(
            hour.times(ClockCostants.MIN_HOUR_ROTATION_ANGLE) - ClockCostants.OFFSET_INTERVAL_ANGLE
        )
        mHoursTicker.apply {
            setEndXY(
                ClockGeometry.calculateX(
                    mClockCenter.coordinates.cordX, mRadius.minus(
                        MARGIN_HOUR_VIEW
                    ), mHoursRotation
                ), ClockGeometry.calculateY(
                    mClockCenter.coordinates.cordY, mRadius.minus(
                        MARGIN_HOUR_VIEW
                    ), mHoursRotation
                )
            )
        }
    }

    override fun onShouldUpdateClock() {
        invalidate()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    private fun start() {
        mClockTimer.start()
    }

    private fun stop() {
        mClockTimer.stop()
    }
}