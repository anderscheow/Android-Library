package io.github.anderscheow.library.adapters.view_holder

import android.support.annotation.LayoutRes

@Suppress("UNUSED")
data class BodyViewTypeHolder(val identifier: String,
                              @get:LayoutRes
                              val layout: Int)