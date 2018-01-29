package io.github.anderscheow.library.services

import android.Manifest
import android.app.ProgressDialog
import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

import java.util.LinkedList
import java.util.Queue

import io.github.anderscheow.library.R
import io.github.anderscheow.library.kotlin.isConnectedToInternet

abstract class ServiceBoundAppCompatActivity<T : ApplicationService> : AppCompatActivity(), ServiceConnection {

    private var scheduler: ApplicationServiceScheduler? = null
    private var bound: Boolean = false
    private var callbackQueue: Queue<ApplicationServiceReadyCallback<T>>? = null

    var progressDialog: ProgressDialog? = null

    val isConnectedToInternet: Boolean
        @Synchronized get() {
            return applicationContext.isConnectedToInternet()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callbackQueue = LinkedList()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (bound)
            unbindService(this)
    }

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
        val binder = service as ApplicationService.ApplicationContextServiceBinder

        scheduler = binder.scheduler
        bound = true

        callbackQueue?.let {
            while (!it.isEmpty())
                scheduler?.scheduleForService(it.remove())
        }
    }

    override fun onServiceDisconnected(name: ComponentName) {
        scheduler = null
        bound = false
    }

    fun scheduleForService(callback: ApplicationServiceReadyCallback<T>) {
        val threadedCallback = UiThreadCallback(callback)

        if (!bound) {
            callbackQueue?.add(threadedCallback)
            startService(ApplicationService.newIntent(this))
        } else {
            scheduler?.scheduleForService(threadedCallback)
        }
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

    abstract fun startService()

    private inner class UiThreadCallback internal constructor(private val callback: ApplicationServiceReadyCallback<T>) : ApplicationServiceReadyCallback<T>, Runnable {
        private var service: T? = null

        override fun onServiceReady(service: T) {
            this.service = service
            runOnUiThread(this)
        }

        override fun run() {
            callback.onServiceReady(service)
        }
    }

}
