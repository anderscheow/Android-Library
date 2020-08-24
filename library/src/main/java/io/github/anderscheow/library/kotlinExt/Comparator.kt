package io.github.anderscheow.library.kotlinExt

import java.util.*

infix fun <T> Boolean.then(value: T?) = if (this) value else null

inline fun <T> Boolean.then(function: () -> T, default: () -> T) = if (this) function() else default()

inline infix fun <reified T> Boolean.then(function: () -> T) = if (this) function() else null

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