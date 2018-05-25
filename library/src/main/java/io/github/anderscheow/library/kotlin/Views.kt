@file:Suppress("UNUSED")

package io.github.anderscheow.library.kotlin

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.widget.TextView

//region Extensions

/** Extension for AppBar */
// To enable or disable CollapsingToolbarLayout scrolling
fun AppBarLayout.setDragCallback(enable: Boolean) {
    (this.layoutParams as? CoordinatorLayout.LayoutParams)?.let { layoutParams ->
        layoutParams.behavior?.let { behavior ->
            (behavior as? AppBarLayout.Behavior)?.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
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

fun View.fadeIn(timeInMillis: Long = 300,
                onAnimationStart: (() -> Unit)? = null,
                onAnimationEnd: (() -> Unit)? = null) {
    this.post {
        this.visibility = View.VISIBLE
        this.alpha = 0f

        this.animate()
                .alpha(1f)
                .setInterpolator(DecelerateInterpolator())
                .setDuration(timeInMillis)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        onAnimationStart?.invoke()
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        onAnimationEnd?.invoke()

                        this@fadeIn.visible()
                    }
                })
    }
}

fun View.fadeOut(timeInMillis: Long = 300, gone: Boolean = true,
                 onAnimationStart: (() -> Unit)? = null,
                 onAnimationEnd: (() -> Unit)? = null) {
    this.post {
        this.animate()
                .alpha(0f)
                .setInterpolator(AccelerateInterpolator())
                .setDuration(timeInMillis)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        onAnimationStart?.invoke()
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        onAnimationEnd?.invoke()

                        if (gone) this@fadeOut.gone() else this@fadeOut.invisible()
                    }
                })
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
    view.gone()
}

// Iterate views and change its visibility to VISIBLE
fun showFields(vararg views: View) {
    for (view in views) {
        view.visible()
    }
}

fun showField(view: View) {
    view.visible()
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
    view.enable()
}

// Iterate views and disable it
fun disableViews(vararg views: View) {
    for (view in views) {
        view.disable()
    }
}

fun disableView(view: View) {
    view.disable()
}
//endregion