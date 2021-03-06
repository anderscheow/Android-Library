package io.github.anderscheow.library.kotlinExt

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Extension method to retrieve current activity's display metrics
 */
private var displayMetrics: DisplayMetrics? = null

fun Activity.getDisplayMetrics(): DisplayMetrics? {
    if (displayMetrics == null) {
        val newDisplayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(newDisplayMetrics)

        displayMetrics = newDisplayMetrics
    }

    return displayMetrics
}

fun Activity.getStatusBarHeight(): Int {
    var result = 0
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
    }
    return result
}

/**
 * Extension method to show keyboard
 */
fun Activity.showKeyboard(view: View?) {
    view?.let {
        it.requestFocus()

        (this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT)
    }
}

/**
 * Extension method to show keyboard
 */
fun Activity.showKeyboard() {
    showKeyboard(currentFocus)
}

/**
 * Extension method to hide keyboard
 */
fun Activity.hideKeyboard(view: View?) {
    view?.let {
        (this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

/**
 * Extension method to hide keyboard
 */
fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus)
}

/**
 * Extension method to hide system UI
 */
@Suppress("DEPRECATION")
fun Activity.hideSystemUI() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    } else {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}

/**
 * Extension method to show system UI
 */
@Suppress("DEPRECATION")
fun Activity.showSystemUI() {
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
}