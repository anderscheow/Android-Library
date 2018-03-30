package io.github.anderscheow.library.utils

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView

data class BodyViewTypeHolder(val identifier: String,
                              @get:LayoutRes
                              val layout: Int,
                              val viewHolder: RecyclerView.ViewHolder)