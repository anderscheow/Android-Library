package io.github.anderscheow.library.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.View
import com.orhanobut.logger.Logger
import io.github.anderscheow.library.R
import io.github.anderscheow.library.constant.EventBusType
import io.github.anderscheow.library.kotlin.isConnectedToInternet
import io.github.anderscheow.library.kotlin.isNotThere
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.okButton
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.yesButton

@Suppress("UNUSED")
abstract class FoundationFragment : Fragment() {

    @LayoutRes
    abstract fun getResLayout(): Int

    abstract fun getEventBusType(): EventBusType?

    abstract fun init()

    abstract var initializer: () -> Unit

    open fun requiredButterknife(): Boolean {
        return false
    }

    var isDestroy = false
        private set

    @Suppress("DEPRECATION")
    var progressDialog: ProgressDialog? = null
        private set

    override fun onAttach(context: Context?) {
        Logger.v("Fragment ATTACHED")
        super.onAttach(context)
        if (EventBusType.isOnAttach(getEventBusType())) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
        initializer.invoke()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.v("Fragment CREATED")
        super.onCreate(savedInstanceState)
        if (EventBusType.isOnCreate(getEventBusType())) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            init()
        }
    }

    override fun onActivityCreated(savedInstaceState: Bundle?) {
        Logger.v("Fragment ACTIVITY CREATED")
        super.onActivityCreated(savedInstaceState)
    }

    override fun onStart() {
        Logger.v("Fragment STARTED")
        super.onStart()
        if (EventBusType.isOnStart(getEventBusType())) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
    }

    override fun onResume() {
        Logger.v("Fragment RESUMED")
        super.onResume()
        if (EventBusType.isOnResume(getEventBusType())) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
    }

    override fun onPause() {
        Logger.v("Fragment PAUSED")
        if (EventBusType.isOnResume(getEventBusType())) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onPause()
    }

    override fun onStop() {
        Logger.v("Fragment STOPPED")
        if (EventBusType.isOnStart(getEventBusType())) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onStop()
    }

    override fun onDestroy() {
        Logger.v("Fragment DESTROYED")
        isDestroy = true
        if (EventBusType.isOnCreate(getEventBusType())) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onDestroy()
    }

    override fun onDetach() {
        Logger.v("Fragment DETACHED")
        if (EventBusType.isOnAttach(getEventBusType())) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onDetach()
    }

    fun showProgressDialog(message: Int) {
        if (isNotThere()) return

        if (progressDialog == null) {
            progressDialog = indeterminateProgressDialog(R.string.prompt_please_wait) {
                setCanceledOnTouchOutside(false)
                setCancelable(false)
            }
        }

        if (message != 0) {
            progressDialog?.setMessage(getString(message))
        }
        progressDialog?.show()
    }

    fun dismissProgressDialog() {
        if (isNotThere()) return

        progressDialog?.dismiss()
    }

    open fun toastInternetRequired() {
        toast(R.string.prompt_internet_required)
    }

    @Suppress("UNUSED")
    fun isConnectedToInternet(action: () -> Unit) {
        context?.let {
            if (it.isConnectedToInternet()) {
                action.invoke()
            } else {
                toastInternetRequired()
            }
        }
    }

    @Suppress("UNUSED")
    fun checkLoadingIndicator(active: Boolean, message: Int) {
        if (active) {
            showProgressDialog(message)
        } else {
            dismissProgressDialog()
        }
    }

    fun showOkAlertDialog(message: CharSequence, title: CharSequence? = null,
                          buttonText: Int = 0, cancellable: Boolean = false, action: () -> Unit) {
        if (isNotThere()) return

        activity?.alert(message, title) {
            isCancelable = cancellable

            if (buttonText == 0) {
                okButton {
                    action.invoke()
                    it.dismiss()
                }
            } else {
                positiveButton(buttonText) {
                    action.invoke()
                    it.dismiss()
                }
            }
        }?.show()
    }

    fun showYesAlertDialog(message: CharSequence, title: CharSequence? = null,
                           buttonText: Int = 0, cancellable: Boolean = false, action: () -> Unit) {
        if (isNotThere()) return

        activity?.alert(message, title) {
            isCancelable = cancellable

            if (buttonText == 0) {
                yesButton {
                    action.invoke()
                    it.dismiss()
                }
            } else {
                positiveButton(buttonText) {
                    action.invoke()
                    it.dismiss()
                }
            }
        }?.show()
    }

    fun showNoAlertDialog(message: CharSequence, title: CharSequence? = null,
                          buttonText: Int = 0, cancellable: Boolean = false, action: () -> Unit) {
        if (isNotThere()) return

        activity?.alert(message, title) {
            isCancelable = cancellable

            if (buttonText == 0) {
                noButton {
                    action.invoke()
                    it.dismiss()
                }
            } else {
                negativeButton(buttonText) {
                    action.invoke()
                    it.dismiss()
                }
            }
        }?.show()
    }

    fun showYesNoAlertDialog(message: CharSequence, title: CharSequence? = null,
                             yesButtonText: Int = 0, noButtonText: Int = 0,
                             cancellable: Boolean = false,
                             yesAction: () -> Unit, noAction: () -> Unit) {
        if (isNotThere()) return

        activity?.alert(message, title) {
            isCancelable = cancellable

            if (yesButtonText == 0) {
                yesButton {
                    yesAction.invoke()
                    it.dismiss()
                }
            } else {
                positiveButton(yesButtonText) {
                    yesAction.invoke()
                    it.dismiss()
                }
            }

            if (noButtonText == 0) {
                noButton {
                    noAction.invoke()
                    it.dismiss()
                }
            } else {
                negativeButton(noButtonText) {
                    noAction.invoke()
                    it.dismiss()
                }
            }
        }?.show()
    }
}
