@file:Suppress("UNUSED")

package io.github.anderscheow.library.kotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Handler
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import java.util.*

//region Extensions
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

// To detect internet connectivity
val connectivityIntentFilter = IntentFilter().apply {
    this.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
}

fun getConnectivityReceiver(action: () -> Unit): BroadcastReceiver {
    return object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            action.invoke()
        }
    }
}

// Thread safe lazy initializer
fun <T> lazyThreadSafetyNone(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)

/**
 * Extension method to check is aboveApi.
 */
inline fun aboveApi(api: Int, included: Boolean = false, block: () -> Unit) {
    if (Build.VERSION.SDK_INT > if (included) api - 1 else api) {
        block()
    }
}

/**
 * Extension method to check is belowApi.
 */
inline fun belowApi(api: Int, included: Boolean = false, block: () -> Unit) {
    if (Build.VERSION.SDK_INT < if (included) api + 1 else api) {
        block()
    }
}

/**
 * Extension method to get the TAG name for all object
 */
@Suppress("FunctionName")
fun <T : Any> T.TAG() = this::class.simpleName
//endregion