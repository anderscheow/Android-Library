package io.github.anderscheow.library.appCompat.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.kaopiz.kprogresshud.KProgressHUD
import com.orhanobut.logger.Logger
import io.github.anderscheow.library.R
import io.github.anderscheow.library.constant.EventBusType
import io.github.anderscheow.library.kotlinExt.isConnectedToInternet
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.toast

abstract class FoundationAppCompatActivity : AppCompatActivity() {

    @LayoutRes
    abstract fun getResLayout(): Int

    open fun getToolbar(): Toolbar? = null

    open fun getEventBusType(): EventBusType? = null

    open fun requiredDisplayHomeAsUp(): Boolean = false

    open fun initBeforeSuperOnCreate() {
    }

    open fun init() {
    }

    var progressDialog: KProgressHUD? = null
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

    protected fun canAccessCamera(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.CAMERA)
        } else {
            true
        }
    }

    protected fun canAccessStorage(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            true
        }
    }

    protected fun canAccessLocation(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) &&
                    PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            true
        }
    }

    protected fun shouldShowLocationPermissionRationalDialog(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) &&
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            true
        }
    }

    //region Progress Dialog
    fun showProgressDialog(message: Int = 0, isFullScreen: Boolean = false) {
        if (isFinishing) return

        if (progressDialog == null) {
            progressDialog = KProgressHUD.create(this).apply {
                this.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                this.setCancellable(false)
                this.setDimAmount(0.3f)
            }
        }
        progressDialog?.setFullscreen(isFullScreen)

        if (message != 0) {
            progressDialog?.setLabel(getString(message))
        }
        progressDialog?.show()
    }

    fun dismissProgressDialog() {
        if (isFinishing) return

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

    fun checkLoadingIndicator(active: Boolean, message: Int, isFullScreen: Boolean = false) {
        if (active) {
            showProgressDialog(message, isFullScreen)
        } else {
            dismissProgressDialog()
        }
    }
    //endregion

    //region Alert Dialog
    inline fun showYesAlertDialog(message: CharSequence,
                                  title: CharSequence? = null,
                                  buttonText: Int,
                                  cancellable: Boolean = false,
                                  crossinline action: () -> Unit) {
        if (isFinishing) return

        AlertDialog.Builder(this)
                .setMessage(message)
                .setTitle(title)
                .setCancelable(cancellable)
                .setPositiveButton(buttonText) { dialog, _ ->
                    dialog.dismiss()
                    action()
                }
                .show()
    }

    inline fun showNoAlertDialog(message: CharSequence,
                                 title: CharSequence? = null,
                                 buttonText: Int,
                                 cancellable: Boolean = false,
                                 crossinline action: () -> Unit) {
        if (isFinishing) return

        AlertDialog.Builder(this)
                .setMessage(message)
                .setTitle(title)
                .setCancelable(cancellable)
                .setNegativeButton(buttonText) { dialog, _ ->
                    dialog.dismiss()
                    action()
                }
                .show()
    }

    inline fun showYesNoAlertDialog(message: CharSequence,
                                    title: CharSequence? = null,
                                    yesButtonText: Int,
                                    noButtonText: Int,
                                    cancellable: Boolean = false,
                                    crossinline yesAction: () -> Unit,
                                    crossinline noAction: () -> Unit) {
        if (isFinishing) return

        AlertDialog.Builder(this)
                .setMessage(message)
                .setTitle(title)
                .setCancelable(cancellable)
                .setPositiveButton(yesButtonText) { dialog, _ ->
                    dialog.dismiss()
                    yesAction()
                }
                .setNeutralButton(noButtonText) { dialog, _ ->
                    dialog.dismiss()
                    noAction()
                }
                .show()
    }
    //endregion
}
