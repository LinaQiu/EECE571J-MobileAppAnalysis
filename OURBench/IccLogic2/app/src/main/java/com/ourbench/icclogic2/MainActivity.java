package com.ourbench.icclogic2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

/**
 * This is a test case used to confirm IccTA's & DroidSafe's advanced ICC handling model.
 * (1) IccTA only reports the intra-component leaks related to ICC method sinks, when it can't determine whether a ICC flow exists.
 * (2) DroidSafe reports the intra-component leaks related to ICC method sinks, for all implicit Intents.
 *
 * In this case, there is no receiver (component) for the defined intent action within the app scope.
 *
 * Expected source: getDeviceId()
 * Expected sink: startActivity()
 * Number of expected leak (based on taint analysis standards): 1
 * Number of expected leak (based on IccTA's ICC model): 1
 * Number of expected leak (based on DroidSafe's ICC model): 1
 *
 * Flow path:
 * line 38: tpm.getDeviceId() -->
 * line 42: intent.putExtra("IMEI", imei) -->
 * line 43: startActivity(intent) --> leak
 *
 */
public class MainActivity extends Activity {

    private TelephonyManager tpm;
    private static String imei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        imei = tpm.getDeviceId();   // Source: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_

        Intent intent = new Intent();
        intent.setAction("OURBenchtest");
        intent.putExtra("IMEI", imei);
        startActivity(intent);  // Sink: <android.app.Activity: void startActivity(android.content.Intent)> -> _SINK_, leak
    }
}
