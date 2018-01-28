package lina.ubc.uncalledactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

/**
 * This is a test case used to check if an activity is not called, will the flow between source and sink in the uncalled activity
 * gets reported.
 * Expected sources: line 47: getDeviceId()
 * Expected sinks:
 * MainActivity: line 50: startActivity(android.content.Intent)
 * CalledActivity: line 16: Log.d(java.lang.String,java.lang.String)
 * UncalledActivity: line 16: Log.d(java.lang.String,java.lang.String)
 * Number of expected leaks: 3
 *
 * Flow Paths:
 * Path1:
 * line 46: String deviceId = tpm.getDeviceId() -->
 * line 49: calledIntent.putExtra("IMEI1", deviceId) -->
 * line 50: startActivity(calledIntent) --> leak
 *
 * Path2:
 * line 46: String deviceId = tpm.getDeviceId() -->
 * line 49: calledIntent.putExtra("IMEI1", deviceId) -->
 * line 50: startActivity(calledIntent) -->
 * --> Call CalledActivity.java
 * line 16: Log.d("CalledActivity", receivedIntent.getStringExtra("IMEI1")) --> leak
 *
 * Path3:
 * line 46: String deviceId = tpm.getDeviceId() -->
 * line 53: uncalledIntent.putExtra("IMEI2", deviceId) -->
 * --> Call UncalledActivity
 * line 16: Log.d("UncalledActivity", receivedIntent.getStringExtra("IMEI2")) --> leak
 *
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String deviceId = tpm.getDeviceId();

        Intent calledIntent = new Intent(this, CalledActivity.class);
        calledIntent.putExtra("IMEI1", deviceId);
        startActivity(calledIntent);

        Intent uncalledIntent = new Intent(this, UncalledActivity.class);
        uncalledIntent.putExtra("IMEI2", deviceId);
    }
}
