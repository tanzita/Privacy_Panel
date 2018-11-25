package com.tanzianondoproduction.privacypanel;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Tanzi on 3/20/2016.
 */
public class ApplicationRestartService extends Service {

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {

        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.tanzianondoproduction.privacypanel");
        startActivity(LaunchIntent);
    }

    @Override
    public void onDestroy() {

    }
}
