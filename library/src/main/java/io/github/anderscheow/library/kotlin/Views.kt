package io.github.anderscheow.library.kotlin

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.view.View
import android.widget.EditText
import android.widget.TextView

//region Extensions

/** Extension for AppBar */
// To enable or disable CollapingToolbarLayout scrolling
fun AppBarLayout.setDragCallback(enable: Boolean) {
    (this.layoutParams as? CoordinatorLayout.LayoutParams)?.let { layoutParams ->
        layoutParams.behavior?.let { behavior ->
            (behavior as? AppBarLayout.Behavior)?.
                    setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                        override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                            return enable
                        }
                    })
        }
    }
}

/** Extension for View */
fun View.gone() {
    if (this.visibility != View.GONE) {
        this.visibility = View.GONE
    }
}

fun View.invisible() {
    if (this.visibility != View.INVISIBLE) {
        this.visibility = View.INVISIBLE
    }
}

fun View.visible() {
    if (this.visibility != View.VISIBLE) {
        this.visibility = View.VISIBLE
    }
}

fun View.enable() {
    if (!this.isEnabled) {
        this.isEnabled = true
    }
}

fun View.disable() {
    if (this.isEnabled) {
        this.isEnabled = false
    }
}

/** Extension for TextView */
fun TextView.setTextToSpanned(value: String) {
    this.text = toSpanned(value)
}
//endregion

//region Non-extension

// Iterate views and change its visibility to GONE
fun hideFields(vararg views: View) {
    for (view in views) {
        view.gone()
    }
}

fun hideField(view: View) {
    hideFields(view)
}

// Iterate views and change its visibility to VISIBLE
fun showFields(vararg views: View) {
    for (view in views) {
        view.visible()
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

fun clearText(editText: EditText) {
    clearTexts(editText)
}

// Iterate views and enable it
fun enableViews(vararg views: View) {
    for (view in views) {
        view.enable()
    }
}

fun enableView(view: View) {
    enableViews(view)
}

// Iterate views and disable it
fun disableViews(vararg views: View) {
    for (view in views) {
        view.disable()
    }
}

fun disableView(view: View) {
    disableViews(view)
}
//endregion