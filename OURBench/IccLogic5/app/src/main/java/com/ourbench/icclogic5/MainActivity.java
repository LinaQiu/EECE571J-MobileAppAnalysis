package com.ourbench.icclogic5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

/**
 * This is a test case used to confirm IccTA's & DroidSafe's advanced ICC handling model.
 * (1) IccTA only reports the intra-component leaks related to ICC method sinks, when it can't determine whether a ICC flow exists.
 * (2) DroidSafe reports the intra-component leaks related to ICC method sinks, for all implicit Intents.
 *
 * In this case, we created an explicit Intent, pointing to AnotherActivity, and then sinked the tainted data in AnotherActivity.
 *
 * Expected source: getDeviceId()
 * Expected sink: MainActivity, line 48: startActivity(); AnotherActivity, line 14: Log.d()
 * Number of expected leak (based on taint analysis standards): 2
 * Number of expected leak (based on IccTA's ICC model): 1 (only Flow path2)
 * Number of expected leak (based on DroidSafe's ICC model): 1 (only Flow path2)
 *
 * Flow path1:
 * line 46: tpm.getDeviceId()   -->
 * line 49: intent.putExtra("IMEI", imei)   -->
 * line 50: startActivity(intent)   --> leak
 *
 * Flow path2:
 * line 46: tpm.getDeviceId()   -->
 * line 49: intent.putExtra("IMEI", imei)   -->
 * line 50: startActivity(intent)   -->
 * AnotherActivity -->
 * line 14: Log.d("IMEI", getIntent().getStringExtra("imei")) --> leak
 *
 */
public class MainActivity extends Activity {

    private TelephonyManager tpm;
    private static String imei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tpm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        imei = tpm.getDeviceId();    // Source: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_

        Intent i = new Intent(this, AnotherActivity.class);
        i.putExtra("imei", imei);
        startActivity(i);   // Sink1: <android.app.Activity: void startActivity(android.content.Intent)> -> _SINK_, leak
    }
}
