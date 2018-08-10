package io.github.anderscheow.library.adapters.view_holder

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import io.github.anderscheow.library.BR

abstract class MyBaseViewHolder<in T>(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    private var item: T? = null

    protected abstract fun extraBinding(item: T)

    protected abstract fun onClick(view: View, item: T?)

    init {
        init()
    }

    private fun init() {
        this.binding.root.setOnClickListener {
            onClick(binding.root, item)
        }
    }

    fun bind(item: T?) {
        item?.let {
            this.item = it

            binding.setVariable(BR.obj, it)
            binding.executePendingBindings()

            extraBinding(it)
        }
    }
}
