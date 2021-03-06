package io.github.anderscheow.library.kotlinExt

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

fun Fragment.isNotThere(): Boolean = (activity?.isFinishing ?: true) || isDetached

inline fun Fragment.isAdded(block: () -> Unit) {
    if (isAdded) block.invoke()
}

inline fun Fragment.isAdded(block: () -> Unit, fallback: () -> Unit) {
    if (isAdded) block.invoke()
    else fallback.invoke()
}

inline fun Fragment.withContext(block: (Context) -> Unit) {
    context?.let(block)
}

@Suppress("UNCHECKED_CAST")
inline fun <T> Fragment.withContextAs(block: (T) -> Unit) {
    (context as? T)?.let(block)
}

@Suppress("UNCHECKED_CAST")
fun <T> Fragment.withContextAs(): T? {
    return (context as? T)
}

inline fun Fragment.withActivity(block: (Activity) -> Unit) {
    activity?.let(block)
}

@Suppress("UNCHECKED_CAST")
inline fun <T> Fragment.withActivityAs(block: (T) -> Unit) {
    (activity as? T)?.let(block)
}

@Suppress("UNCHECKED_CAST")
fun <T> Fragment.withActivityAs(): T? {
    return (activity as? T)
}

fun Fragment.toast(textResource: Int) = activity?.toast(textResource)

fun Fragment.toast(text: CharSequence) = activity?.toast(text)

fun Fragment.longToast(textResource: Int) = activity?.longToast(textResource)

fun Fragment.longToast(text: CharSequence) = activity?.longToast(text)

/**
 * Extension method to show keyboard
 */
fun Fragment.showKeyboard(view: View?) {
    activity?.showKeyboard(view)
}

/**
 * Extension method to show keyboard
 */
fun Fragment.showKeyboard() {
    activity?.showKeyboard()
}

/**
 * Extension method to hide keyboard
 */
fun Fragment.hideKeyboard(view: View?) {
    activity?.hideKeyboard(view)
}

/**
 * Extension method to hide keyboard
 */
fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}


/**
 * Extension method to hide system UI
 */
fun Fragment.hideSystemUI() {
    activity?.hideSystemUI()
}

/**
 * Extension method to show system UI
 */
fun Fragment.showSystemUI() {
    activity?.showSystemUI()
}