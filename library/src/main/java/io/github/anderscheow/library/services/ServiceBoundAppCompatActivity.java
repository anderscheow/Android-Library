package io.github.anderscheow.library.services;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Queue;

import io.github.anderscheow.library.R;

public abstract class ServiceBoundAppCompatActivity<T extends ApplicationService> extends AppCompatActivity
        implements ServiceConnection {

    private ApplicationServiceScheduler scheduler;
    private boolean bound;
    private Queue<ApplicationServiceReadyCallback<T>> callbackQueue;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackQueue = new LinkedList<>();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (bound)
            unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        ApplicationService.ApplicationContextServiceBinder binder =
                (ApplicationService.ApplicationContextServiceBinder) service;

        scheduler = binder.getScheduler();
        bound = true;

        while (!callbackQueue.isEmpty())
            scheduler.scheduleForService(callbackQueue.remove());
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        scheduler = null;
        bound = false;
    }

    public void scheduleForService(ApplicationServiceReadyCallback<T> callback) {
        ApplicationServiceReadyCallback<T> threadedCallback = new UiThreadCallback(callback);

        if (!bound) {
            callbackQueue.add(threadedCallback);
            startService(ApplicationService.newIntent(this));
        } else {
            scheduler.scheduleForService(threadedCallback);
        }
    }

    public synchronized boolean isConnectedToInternet() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm != null ? cm.getActiveNetworkInfo() : null;
        return netInfo != null && netInfo.isConnectedOrConnecting() && !netInfo.isRoaming();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected boolean canAccessCamera() {
        return PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.CAMERA);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected boolean canAccessStorage() {
        return PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected boolean canAccessLocation() {
        return PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) &&
                PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected boolean shouldShowLocationPermissionRationalDialog() {
        return shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) &&
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public void showProgressDialog(int message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }

        progressDialog.setMessage(message == 0 ? getString(R.string.prompt_please_wait) : getString(message));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void toast(int resourceID) {
        Toast.makeText(this, resourceID, Toast.LENGTH_SHORT).show();
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void toastInternetRequired() {
        Toast.makeText(this, getString(R.string.prompt_internet_required), Toast.LENGTH_LONG).show();
    }

    public abstract void startService();

    private class UiThreadCallback implements ApplicationServiceReadyCallback<T>, Runnable {

        private ApplicationServiceReadyCallback<T> callback;
        private T service;

        UiThreadCallback(ApplicationServiceReadyCallback<T> callback) {
            this.callback = callback;
        }

        @Override
        public void onServiceReady(T service) {
            this.service = service;
            runOnUiThread(this);
        }

        @Override
        public void run() {
            callback.onServiceReady(service);
        }
    }

}
