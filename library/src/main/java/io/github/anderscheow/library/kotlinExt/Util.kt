package io.github.anderscheow.library.kotlinExt

import android.os.Build
import android.os.Handler
import java.util.*

//region Extensions
//endregion

//region Non-extension

// Generate empty String
fun empty(): String {
    return ""
}

// Delay action within period of time
fun delay(timeInMilli: Long, action: () -> Unit) {
    Handler().postDelayed(action, timeInMilli)
}

// Throw NPE when detect is null, otherwise invoke action
inline fun assertNull(value: Any?, message: String? = null, action: () -> Unit) {
    if (value == null) {
        if (message == null) {
            throw NullPointerException()
        } else {
            throw NullPointerException(message)
        }
    }

    action.invoke()
}

// Thread safe lazy initializer
fun <T> lazyThreadSafetyNone(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)

/**
 * Method to check is aboveApi.
 */
inline fun aboveApi(api: Int, included: Boolean = false, block: () -> Unit) {
    if (Build.VERSION.SDK_INT > included then api - 1 ?: api) {
        block()
    }
}

/**
 * Method to check is belowApi.
 */
inline fun belowApi(api: Int, included: Boolean = false, block: () -> Unit) {
    if (Build.VERSION.SDK_INT < included then api + 1 ?: api) {
        block()
    }
}

/**
 * Method to get the TAG name for all object
 */
@Suppress("FunctionName")
val <T : Any> T.TAG
    get() = this::class.simpleName!!

fun <T> copyIterator(iterator: Iterator<T>?): List<T> {
    val copy = ArrayList<T>()
    if (iterator != null) {
        while (iterator.hasNext())
            copy.add(iterator.next())
    }
    return copy
}
//endregion