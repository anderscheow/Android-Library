package io.github.anderscheow.library.recyclerView.adapters.paging

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.anderscheow.library.R
import io.github.anderscheow.library.constant.NetworkState

abstract class FoundationPagedListAdapter<T>(
        diffCallback: DiffUtil.ItemCallback<T>,
        private val callback: (() -> Unit)?)
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
