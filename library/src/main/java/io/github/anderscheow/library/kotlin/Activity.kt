@file:Suppress("UNUSED")

package io.github.anderscheow.library.kotlin

import android.app.Activity
import android.util.DisplayMetrics

/**
 * Extension method to retrieve current activity's display metrics
 */
private var displayMetrics: DisplayMetrics? = null

fun Activity.getDisplayMetrics(): DisplayMetrics? {
    if (displayMetrics == null) {
        val newDisplayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(DisplayMetrics())

        displayMetrics = newDisplayMetrics
    }

    return displayMetrics
}