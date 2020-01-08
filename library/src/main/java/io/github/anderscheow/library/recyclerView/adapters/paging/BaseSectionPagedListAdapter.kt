package io.github.anderscheow.library.recyclerView.adapters.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import io.github.anderscheow.library.databinding.ViewNetworkStateBinding
import io.github.anderscheow.library.recyclerView.util.SectionGroup
import io.github.anderscheow.library.recyclerView.viewHolder.BaseViewHolder
import io.github.anderscheow.library.recyclerView.viewHolder.NetworkStateViewHolder

abstract class BaseSectionPagedListAdapter<S, R>(
        private val callback: () -> Unit)
    : FoundationPagedListAdapter<SectionGroup<S, R>>(SectionGroup.getDiffCallback(), callback) {

    @get:LayoutRes
    protected abstract val headerLayout: Int

    protected abstract fun getHeaderViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            NETWORK_STATE_LAYOUT -> {
                val binding = DataBindingUtil.inflate<ViewNetworkStateBinding>(
                        layoutInflater, NETWORK_STATE_LAYOUT, parent, false)
                NetworkStateViewHolder.create(binding, callback)
            }

            headerLayout -> {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                        layoutInflater, headerLayout, parent, false)
                getHeaderViewHolder(binding)
            }

            else -> {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                        layoutInflater, viewType, parent, false)
                getBodyViewHolder(viewType, binding)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            NETWORK_STATE_LAYOUT -> (holder as NetworkStateViewHolder).bind(networkState)

            headerLayout -> {
                if (holder !is BaseViewHolder<*>) {
                    throw IllegalStateException("Must inherit BaseViewHolder for body view holder")
                }
                (holder as BaseViewHolder<S>).bind(getItem(position)?.section)
            }

            else -> {
                if (holder !is BaseViewHolder<*>) {
                    throw IllegalStateException("Must inherit BaseViewHolder for body view holder")
                }
                (holder as BaseViewHolder<R>).bind(getItem(position)?.row)
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_STATE_LAYOUT
        } else {
            val item = getItem(position)
            if (item != null && item.isRow) {
                getBodyLayout(position)
            } else {
                headerLayout
            }
        }
    }
}
