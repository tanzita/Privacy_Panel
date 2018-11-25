package com.tanzianondoproduction.privacypanel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Tanzi on 3/20/2016.
 */
public class ApplicationRestartSignalReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(AppContext.getContext(), "App will be started now", Toast.LENGTH_LONG).show();

        Intent service = new Intent(AppContext.getContext(), ApplicationRestartService.class);
        AppContext.getContext().startService(service);

    }
}
