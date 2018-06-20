@file:Suppress("UNUSED")

package io.github.anderscheow.library.kotlin

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import org.jetbrains.anko.browse

/**
 * Extension method to rate app on PlayStore for Context.
 */
fun Context.rate(packageName: String = this.packageName): Boolean =
        browse("market://details?id=$packageName") or
                browse("http://play.google.com/store/apps/details?id=$packageName")

/**
 * Extension method to check network availability
 */
fun Context.isConnectedToInternet(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    return connectivityManager?.activeNetworkInfo?.isConnected ?: false
}

/**
 * Extension method to find color based on color resource.
 */
fun Context.findColor(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

/**
 * Extension method to find drawable based on drawable resource.
 */
fun Context.findDrawable(@DrawableRes resId: Int): Drawable? = ContextCompat.getDrawable(this, resId)