package io.github.anderscheow.library.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import java.util.*

object MiscUtil {

    private var displayMetrics: DisplayMetrics? = null

    fun getDisplayMetrics(activity: Activity): DisplayMetrics? {
        if (displayMetrics == null) {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)

            MiscUtil.displayMetrics = displayMetrics
        }

        return displayMetrics
    }

    fun calculateNoOfColumns(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density

        return (dpWidth / 180).toInt()
    }

    fun <T> copyIterator(iterator: Iterator<T>?): List<T> {
        val copy = ArrayList<T>()
        if (iterator != null) {
            while (iterator.hasNext())
                copy.add(iterator.next())
        }
        return copy
    }

    fun showKeyboard(context: Context, view: View?) {
        view?.let {
            it.requestFocus()

            (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.
                    toggleSoftInput(InputMethodManager.SHOW_FORCED, SHOW_IMPLICIT)
        }
    }

    fun hideKeyboard(context: Context, view: View?) {
        view?.let {
            (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.
                    hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}