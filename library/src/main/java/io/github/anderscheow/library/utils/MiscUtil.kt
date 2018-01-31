package io.github.anderscheow.library.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import java.util.ArrayList

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
}