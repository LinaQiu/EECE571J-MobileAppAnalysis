package lina.ubc.branching3;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * This is a test case used to explain the branching issue of FlowDroid, IccTA, Amandroid, and DroidSafe.
 * Expected source: getDeviceId()
 * Expected sink: Log.d(java.lang.String, java.lang.String)
 * Number of expected leaks: 2
 * Flow Paths:
 * Path1:
 * line 35: String deviceId = tpm.getDeviceId() -->
 * line 38: deviceIdAppendWith1=deviceId+"1" -->
 * line 44: Log.d("Branching1", "Device Id appended with 1: "+deviceIdAppendWith1+"Device Id appended with 10: "+deviceIdAppendWith10);
 *
 * Path2:
 * line 35: String deviceId = tpm.getDeviceId() -->
 * line 40: deviceIdAppendWith10=deviceId+"10" -->
 * line 44: Log.d("Branching1", "Device Id appended with 1: "+deviceIdAppendWith1+"Device Id appended with 10: "+deviceIdAppendWith10);
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String deviceIdAppendWith1=null;
        String deviceIdAppendWith10=null;

        TelephonyManager tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String deviceId = tpm.getDeviceId();    // Source: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_

        if (Math.random()>50){
            deviceIdAppendWith1=deviceId+"1";
        }else {
            deviceIdAppendWith10=deviceId+"10";
        }

        // Sink: <android.util.Log: int d(java.lang.String,java.lang.String)> -> _SINK_
        Log.d("Branching1", "Device Id appended with 1: "+deviceIdAppendWith1+"Device Id appended with 10: "+deviceIdAppendWith10);
    }
}
