package com.example.vkclockapp.clock

import android.os.Handler
import android.os.Looper
import java.util.*



class ClockTimer(private var mOnClockTimerListener: OnClockTimerListener?) {

    companion object {
        internal const val TIMER = 1000L

        internal const val MAXIMUM_SECONDS = 60

        internal const val MAXIMUM_MINUTES = 60

        internal const val MAXIMUM_HOURS = 24
    }

    interface OnClockTimerListener {
        fun onNextSecond(second: Int)

        fun onNextMinute(minute: Int)

        fun onNextHour(hour: Float)

        fun onShouldUpdateClock()
    }

    var secondsCounter = 0

    var minutesCounter = 0

    var hoursCounter = 0

    private val mHandler = Handler(Looper.getMainLooper())


    private val mTimerRunnable = object : Runnable {
        override fun run() {
            secondsCounter += 1
            if (secondsCounter <= MAXIMUM_SECONDS)
                mOnClockTimerListener?.onNextSecond(secondsCounter)

            if (secondsCounter > MAXIMUM_SECONDS) {
                secondsCounter = 0
                // следующая минута
                minutesCounter += 1
                if (minutesCounter <= MAXIMUM_MINUTES)
                    mOnClockTimerListener?.onNextMinute(minutesCounter)

                if (minutesCounter > MAXIMUM_MINUTES) {
                    minutesCounter = 0
                    // следующий час
                    hoursCounter += 1
                    if (minutesCounter <= MAXIMUM_HOURS)
                        mOnClockTimerListener?.onNextHour((hoursCounter + (minutesCounter / MAXIMUM_MINUTES.toFloat())).toFloat())

                    if (hoursCounter > MAXIMUM_HOURS) {
                        hoursCounter = 0
                    }
                }
            }

            mOnClockTimerListener?.onShouldUpdateClock()
            mHandler.postDelayed(this, TIMER)
        }
    }

    fun start() {
        updateTimeFromDevices()
        mHandler.postDelayed(mTimerRunnable, TIMER)
    }

    fun stop() {
        mHandler.removeCallbacksAndMessages(null)
    }

    private fun updateTimeFromDevices() {
        val rightNow = Calendar.getInstance()

        hoursCounter = rightNow.get(Calendar.HOUR_OF_DAY)
        if (hoursCounter > 12) {
            hoursCounter -= 12
        }
        minutesCounter = rightNow.get(Calendar.MINUTE)
        secondsCounter = rightNow.get(Calendar.SECOND)

        mOnClockTimerListener?.onNextSecond(secondsCounter)
        mOnClockTimerListener?.onNextMinute(minutesCounter)
        mOnClockTimerListener?.onNextHour((hoursCounter + (minutesCounter / MAXIMUM_MINUTES.toFloat())))
        mOnClockTimerListener?.onShouldUpdateClock()
    }


}