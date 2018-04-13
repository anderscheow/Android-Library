package io.github.anderscheow.library.kotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
@Suppress("UNUSED")
fun empty(): String {
    return ""
}

// Generate empty List
@Suppress("UNUSED")
fun <T> emptyList(): List<T> {
    return ArrayList()
}

// Delay action within period of time
@Suppress("UNUSED")
fun delay(timeInMilli: Long, action: () -> Unit) {
    Handler().postDelayed(action, timeInMilli)
}

// Throw NPE when detect is null, otherwise invoke action
@Suppress("UNUSED")
fun assertNull(value: Any?, action: () -> Unit) {
    if (value == null) {
        throw NullPointerException()
    }

    action.invoke()
}

// To detect internet conenctivity
@Suppress("UNUSED")
val connectivityIntentFilter = IntentFilter().apply {
    this.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
}

@Suppress("UNUSED")
fun getConnectivityReceiver(action: () -> Unit): BroadcastReceiver {
    return object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            action.invoke()
        }
    }
}
//endregion