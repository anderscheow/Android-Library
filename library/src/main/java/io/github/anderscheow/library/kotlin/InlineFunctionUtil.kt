package io.github.anderscheow.library.kotlin

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Handler
import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/** Check not null */
fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, R : Any> safeLet(p1: T1?, p2: T2?, p3: T3?, block: (T1, T2, T3) -> R?): R? {
    return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> safeLet(p1: T1?, p2: T2?, p3: T3?, p4: T4?, block: (T1, T2, T3, T4) -> R?): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null) block(p1, p2, p3, p4) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> safeLet(p1: T1?, p2: T2?, p3: T3?, p4: T4?, p5: T5?, block: (T1, T2, T3, T4, T5) -> R?): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null && p5 != null) block(p1, p2, p3, p4, p5) else null
}

/** Check is String and is not null and not empty */
fun <T : Any, R : Any> isNotNullAndNotEmpty(p1: T?, block: (T) -> R?): R? {
    if (p1 is String? && p1.isNotNullAndNotEmpty()) {
        p1?.let { block(it) }
    }

    return null
}

fun <T : Any, R : Any> isNotNullAndNotEmpty(p1: T?, p2: T?, block: (T, T) -> R?): R? {
    if (p1 is String && p2 is String) {
        if (p1.isNotNullAndNotEmpty() && p2.isNotNullAndNotEmpty()) {
            block(p1, p2)
        }
    }

    return null
}

fun <T : Any, R : Any> isNotNullAndNotEmpty(p1: T?, p2: T?, p3: T?, block: (T, T, T) -> R?): R? {
    if (p1 is String && p2 is String && p3 is String) {
        if (p1.isNotNullAndNotEmpty() && p2.isNotNullAndNotEmpty() && p3.isNotNullAndNotEmpty()) {
            block(p1, p2, p3)
        }
    }

    return null
}

fun <T : Any, R : Any> isNotNullAndNotEmpty(p1: T?, p2: T?, p3: T?, p4: T?, block: (T, T, T, T) -> R?): R? {
    if (p1 is String && p2 is String && p3 is String && p4 is String) {
        if (p1.isNotNullAndNotEmpty() && p2.isNotNullAndNotEmpty() && p3.isNotNullAndNotEmpty() && p4.isNotNullAndNotEmpty()) {
            block(p1, p2, p3, p4)
        }
    }

    return null
}

fun <T : Any, R : Any> isNotNullAndNotEmpty(p1: T?, p2: T?, p3: T?, p4: T?, p5: T?, block: (T, T, T, T, T) -> R?): R? {
    if (p1 is String && p2 is String && p3 is String && p4 is String && p5 is String) {
        if (p1.isNotNullAndNotEmpty() && p2.isNotNullAndNotEmpty() && p3.isNotNullAndNotEmpty() && p4.isNotNullAndNotEmpty() && p5.isNotNullAndNotEmpty()) {
            block(p1, p2, p3, p4, p5)
        }
    }

    return null
}

/** Util */
// Generate empty String
fun empty(): String {
    return ""
}

// Generate empty List
fun <T> emptyList(): List<T> {
    return ArrayList()
}

fun toSpanned(value: String): Spanned {
    return value.formatToSpanned()
}

// Delay action within period of time
fun delay(timeInMilli: Long, action: () -> Unit) {
    Handler().postDelayed(action, timeInMilli)
}

// Iterate views and change its visibility to GONE
fun hideFields(vararg views: View) {
    for (view in views) {
        if (view.visibility != View.GONE) view.visibility = View.GONE
    }
}

fun hideField(view: View) {
    hideFields(view)
}

// Iterate views and change its visibility to VISIBLE
fun showFields(vararg views: View) {
    for (view in views) {
        if (view.visibility != View.VISIBLE) view.visibility = View.VISIBLE
    }
}

fun showField(view: View) {
    showFields(view)
}

// Iterate edit texts and clear its text
fun clearTexts(vararg editTexts: EditText) {
    for (editText in editTexts) {
        editText.text.clear()
    }
}

/** Extension for String */
// Check string is not null and not empty
fun String?.isNotNullAndNotEmpty(): Boolean {
    return this != null && this.isNotEmpty()
}

fun String.formatToSpanned(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }
}

/** Extension for Context */
// Check is network available
fun Context.isConnectedToInternet(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    return connectivityManager?.activeNetworkInfo?.isConnected ?: false
}

/** Extension for Long */
fun Long?.formatAmount(format: String? = null): String {
    val d = (this ?: 0) / 100.0
    val formatter = DecimalFormat(format ?: "###,###,##0.00")

    return formatter.format(d)
}

fun Long?.formatDateWithYear(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this ?: 0

    return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(calendar.time)
}

fun Long?.formatDateWithoutYear(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this ?: 0

    return SimpleDateFormat("dd MMM", Locale.getDefault()).format(calendar.time)
}

fun Long?.formatTime(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this ?: 0

    return SimpleDateFormat("h:mm a", Locale.getDefault()).format(calendar.time)
}

fun Long?.formatMinuteSecond(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this ?: 0

    return SimpleDateFormat("mm:ss", Locale.getDefault()).format(calendar.time)
}

fun Long?.formatSecond(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this ?: 0

    return SimpleDateFormat("0:ss", Locale.getDefault()).format(calendar.time)
}

fun Long?.formatDateTime(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this ?: 0

    return SimpleDateFormat("dd MMM yyyy, h:mm a", Locale.getDefault()).format(calendar.time)
}

fun Long?.formatDateTime24Hours(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this ?: 0

    return SimpleDateFormat("dd MMM yyyy, HH:mm a", Locale.getDefault()).format(calendar.time)
}

fun Long?.formatDate(format: String): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this ?: 0

    return SimpleDateFormat(format, Locale.getDefault()).format(calendar.time)
}

/** Extension for Double */
@Suppress("UNUSED_PARAMETER")
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

/** Extension for View */
fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

/** Extension for TextView */
fun TextView.setTextToSpanned(value: String) {
    this.text = toSpanned(value)
}

/** Extension for CharSequence */
fun CharSequence.trimToString(): String {
    return this.toString().trim()
}