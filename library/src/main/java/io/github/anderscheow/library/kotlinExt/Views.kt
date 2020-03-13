package io.github.anderscheow.library.kotlinExt

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.TextViewCompat
import com.google.android.material.appbar.AppBarLayout
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.textColor

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
                onAnimationStart: ((Animator?) -> Unit)? = null,
                onAnimationEnd: ((Animator?) -> Unit)? = null) {
    this.post {
        this.visibility = View.VISIBLE
        this.alpha = 0f

        this.animate()
                .alpha(1f)
                .setInterpolator(DecelerateInterpolator())
                .setDuration(timeInMillis)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        onAnimationStart?.invoke(animation)
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        onAnimationEnd?.invoke(animation)

                        this@fadeIn.visible()
                    }
                })
    }
}

fun View.fadeOut(timeInMillis: Long = 300, gone: Boolean = true,
                 onAnimationStart: ((Animator?) -> Unit)? = null,
                 onAnimationEnd: ((Animator?) -> Unit)? = null) {
    this.post {
        this.animate()
                .alpha(0f)
                .setInterpolator(AccelerateInterpolator())
                .setDuration(timeInMillis)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        onAnimationStart?.invoke(animation)
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        onAnimationEnd?.invoke(animation)

                        if (gone) this@fadeOut.gone() else this@fadeOut.invisible()
                    }
                })
    }
}

@Suppress("UNCHECKED_CAST")
fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener { block(it as T) }

@Suppress("UNCHECKED_CAST")
fun <T : View> T.longClick(block: (T) -> Boolean) = setOnLongClickListener { block(it as T) }

inline fun View.showIf(block: () -> Boolean) : View {
    if (visibility != View.VISIBLE && block()) {
        visibility = View.VISIBLE
    }
    return this
}

inline fun View.hideIf(block: () -> Boolean) : View {
    if (visibility != View.INVISIBLE && block()) {
        visibility = View.INVISIBLE
    }
    return this
}

inline fun View.removeIf(block: () -> Boolean) : View {
    if (visibility != View.GONE && block()) {
        visibility = View.GONE
    }
    return this
}

/** Extension for TextView */
fun TextView.setTextToSpanned(value: String) {
    this.text = toSpanned(value)
}

fun TextView.disableWith(backgroundResource: Int = 0, textColor: Int = 0) {
    this.disable()
    if (backgroundResource > 0) this.backgroundResource = backgroundResource
    if (textColor > 0) this.textColor = textColor
}

fun TextView.enableWith(backgroundResource: Int = 0, textColor: Int = 0) {
    this.enable()
    if (backgroundResource > 0) this.backgroundResource = backgroundResource
    if (textColor > 0) this.textColor = textColor
}

fun TextView.setSizeTextTypeToUniform() {
    TextViewCompat.setAutoSizeTextTypeWithDefaults(this, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
}

fun TextView.setSizeTextTypeToNone() {
    TextViewCompat.setAutoSizeTextTypeWithDefaults(this, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE)
}

fun TextView.setDefaultOnEditorActionListener(block: (TextView, Int, KeyEvent) -> Unit) {
    this.setOnEditorActionListener { v, actionId, event ->
        block.invoke(v, actionId, event)
        true
    }
}

/** Extension for EditText */
open class DefaultTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
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

// Popup Window
fun PopupWindow.dimBehind() {
    val container: View = if (this.background == null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.contentView.parent as View
        } else {
            this.contentView
        }
    } else {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.contentView.parent.parent as View
        } else {
            this.contentView.parent as View
        }
    }
    val context = this.contentView.context
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
    val p = container.layoutParams as? WindowManager.LayoutParams
    p?.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
    p?.dimAmount = 0.3f
    wm?.updateViewLayout(container, p)
}

fun generatePopupWindow(activity: Activity, layout: Int, block: (View) -> Unit): PopupWindow? {
    val layoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as? LayoutInflater
    var popupView: View
    layoutInflater?.let {
        popupView = it.inflate(layout, null)

        block(popupView)

        return PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            isOutsideTouchable = true
            isFocusable = true
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    return null
}
//endregion