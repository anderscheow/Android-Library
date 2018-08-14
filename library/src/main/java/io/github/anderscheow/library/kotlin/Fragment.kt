package io.github.anderscheow.library.kotlin

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment

fun Fragment.isNotThere(): Boolean = (activity?.isFinishing ?: true) || isDetached

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