package io.github.anderscheow.library.viewModel.util

import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class AlertDialogMessage : SingleLiveEvent<String>() {

    fun observe(owner: LifecycleOwner, observer: AlertDialogObserver) {
        super.observe(owner, Observer { t ->
            if (t == null) {
                return@Observer
            }

            observer.onNewMessage(t)
        })
    }

    interface AlertDialogObserver {
        /**
         * Called when there is a new message to be shown.
         *
         * @param message The new message, non-null.
         */
        fun onNewMessage(message: String)
    }
}
