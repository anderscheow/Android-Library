package io.github.anderscheow.library.adapters.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anderscheow.library.adapters.view_holder.MyBaseViewHolder
import io.github.anderscheow.library.adapters.view_holder.NetworkStateViewHolder
import io.github.anderscheow.library.databinding.ViewNetworkStateBinding

abstract class BasePagedListAdapter<T>(
        private val callback: () -> Unit,
        diffCallback: DiffUtil.ItemCallback<T>)
    : FoundationPagedListAdapter<T>(callback, diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            NETWORK_STATE_LAYOUT -> {
                val binding = DataBindingUtil.inflate<ViewNetworkStateBinding>(
                        layoutInflater, NETWORK_STATE_LAYOUT, parent, false)
                NetworkStateViewHolder.create(binding, callback)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                        layoutInflater, viewType, parent, false)
                getBodyViewHolder(viewType, binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)

        if (itemViewType == getBodyLayout(position)) {
            (holder as? MyBaseViewHolder<T>)?.bind(getItem(position))
        } else if (itemViewType == NETWORK_STATE_LAYOUT) {
            (holder as NetworkStateViewHolder).bind(networkState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_STATE_LAYOUT
        } else {
            getBodyLayout(position)
        }
    }
}
