package io.github.anderscheow.library.mvp

import androidx.annotation.UiThread

interface BasePresenter<V : BaseView> {
    @UiThread
    fun onAttachView(view: V)

    @UiThread
    fun onDetachView()

    @UiThread
    fun onStart()

    @UiThread
    fun onResume()

    @UiThread
    fun onPause()

    @UiThread
    fun onDestroy()
}

interface BaseView {

    fun toastMessage(message: CharSequence)

    fun toastMessage(message: Int = 0)

    fun setLoadingIndicator(active: Boolean, message: Int = 0)

    fun showErrorAlertDialog(message: CharSequence, title: CharSequence? = null, action: () -> Unit)

    fun showErrorAlertDialog(message: CharSequence, title: CharSequence? = null) {
        showErrorAlertDialog(message, title) {}
    }
}
