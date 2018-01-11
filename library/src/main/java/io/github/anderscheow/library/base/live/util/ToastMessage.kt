package io.github.anderscheow.library.base.live.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer

class ToastMessage : SingleLiveEvent<String>() {

    fun observe(owner: LifecycleOwner, observer: ToastObserver) {
        super.observe(owner, Observer { s ->
            if (s == null) {
                return@Observer
            }
            observer.onNewMessage(s)
        })
    }

    interface ToastObserver {
        /**
         * Called when there is a new message to be shown.
         *
         * @param message The new message, non-null.
         */
        fun onNewMessage(message: String?)
    }
}
