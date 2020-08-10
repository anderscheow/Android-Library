package io.github.anderscheow.library.recyclerView.viewHolder

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import io.github.anderscheow.library.BR

abstract class BaseViewHolder<in Value>(
    private val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var item: Value? = null

    protected abstract fun extraBinding(item: Value)

    protected abstract fun onClick(view: View, item: Value?)

    init {
        init()
    }

    private fun init() {
        this.binding.root.setOnClickListener {
            onClick(binding.root, item)
        }
    }

    fun bind(item: Value?) {
        item?.let {
            this.item = it

            binding.setVariable(BR.obj, it)
            binding.executePendingBindings()

            extraBinding(it)
        }
    }
}
