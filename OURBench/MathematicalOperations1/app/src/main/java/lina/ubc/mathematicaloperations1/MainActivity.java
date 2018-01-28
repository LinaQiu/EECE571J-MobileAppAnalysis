package lina.ubc.mathematicaloperations1;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * This is a test case used to explain that Amandroid is not able to handle flows that are related to mathematical operations.
 * Expected source: getDeviceId()
 * Expected sink: line 45: Log.d(java.lang.String, java.lang.String)
 * Number of expected leaks: 1
 * Flow Paths:
 * Path1:
 * line 27: String devcieId = tpm.getDeviceId() -->
 * line 29: double deviceIdPlus1 = Double.parseDouble(deviceId)+1.0 -->
 * line 30: Log.d("MathematicalOperations1", "The value of deviceId plus 1.0: "+deviceIdPlus1) --> leak
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String deviceId = tpm.getDeviceId();    // Source: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_

        double deviceIdPlus1 = Double.parseDouble(deviceId)+1.0;
        Log.d("MathematicalOperations1", "The value of deviceId plus 1.0: "+deviceIdPlus1); // Sink: <android.util.Log: int d(java.lang.String,java.lang.String)> -> _SINK_
    }
}
