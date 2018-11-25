﻿package com.tanzianondoproduction.privacypanel;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Tanzi on 3/20/2016.
 */
public class LocationPermissionWarningServiceReceiver extends BroadcastReceiver {

    boolean GPSProtectionEnable = false;
    final Context context = AppContext.getContext();
    final DatabaseHandler db = new DatabaseHandler(context);
    StringBuilder sb = new StringBuilder();

    @Override
    public void onReceive(Context context, Intent intent) {

        //detecting foreground apps
        new AsyncTask<Void, Void, List<ProcessManager.Process>>() {

            long startTime;

            @Override
            protected List<ProcessManager.Process> doInBackground(Void... params) {
                startTime = System.currentTimeMillis();
                return ProcessManager.getRunningApps();
            }

            @Override
            protected void onPostExecute(List<ProcessManager.Process> processes) {
               // StringBuilder sb = new StringBuilder();
               // sb.append("Execution time: ").append(System.currentTimeMillis() - startTime).append("ms\n");
             //   sb.append("Attention: Following Apps are using your location permissions\n");

                for (ProcessManager.Process process : processes) {

                    //check whether it is a system app or not
                    //-------------------------------START---------------------------------------------------------------------

                    Log.d("Foreground Process: ", "starts");

                    ApplicationInfoController applicationInfo = db.getApplicationInfo(process.uid);


                    //check whether it is a system app or not
                    //if system process id is of a system app then it will be null or 0
                    if(applicationInfo.getUID()!=0) {
                        //sb.append('\n').append(process.name);
                        //sb.append('\n').append(process.uid);

                        String log = "Id: "+applicationInfo.getId()+" ,UID: " + applicationInfo.getUID()
                                +" ,Name: " + applicationInfo.getAppName() + " ,Package Name: " + applicationInfo.getPackageName()
                                + " ,Permission Checked: " + applicationInfo.getPermissionChecked();
                        // Writing Contacts to log
                        Log.d("Foreground Process: ", log);

                        //Check gps permissions
                        List<PermissionInfoController> packageSpecificPermissionInfo = db.
                                getPackageSpecificPermissionInfo(applicationInfo.getUID());

                        for (PermissionInfoController apps : packageSpecificPermissionInfo) {
                            if(apps.getPermissions().equals("android.permission.ACCESS_COARSE_LOCATION")
                                    ||apps.getPermissions().equals("android.permission.ACCESS_FINE_LOCATION") ){

                                if(applicationInfo.getPermissionChecked().equals("1")) {// 0 is for testing only, it will be 1
                                    GPSProtectionEnable = true;
                                    sb.append('\n').append(applicationInfo.getAppName());
                                   // sb.append('\n').append(applicationInfo.getPackageName());
                                }

                                //sb.append('\n').append(applicationInfo.getAppName());
                                //sb.append('\n').append(applicationInfo.getPackageName());

                                break;
                            }

                        }

                    }
                }

                Log.d("GPSProtectionEnable: ", Boolean.toString(GPSProtectionEnable));

               // Intent service = new Intent(AppContext.getContext(), FakeGPSOnService.class);

                //------------------------------------------------------------------------------------------------------

                if (GPSProtectionEnable) {

                    //we have to show a warning dialogue here
                    //Log.d("locationWarning ", "GPS Permission");
                   // Toast.makeText(AppContext.getContext(), "GPS Permission", Toast.LENGTH_LONG).show();
                    //Toast.makeText(AppContext.getContext(), "Active Apps: " + sb.toString(), Toast.LENGTH_LONG).show();

                    //alert dialogue to warn user
                    //----------------------------------------

                     AlertDialog.Builder builder = new AlertDialog.Builder(AppContext.getContext());
            builder.setMessage(sb.toString())
                    .setCancelable(false)
                    .setTitle("Attention: Following Apps are using your location permissions!!!")
                    .setPositiveButton("OK",null);
            AlertDialog alert = builder.create();
            alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            alert.show();


                    //------------------------------------------------------------------------------

                }
                else{

                    Log.d("locationWarning ", "No GPS Permission");
                    Toast.makeText(AppContext.getContext(), "No GPS Permission", Toast.LENGTH_LONG).show();

                   // AppContext.getContext().stopService(service);
                }
                //------------------------------------------------------------------------------------------------/


                //printDialog(GPSProtectionEnable, sb.toString());
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        //------------------------------------------------------------------------------------------------
        db.close();

    }

}