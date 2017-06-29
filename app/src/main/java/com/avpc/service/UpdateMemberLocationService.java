package com.avpc.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdateMemberLocationService extends Service {

    private static final String TAG = UpdateMemberLocationService.class.getSimpleName();

    public UpdateMemberLocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "Service Created " + Thread.currentThread().getName());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service Started " + Thread.currentThread().getName());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean stopService(Intent name) {
        Log.i(TAG, "Service Stopped " + Thread.currentThread().getName());
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {

        Log.i(TAG, "Destroying Service " + Thread.currentThread().getName());
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        Log.i(TAG, "Service Binding " + Thread.currentThread().getName());
        return null;
    }
}
