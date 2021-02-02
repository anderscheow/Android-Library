package io.github.anderscheow.library.recyclerView.viewHolder

import android.view.View
import androidx.annotation.StringRes
import io.github.anderscheow.library.constant.NetworkState
import io.github.anderscheow.library.databinding.ViewNetworkStateBinding
import kotlinx.android.extensions.LayoutContainer

class NetworkStateViewHolder(private val binding: ViewNetworkStateBinding,
                             private val callback: () -> Unit,
                             @StringRes
                             private val errorMessage: Int? = null) : BaseViewHolder<NetworkState>(binding), LayoutContainer {

    override val containerView: View
        get() = binding.root

    override fun extraBinding(item: NetworkState) {
        binding.buttonRetry.setOnClickListener {
            callback.invoke()
        }

        errorMessage?.let {
            binding.textViewErrorMsg.setText(errorMessage)
        }
    }

    override fun onClick(view: View, item: NetworkState?) {

    }

    companion object {

        fun create(binding: ViewNetworkStateBinding,
                   callback: () -> Unit): NetworkStateViewHolder {
            return NetworkStateViewHolder(binding, callback)
        }

        fun create(binding: ViewNetworkStateBinding,
                   callback: () -> Unit,
                   @StringRes errorMessage: Int?): NetworkStateViewHolder {
            return NetworkStateViewHolder(binding, callback, errorMessage)
        }
    }
}
