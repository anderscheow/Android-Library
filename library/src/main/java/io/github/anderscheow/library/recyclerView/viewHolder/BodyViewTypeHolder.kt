package io.github.anderscheow.library.recyclerView.viewHolder

import androidx.annotation.LayoutRes

data class BodyViewTypeHolder(val identifier: String,
                              @get:LayoutRes
                              val layout: Int)