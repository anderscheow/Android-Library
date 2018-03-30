package io.github.anderscheow.library.adapters.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anderscheow.library.adapters.view_holder.MyBaseViewHolder

abstract class BasePagedListWithoutNetworkStateAdapter<T>(
        context: Context,
        diffCallback: DiffUtil.ItemCallback<T>)
    : FoundationPagedListAdapter<T>(context,null, diffCallback) {

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
