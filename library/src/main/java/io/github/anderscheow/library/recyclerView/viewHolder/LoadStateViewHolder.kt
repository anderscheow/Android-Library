package io.github.anderscheow.library.recyclerView.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import io.github.anderscheow.library.R
import io.github.anderscheow.library.databinding.ViewNetworkStateBinding

class LoadStateViewHolder(
        parent: ViewGroup,
        retry: () -> Unit
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.view_network_state, parent, false)) {

    private val binding = ViewNetworkStateBinding.bind(itemView)
    private val progressBar: ProgressBar = binding.progressBar
    private val errorMsg: TextView = binding.textViewErrorMsg
    private val retry: Button = binding.buttonRetry.also {
        it.setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            errorMsg.text = loadState.error.localizedMessage
        }

        progressBar.isVisible = loadState is LoadState.Loading
        retry.isVisible = loadState is LoadState.Error
        errorMsg.isVisible = loadState is LoadState.Error
    }
}