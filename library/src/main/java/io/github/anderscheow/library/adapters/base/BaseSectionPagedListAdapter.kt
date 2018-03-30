package io.github.anderscheow.library.adapters.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.anderscheow.library.adapters.view_holder.MyBaseViewHolder
import io.github.anderscheow.library.adapters.view_holder.NetworkStateViewHolder
import io.github.anderscheow.library.databinding.ViewNetworkStateBinding
import io.github.anderscheow.library.utils.SectionGroup

abstract class BaseSectionPagedListAdapter<Key, Value>(
        private val callback: () -> Unit)
    : FoundationPagedListAdapter<SectionGroup>(callback, SectionGroup.DIFF_CALLBACK) {

    @get:LayoutRes
    protected abstract val headerLayout: Int

    protected abstract fun getHeaderViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            headerLayout -> {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                        layoutInflater, headerLayout, parent, false)
                getHeaderViewHolder(binding)
            }
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

        when (itemViewType) {
            headerLayout -> (holder as MyBaseViewHolder<Key>).bind(getItem(position)?.section as Key)

            bodyLayout -> (holder as MyBaseViewHolder<Value>).bind(getItem(position)?.row as Value)

            NETWORK_STATE_LAYOUT -> (holder as NetworkStateViewHolder).bind(networkState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_STATE_LAYOUT
        } else {
            val item = getItem(position)
            if (item != null) {
                if (item.isRow) {
                    bodyLayout
                } else {
                    headerLayout
                }
            } else {
                headerLayout
            }
        }
    }
}
