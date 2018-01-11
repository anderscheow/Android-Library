package io.github.anderscheow.library.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import java.util.concurrent.Semaphore;

public abstract class ApplicationService extends Service
        implements ApplicationServiceScheduler {

    private final ApplicationContextServiceBinder serviceBinder;
    private final Semaphore semaphore;

    public boolean isSynchronized;
    private int runningTasks;

    public ApplicationService() {
        serviceBinder = new ApplicationContextServiceBinder();
        semaphore = new Semaphore(0);

        runningTasks = 0;
        isSynchronized = false;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ApplicationService.class);
    }

    @Override
    public IBinder onBind(Intent intent) {
        loadResources();
        return serviceBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        loadResources();
        return super.onStartCommand(intent, flags, startId);
    }

    public synchronized void loadResources() {

    }

    public synchronized void logout() {
        this.isSynchronized = false;
    }

    public void addWork() {
        runningTasks++;
    }

    public synchronized void workDone() {
        semaphore.release();
    }

    public void requestWorkerTo(Runnable runnable) {
        new WorkerAsyncTask().execute(runnable);
    }

    @Override
    public void scheduleForService(ApplicationServiceReadyCallback callback) {
        new ServiceReadyAsyncTask().execute(callback);
    }

    class ApplicationContextServiceBinder extends Binder {

        ApplicationServiceScheduler getScheduler() {
            return ApplicationService.this;
        }

    }

    private class ServiceReadyAsyncTask extends AsyncTask<ApplicationServiceReadyCallback, Void, Void> {

        @Override
        protected Void doInBackground(ApplicationServiceReadyCallback... params) {
            try {
                semaphore.acquire(runningTasks);
                runningTasks = 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (ApplicationServiceReadyCallback handler : params)
                handler.onServiceReady(ApplicationService.this);

            return null;
        }
    }

    private class WorkerAsyncTask extends AsyncTask<Runnable, Void, Void> {

        @Override
        protected Void doInBackground(Runnable... params) {
            for (Runnable runnable : params)
                runnable.run();

            return null;
        }
    }
}
