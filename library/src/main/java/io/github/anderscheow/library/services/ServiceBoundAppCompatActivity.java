package io.github.anderscheow.library.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;

import java.util.LinkedList;
import java.util.Queue;

import io.github.anderscheow.library.base.BaseAppCompatActivity;

public abstract class ServiceBoundAppCompatActivity<T extends ApplicationService> extends BaseAppCompatActivity
        implements ServiceConnection {

    private ApplicationServiceScheduler scheduler;
    private boolean bound;
    private Queue<ApplicationServiceReadyCallback<T>> callbackQueue;

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
