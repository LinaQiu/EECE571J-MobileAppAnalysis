package lina.ubc.androidmodeling1test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

/**
 * This is a test case used to check whether the tool can't handle ApplicationModeling1 test case from DroidBench, due to
 * it can't handle/model application lifecycle, or it misses the sinks.
 * Expected sources: line 60: getDeviceId()
 * Expected sinks:
 * MainActivity: line 67: startActivity(android.content.Intent)
 * AnotherActivity: line 17 & 18 & 20: Log.i(java.lang.String,java.lang.String)
 * Number of expected leaks: 5
 *
 * Flow Paths:
 * Path1:
 * line 60: String imei = mgr.getDeviceId() -->
 * line 65: sendIntent.putExtra("IMEI1", imei) -->
 * line 67: startActivity(sendIntent) --> leak
 *
 * Path2:
 * line 60: String imei = mgr.getDeviceId() -->
 * line 62: ((MyApplication)getApplication()).imei = imei -->
 * line 66: sendIntent.putExtra("IMEI2", imei) -->
 * line 67: startActivity(sendIntent) --> leak
 *
 * Path3:
 * line 60: String imei = mgr.getDeviceId() -->
 * line 65: sendIntent.putExtra("IMEI1", imei) -->
 * line 67: startActivity(sendIntent) -->
 * --> Call AnotherActivity.java
 * line 17: Log.i("ApplicationModeling1", receivedIntent.getStringExtra("IMEI1")) --> leak
 *
 * Path4:
 * line 60: String imei = mgr.getDeviceId() -->
 * line 62: ((MyApplication)getApplication()).imei = imei -->
 * line 66: sendIntent.putExtra("IMEI2", imei) -->
 * line 67: startActivity(sendIntent) -->
 * --> Call AnotherActivity.java
 * line 18: Log.i("ApplicationModeling2", receivedIntent.getStringExtra("IMEI2")) --> leak
 *
 * Path5:
 * line 60: String imei = mgr.getDeviceId() -->
 * line 62: ((MyApplication)getApplication()).imei = imei -->
 * line 67: startActivity(sendIntent) -->
 * --> Call AnotherActivity.java
 * line 20: Log.i("ApplicationModeling3", ((MyApplication)getApplication()).imei) --> leak
 *
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager mgr = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        String imei = mgr.getDeviceId();	// Source: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_

        ((MyApplication)getApplication()).imei = imei;

        Intent sendIntent = new Intent(this, AnotherActivity.class);
        sendIntent.putExtra("IMEI1", imei);
        sendIntent.putExtra("IMEI2", ((MyApplication)getApplication()).imei);
        startActivity(sendIntent);  // Sink1: <android.app.Activity: void startActivity(android.content.Intent)> -> _SINK_
    }
}
