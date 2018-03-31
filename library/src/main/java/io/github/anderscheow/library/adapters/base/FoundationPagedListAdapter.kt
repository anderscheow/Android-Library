package io.github.anderscheow.library.adapters.base

import android.arch.paging.PagedListAdapter
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import io.github.anderscheow.library.R
import io.github.anderscheow.library.constant.NetworkState

abstract class FoundationPagedListAdapter<T>(
        private val callback: (() -> Unit)?,
        diffCallback: DiffUtil.ItemCallback<T>)
    : PagedListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    @LayoutRes
    protected abstract fun getBodyLayout(position: Int): Int

    protected abstract fun getBodyViewHolder(viewType: Int, binding: ViewDataBinding): RecyclerView.ViewHolder

    var networkState: NetworkState? = null
        set(value) {
            val previousState = field
            val hadExtraRow = hasExtraRow()
            field = value
            val hasExtraRow = hasExtraRow()
            if (hadExtraRow != hasExtraRow) {
                if (hadExtraRow) {
                    notifyItemRemoved(super.getItemCount())
                } else {
                    notifyItemInserted(super.getItemCount())
                }
            } else if (hasExtraRow && previousState != value) {
                notifyItemChanged(itemCount - 1)
            }
        }

    protected fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    companion object {

        val NETWORK_STATE_LAYOUT = R.layout.view_network_state
    }
}
