package io.github.anderscheow.library.utils

import android.support.annotation.LayoutRes

@Suppress("UNUSED")
data class BodyViewTypeHolder(val identifier: String,
                              @get:LayoutRes
                              val layout: Int)