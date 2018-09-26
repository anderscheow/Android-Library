package io.github.anderscheow.library.adapters.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.anderscheow.library.adapters.view_holder.MyBaseViewHolder

@Suppress("UNUSED")
abstract class BasePagedListWithoutNetworkStateAdapter<T>(
        diffCallback: DiffUtil.ItemCallback<T>)
    : FoundationPagedListAdapter<T>(diffCallback, null) {

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
            if (holder !is MyBaseViewHolder<*>) {
                throw IllegalStateException("Must inherit MyBaseViewHolder for body view holder")
            }
            (holder as? MyBaseViewHolder<T>)?.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getBodyLayout(position)
    }
}
