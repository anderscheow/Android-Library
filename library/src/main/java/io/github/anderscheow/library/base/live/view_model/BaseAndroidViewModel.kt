package io.github.anderscheow.library.base.live.view_model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.support.annotation.StringRes

import io.github.anderscheow.library.base.live.util.ProgressDialogMessage
import io.github.anderscheow.library.base.live.util.ToastMessage
import io.reactivex.disposables.CompositeDisposable

abstract class BaseAndroidViewModel<in T>(context: Application) : AndroidViewModel(context) {

    val isLoading = ObservableBoolean(false)

    val listSize = ObservableField<Long>()

    val toastMessage = ToastMessage()
    val progressDialogMessage = ProgressDialogMessage()

    protected val disposable = CompositeDisposable()

    abstract fun start(args: T?)

    abstract fun onRefresh()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    /**
     * Called to show or dismiss progress dialog
     *
     * @param message if >=0 show, if -1 dismiss
     */
    protected fun showProgressDialog(@StringRes message: Int) {
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

    fun start() {
        start(null)
    }

    fun setListSize(totalItems: Long) {
        showProgressDialog(-1)
        setIsLoading(false)

        if (totalItems != -1L) {
            listSize.set(totalItems)
            listSize.notifyChange()
        }
    }
}
