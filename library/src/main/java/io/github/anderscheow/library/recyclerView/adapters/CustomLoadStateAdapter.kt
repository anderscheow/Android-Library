package io.github.anderscheow.library.recyclerView.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import io.github.anderscheow.library.recyclerView.viewHolder.LoadStateViewHolder

class CustomLoadStateAdapter(
        private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(
            parent: ViewGroup,
            loadState: LoadState
    ) = LoadStateViewHolder(parent, retry)

    override fun onBindViewHolder(
            holder: LoadStateViewHolder,
            loadState: LoadState
    ) = holder.bind(loadState)
}