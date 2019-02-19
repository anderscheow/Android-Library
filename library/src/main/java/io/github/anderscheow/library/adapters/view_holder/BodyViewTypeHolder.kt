package io.github.anderscheow.library.adapters.view_holder

import androidx.annotation.LayoutRes

data class BodyViewTypeHolder(val identifier: String,
                              @get:LayoutRes
                              val layout: Int)