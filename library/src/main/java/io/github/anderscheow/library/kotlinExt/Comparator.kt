package io.github.anderscheow.library.kotlinExt

import java.util.*

fun compareTwoDates(startDate: Date, endDate: Date): Int {
    val sDate: Date = getZeroTimeDate(startDate)
    val eDate: Date = getZeroTimeDate(endDate)
    return when {
        sDate.before(eDate) -> -1
        sDate.after(eDate) -> 1
        else -> 0
    }
}

private fun getZeroTimeDate(date: Date): Date {
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = date
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.time
}