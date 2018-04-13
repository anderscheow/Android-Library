package io.github.anderscheow.library.kotlin

import android.os.Build
import android.text.Html
import android.text.Spanned
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

//region Extensions

/** Extension for String */
fun String.formatToSpanned(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }
}

/** Extension for Long */
@Suppress("UNUSED")
fun Long?.formatAmount(format: String? = null): String {
    val d = (this ?: 0) / 100.0
    val formatter = DecimalFormat(format ?: "###,###,##0.00")

    return formatter.format(d)
}

@Suppress("UNUSED")
fun Long?.formatDateWithYear(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this ?: 0

    return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(calendar.time)
}

@Suppress("UNUSED")
fun Long?.formatDateWithoutYear(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this ?: 0

    return SimpleDateFormat("dd MMM", Locale.getDefault()).format(calendar.time)
}

@Suppress("UNUSED")
fun Long?.formatTime(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this ?: 0

    return SimpleDateFormat("h:mm a", Locale.getDefault()).format(calendar.time)
}

@Suppress("UNUSED")
fun Long?.formatMinuteSecond(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this ?: 0

    return SimpleDateFormat("mm:ss", Locale.getDefault()).format(calendar.time)
}

@Suppress("UNUSED")
fun Long?.formatSecond(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this ?: 0

    return SimpleDateFormat("0:ss", Locale.getDefault()).format(calendar.time)
}

@Suppress("UNUSED")
fun Long?.formatDateTime(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this ?: 0

    return SimpleDateFormat("dd MMM yyyy, h:mm a", Locale.getDefault()).format(calendar.time)
}

@Suppress("UNUSED")
fun Long?.formatDateTime24Hours(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this ?: 0

    return SimpleDateFormat("dd MMM yyyy, HH:mm a", Locale.getDefault()).format(calendar.time)
}

@Suppress("UNUSED")
fun Long?.formatDate(format: String): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this ?: 0

    return SimpleDateFormat(format, Locale.getDefault()).format(calendar.time)
}

/** Extension for Double */
@Suppress("UNUSED_PARAMETER", "UNUSED")
fun Double?.formatAmount(firstFormat: String? = null, secondFormat: String? = null): String {
    val twoZeroFormatted = this.formatAmount(firstFormat ?: "###,###,##0.00")

    if (twoZeroFormatted == "0.00") {
        val fourZeroFormatted = this.formatAmount(firstFormat ?: "###,###,##0.0000")

        if (fourZeroFormatted == "0.0000") {
            return fourZeroFormatted
        }
    }

    return twoZeroFormatted
}

fun Double?.formatAmount(format: String): String {
    val formatter = DecimalFormat(format)
    formatter.roundingMode = RoundingMode.DOWN

    return formatter.format(this ?: 0)
}

/** Extension for CharSequence */
@Suppress("UNUSED")
fun CharSequence.trimToString(): String {
    return this.toString().trim()
}
//endregion

//region Non-extension
fun toSpanned(value: String): Spanned {
    return value.formatToSpanned()
}
//endregion