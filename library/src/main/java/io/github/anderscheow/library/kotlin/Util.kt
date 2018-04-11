package io.github.anderscheow.library.kotlin

import android.content.Context
import android.net.ConnectivityManager
import android.os.Handler
import java.util.*

//region Extensions

/** Extension for Context */
// Check is network available
fun Context.isConnectedToInternet(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    return connectivityManager?.activeNetworkInfo?.isConnected ?: false
}
//endregion

//region Non-extension

// Generate empty String
fun empty(): String {
    return ""
}

// Generate empty List
fun <T> emptyList(): List<T> {
    return ArrayList()
}

// Delay action within period of time
fun delay(timeInMilli: Long, action: () -> Unit) {
    Handler().postDelayed(action, timeInMilli)
}

// Throw NPE when detect is null, otherwise invoke action
fun assertNull(value: Any?, action: () -> Unit) {
    if (value == null) {
        throw NullPointerException()
    }

    action.invoke()
}
//endregion