package lina.ubc.contexttaintissue2;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * This is a test case used to explain Amandroid's another kind of context taint issue.
 * To be more specific, Amandroid taints $this for all variables that are tainted by any sources.
 * Expected Source: getDeviceId()
 * Expected Sink: line 32 & 33: Log.i(java.lang.String, java.lang.String)
 * Number of expected flows: 0
 *
 * False positives that Amandroid reported:
 * FP1: deviceId = tpm.getDeviceId() --> Log.i("ContextTaintIssue2_1", "Current context: "+this)
 * FP2: deviceId = tpm.getDeviceId() --> Log.i("ContextTaintIssue2_2", anotherVariable)
 */
public class MainActivity extends Activity {

    private String deviceId;
    private String anotherVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        deviceId = tpm.getDeviceId();   // Source: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_

        Log.i("ContextTaintIssue2_1", "Current context: "+this);    // Sink1: <android.util.Log: int i(java.lang.String,java.lang.String)> -> _SINK_
        Log.i("ContextTaintIssue2_2", anotherVariable);    // Sink2: <android.util.Log: int i(java.lang.String,java.lang.String)> -> _SINK_
    }
}
