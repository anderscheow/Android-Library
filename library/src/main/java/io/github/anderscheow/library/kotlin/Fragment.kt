package io.github.anderscheow.library.kotlin

import android.support.v4.app.Fragment

fun Fragment.isNotThere(): Boolean = (activity?.isFinishing ?: true) || isDetached