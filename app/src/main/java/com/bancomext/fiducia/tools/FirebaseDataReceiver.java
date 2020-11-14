package com.bancomext.fiducia.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class FirebaseDataReceiver extends BroadcastReceiver {

    private final String TAG = "FirebaseDataReceiver";
    public static boolean notificacion;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "I'm in!!!");
        Bundle dataBundle = intent.getBundleExtra("");
        if (intent != null && intent.getExtras() != null) {
            notificacion=true;
            Bundle extras = intent.getExtras();
            android.util.Log.e("bundle", extras.toString());

            Log.i("titile",""+extras.getString("title"));
            Log.i("message",""+extras.getString("message"));
            Log.i("contrato",""+extras.getString("contrato"));
            Log.i("badge",""+extras.getString("badge"));

        }
    }
}
