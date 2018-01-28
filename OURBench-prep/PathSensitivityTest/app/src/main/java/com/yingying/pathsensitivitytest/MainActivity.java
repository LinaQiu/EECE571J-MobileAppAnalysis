package com.yingying.pathsensitivitytest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = "";
        String deviceId2 = "";
        String deviceId3 = "";

        if (Math.random() < 0.5) {
            deviceId = tm.getDeviceId();
            deviceId2 = deviceId;
        } else {
            deviceId3 = deviceId;
        }

        // Log.i("deviceId", deviceId);
        Log.i("deviceId2, leak", deviceId2);
        Log.i("deviceId3, no leak", deviceId3);
        */
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = "";
        double randomNum= Math.random();
        if(randomNum < 0.5){
            deviceId = tm.getDeviceId();
        }
        if(! (randomNum<0.5)){
            Log.i("deviceId", deviceId);
        }
    }


  /*  public void sendMessage(View view)
    {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = "";
        String deviceId2 = "";
        String deviceId3 = "";

        if (Math.random() < 0.5) {
            deviceId = tm.getDeviceId();
            deviceId2 = deviceId;
        } else {
            deviceId3 = deviceId;
        }

        // Log.i("deviceId", deviceId);
        Log.i("deviceId2, leak", deviceId2);
        Log.i("deviceId3, no leak", deviceId3);
    }
    */
}