package io.github.anderscheow.library.mvp

interface MvpView {

    fun toastMessage(message: CharSequence)

    fun toastMessage(message: Int = 0)

    fun setLoadingIndicator(active: Boolean, message: Int = 0)

    fun showErrorAlertDialog(message: CharSequence, title: CharSequence? = null, action: () -> Unit)

    fun showErrorAlertDialog(message: CharSequence, title: CharSequence? = null) {
        showErrorAlertDialog(message, title) {}
    }
}
