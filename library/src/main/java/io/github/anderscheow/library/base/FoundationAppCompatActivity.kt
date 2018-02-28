package io.github.anderscheow.library.base

import android.Manifest
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.orhanobut.logger.Logger
import io.github.anderscheow.library.R
import io.github.anderscheow.library.constant.EventBusType
import io.github.anderscheow.library.kotlin.isConnectedToInternet
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.*

abstract class FoundationAppCompatActivity : AppCompatActivity() {

    @LayoutRes
    abstract fun getResLayout(): Int

    abstract fun getToolbar(): Toolbar?

    abstract fun getEventBusType(): EventBusType?

    abstract fun requiredDisplayHomeAsUp(): Boolean

    abstract fun init()

    abstract var initializer: () -> Unit

    var progressDialog: ProgressDialog? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.v("Activity CREATED")
        super.onCreate(savedInstanceState)
        if (EventBusType.isOnCreate(getEventBusType())) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
    }

    override fun onStart() {
        Logger.v("Activity STARTED")
        super.onStart()
        if (EventBusType.isOnStart(getEventBusType())) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
    }

    override fun onResume() {
        Logger.v("Activity RESUMED")
        super.onResume()
        if (EventBusType.isOnResume(getEventBusType())) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
    }

    override fun onPause() {
        Logger.v("Activity PAUSED")
        if (EventBusType.isOnResume(getEventBusType())) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onPause()
    }

    override fun onStop() {
        Logger.v("Activity STOPPED")
        if (EventBusType.isOnStart(getEventBusType())) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onStop()
    }

    override fun onDestroy() {
        Logger.v("Activity DESTROYED")
        if (EventBusType.isOnCreate(getEventBusType())) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onDestroy()
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected fun canAccessCamera(): Boolean {
        return PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.CAMERA)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected fun canAccessStorage(): Boolean {
        return PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected fun canAccessLocation(): Boolean {
        return PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) && PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected fun shouldShowLocationPermissionRationalDialog(): Boolean {
        return shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) && shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    //region Progress Dialog
    fun showProgressDialog(message: Int) {
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
        progressDialog?.dismiss()
    }

    open fun toastInternetRequired() {
        toast(R.string.prompt_internet_required)
    }

    fun isConnectedToInternet(action: () -> Unit) {
        if (isConnectedToInternet()) {
            action.invoke()
        } else {
            toastInternetRequired()
        }
    }

    fun checkLoadingIndicator(active: Boolean, message: Int) {
        if (active) {
            showProgressDialog(message)
        } else {
            dismissProgressDialog()
        }
    }
    //endregion

    //region Alert Dialog
    fun showOkAlertDialog(action: () -> Unit, message: String, buttonText: Int = 0) {
        alert(message) {
            isCancelable = false

            if (buttonText == 0) {
                okButton {
                    action.invoke()
                    it.dismiss()
                }
            } else {
                positiveButton(buttonText, {
                    action.invoke()
                    it.dismiss()
                })
            }
        }.show()
    }

    fun showYesAlertDialog(action: () -> Unit, message: String, buttonText: Int = 0) {
        alert(message) {
            isCancelable = false

            if (buttonText == 0) {
                yesButton {
                    action.invoke()
                    it.dismiss()
                }
            } else {
                positiveButton(buttonText, {
                    action.invoke()
                    it.dismiss()
                })
            }
        }.show()
    }

    fun showNoAlertDialog(action: () -> Unit, message: String, buttonText: Int = 0) {
        alert(message) {
            isCancelable = false

            if (buttonText == 0) {
                noButton {
                    action.invoke()
                    it.dismiss()
                }
            } else {
                negativeButton(buttonText, {
                    action.invoke()
                    it.dismiss()
                })
            }
        }.show()
    }

    fun showYesNoAlertDialog(yesAction: () -> Unit, noAction: () -> Unit, message: String, yesButtonText: Int = 0, noButtonText: Int = 0) {
        alert(message) {
            isCancelable = false

            if (yesButtonText == 0) {
                yesButton {
                    yesAction.invoke()
                    it.dismiss()
                }
            } else {
                positiveButton(yesButtonText, {
                    yesAction.invoke()
                    it.dismiss()
                })
            }

            if (noButtonText == 0) {
                noButton {
                    noAction.invoke()
                    it.dismiss()
                }
            } else {
                negativeButton(noButtonText, {
                    noAction.invoke()
                    it.dismiss()
                })
            }
        }.show()
    }
    //endregion
}
