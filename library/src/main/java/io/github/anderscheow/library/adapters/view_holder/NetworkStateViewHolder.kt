package io.github.anderscheow.library.adapters.view_holder

import android.view.View
import androidx.annotation.StringRes
import io.github.anderscheow.library.constant.NetworkState
import io.github.anderscheow.library.databinding.ViewNetworkStateBinding
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_network_state.*

class NetworkStateViewHolder(private val binding: ViewNetworkStateBinding,
                             private val callback: () -> Unit,
                             @StringRes
                             private val errorMessage: Int? = null) : MyBaseViewHolder<NetworkState>(binding), LayoutContainer {

    override val containerView: View?
        get() = binding.root

    override fun extraBinding(item: NetworkState) {
        button_retry.setOnClickListener {
            callback.invoke()
        }

        errorMessage?.let {
            text_view_error_msg.setText(errorMessage)
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
            return NetworkStateViewHolder(binding, callback)
        }
    }
}
