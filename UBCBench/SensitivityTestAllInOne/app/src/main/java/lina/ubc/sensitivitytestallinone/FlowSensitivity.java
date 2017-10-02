package lina.ubc.sensitivitytestallinone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * This is a test case used to check whether the tool is flow sensitive. If the tool is flow sensitive, it should only report one flow as mentioned below.
 * Expected sources: getDeviceId()
 * Expected sinks: line 28: Log.e(java.lang.String, java.lang.String) && line 31: Log.e(java.lang.String, java.lang.String)
 * Number of expected leaks: 1
 * Flow Path:
 * line 27: String deviceId = tpm.getDeviceId() -->
 * line 28: Log.e("FlowSensitivity1", deviceId) --> leak
 */
public class FlowSensitivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_sensitivity);

        TelephonyManager tpm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tpm.getDeviceId();    // Source: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_
        Log.e("FlowSensitivity1", deviceId);    // Sink1, Leak: <android.util.Log: int e(java.lang.String,java.lang.String)> -> _SINK_

        deviceId = "123";
        Log.e("FlowSensitivity2", deviceId);    // Sink2, No leak: <android.util.Log: int e(java.lang.String,java.lang.String)> -> _SINK_

        Intent objectIntent = new Intent(this, ObjectSensitivity.class);
        startActivity(objectIntent);
    }

}
