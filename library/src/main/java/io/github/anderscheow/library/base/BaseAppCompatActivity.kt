package io.github.anderscheow.library.base

import android.Manifest
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil.setContentView
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Toast

import io.github.anderscheow.library.constant.EventBusType
import com.orhanobut.logger.Logger

import org.greenrobot.eventbus.EventBus

import butterknife.ButterKnife
import io.github.anderscheow.library.R
import io.github.anderscheow.library.services.ApplicationService
import io.github.anderscheow.library.services.ServiceBoundAppCompatActivity

abstract class BaseAppCompatActivity : AppCompatActivity() {

    @get:LayoutRes
    abstract val resLayout: Int

    abstract val toolbar: Toolbar?

    abstract val eventBusType: EventBusType?

    abstract fun requiredDisplayHomeAsUp(): Boolean

    abstract fun requiredDataBinding(): Boolean

    abstract fun init()

    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.v("Activity CREATED")
        super.onCreate(savedInstanceState)
        if (EventBusType.isOnCreate(eventBusType)) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }

        if (!requiredDataBinding()) {
            setContentView(resLayout)
            ButterKnife.bind(this)

            toolbar?.let {
                setSupportActionBar(toolbar)

                supportActionBar?.setDisplayHomeAsUpEnabled(requiredDisplayHomeAsUp())
            }
        }

        init()
    }

    override fun onStart() {
        Logger.v("Activity STARTED")
        super.onStart()
        if (EventBusType.isOnStart(eventBusType)) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
    }

    override fun onResume() {
        Logger.v("Activity RESUMED")
        super.onResume()
        if (EventBusType.isOnResume(eventBusType)) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
    }

    override fun onPause() {
        Logger.v("Activity PAUSED")
        if (EventBusType.isOnResume(eventBusType)) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onPause()
    }

    override fun onStop() {
        Logger.v("Activity STOPPED")
        if (EventBusType.isOnStart(eventBusType)) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onStop()
    }

    override fun onRestart() {
        Logger.v("Activity RESTARTED")
        super.onRestart()
    }

    override fun onDestroy() {
        Logger.v("Activity DESTROYED")
        if (EventBusType.isOnCreate(eventBusType)) {
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

    fun showProgressDialog(message: Int) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this)
        }

        progressDialog?.setMessage(if (message == 0) getString(R.string.prompt_please_wait) else getString(message))
        progressDialog?.setCanceledOnTouchOutside(false)
        progressDialog?.setCancelable(false)
        progressDialog?.isIndeterminate = true
        progressDialog?.show()
    }

    fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

    fun toast(resourceID: Int) {
        Toast.makeText(this, resourceID, Toast.LENGTH_SHORT).show()
    }

    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun toastInternetRequired() {
        Toast.makeText(this, getString(R.string.prompt_internet_required), Toast.LENGTH_LONG).show()
    }
}
