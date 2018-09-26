package io.github.anderscheow.library.base.live.view_model

import android.app.Application
import androidx.annotation.StringRes
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import io.github.anderscheow.library.base.live.util.ProgressDialogMessage
import io.github.anderscheow.library.base.live.util.ToastMessage
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseAndroidViewModel<in Args>(context: Application) : AndroidViewModel(context) {

    val isLoading = ObservableBoolean(false)

    val listSize = ObservableField<Long>()

    val toastMessage = ToastMessage()
    val progressDialogMessage = ProgressDialogMessage()

    protected val disposable = CompositeDisposable()

    abstract fun start(args: Args? = null)

    protected fun launch(job: () -> Disposable) {
        disposable.add(job())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    open fun startWithoutProgressDialog() {
    }

    open fun onRefresh() {
        start()
    }

    /**
     * Called to show or dismiss progress dialog
     *
     * @param message if >=0 show, if -1 dismiss
     */
    protected fun showProgressDialog(@StringRes message: Int = 0) {
        progressDialogMessage.postValue(message)
    }

    /**
     * Called to dismiss progress dialog
     */
    protected fun dismissProgressDialog() {
        progressDialogMessage.postValue(-1)
    }

    protected fun showToast(message: String) {
        toastMessage.postValue(message)
    }

    protected fun setIsLoading(value: Boolean) {
        isLoading.set(value)
        isLoading.notifyChange()
    }

    fun finishLoading(totalItems: Long = -1) {
        dismissProgressDialog()
        setIsLoading(false)

        if (totalItems != -1L) {
            listSize.set(totalItems)
            listSize.notifyChange()
        }
    }
}
