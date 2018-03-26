package io.github.anderscheow.library.adapters.base

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anderscheow.library.R
import io.github.anderscheow.library.adapters.view_holder.MyBaseViewHolder
import io.github.anderscheow.library.adapters.view_holder.NetworkStateViewHolder
import io.github.anderscheow.library.constant.NetworkState
import io.github.anderscheow.library.databinding.ViewNetworkStateBinding

abstract class BasePagedListWithoutNetworkStateAdapter<T>(
        val context: Context,
        diffCallback: DiffUtil.ItemCallback<T>) : PagedListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    @get:LayoutRes
    protected abstract val bodyLayout: Int

    protected abstract fun getBodyViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            bodyLayout -> {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                        layoutInflater, bodyLayout, parent, false)
                getBodyViewHolder(binding)
            }
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)

        if (itemViewType == bodyLayout) {
            (holder as MyBaseViewHolder<T>).bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return bodyLayout
    }
}
