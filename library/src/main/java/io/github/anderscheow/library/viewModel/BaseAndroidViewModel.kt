package io.github.anderscheow.library.viewModel

import android.app.Application
import androidx.annotation.StringRes
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import io.github.anderscheow.library.CustomApplication
import io.github.anderscheow.library.viewModel.util.AlertDialogData
import io.github.anderscheow.library.viewModel.util.AlertDialogMessage
import io.github.anderscheow.library.viewModel.util.ProgressDialogMessage
import io.github.anderscheow.library.viewModel.util.ToastMessage
import org.kodein.di.DIAware

abstract class BaseAndroidViewModel<in Args>(application: Application)
    : AndroidViewModel(application), DIAware {

    override val di by (application as CustomApplication).di

    val isLoading = ObservableBoolean(false)

    val listSize = ObservableField<Long>()

    val toastMessage = ToastMessage()
    val progressDialogMessage = ProgressDialogMessage()
    val alertDialogMessage = AlertDialogMessage()

    abstract fun start(args: Args? = null)

    open fun startWithoutProgressDialog() {

    }

    open fun onRefresh() {
        start()
    }

    /**
     * Called to show or dismiss progress dialog
     *
     * @param resId if >=0 show, if -1 dismiss
     */
    protected fun showProgressDialog(@StringRes resId: Int = 0) {
        progressDialogMessage.postValue(resId)
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

    protected fun showAlertDialog(data: AlertDialogData) {
        alertDialogMessage.postValue(data)
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
