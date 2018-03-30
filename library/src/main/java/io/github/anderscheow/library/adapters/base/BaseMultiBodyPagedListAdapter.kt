package io.github.anderscheow.library.adapters.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anderscheow.library.adapters.view_holder.MyBaseViewHolder
import io.github.anderscheow.library.adapters.view_holder.NetworkStateViewHolder
import io.github.anderscheow.library.databinding.ViewNetworkStateBinding
import io.github.anderscheow.library.utils.BodyViewTypeHolder

abstract class BaseMultiBodyPagedListAdapter<T>(
        context: Context,
        private val callback: () -> Unit,
        diffCallback: DiffUtil.ItemCallback<T>)
    : FoundationPagedListAdapter<T>(context, callback, diffCallback) {

    protected abstract val bodyViewTypeIdentifier: String

    @get:LayoutRes
    protected abstract val defaultLayout: Int

    protected abstract fun getDefaultViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder

    protected abstract val bodyLayouts: List<BodyViewTypeHolder>

    override val bodyLayout: Int
        get() = bodyLayouts.find {
                    it.identifier == bodyViewTypeIdentifier
                }?.layout ?: kotlin.run {
                    defaultLayout
                }

    override fun getBodyViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return bodyLayouts.find {
            it.identifier == bodyViewTypeIdentifier
        }?.viewHolder ?: kotlin.run {
            getDefaultViewHolder(binding)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            bodyLayout -> {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                        layoutInflater, bodyLayout, parent, false)
                getBodyViewHolder(binding)
            }
            NETWORK_STATE_LAYOUT -> {
                val binding = DataBindingUtil.inflate<ViewNetworkStateBinding>(
                        layoutInflater, NETWORK_STATE_LAYOUT, parent, false)
                NetworkStateViewHolder.create(binding, callback)
            }
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)

        if (itemViewType == bodyLayout) {
            (holder as MyBaseViewHolder<T>).bind(getItem(position))
        } else if (itemViewType == NETWORK_STATE_LAYOUT) {
            (holder as NetworkStateViewHolder).bind(networkState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_STATE_LAYOUT
        } else {
            bodyLayout
        }
    }
}
