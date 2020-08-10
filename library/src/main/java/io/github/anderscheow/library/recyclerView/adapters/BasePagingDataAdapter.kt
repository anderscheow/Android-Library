package io.github.anderscheow.library.recyclerView.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.anderscheow.library.recyclerView.viewHolder.BaseViewHolder

abstract class BasePagingDataAdapter<Value : Any>(
        diffCallback: DiffUtil.ItemCallback<Value>
) : PagingDataAdapter<Value, RecyclerView.ViewHolder>(diffCallback) {

    @LayoutRes
    protected abstract fun getBodyLayout(position: Int): Int

    protected abstract fun getBodyViewHolder(viewType: Int, binding: ViewDataBinding): RecyclerView.ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                layoutInflater, viewType, parent, false)

        return getBodyViewHolder(viewType, binding)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)

        if (itemViewType == getBodyLayout(position)) {
            if (holder !is BaseViewHolder<*>) {
                throw IllegalStateException("Must inherit BaseViewHolder for body view holder")
            }
            (holder as? BaseViewHolder<Value>)?.bind(getItem(holder.bindingAdapterPosition))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getBodyLayout(position)
    }
}