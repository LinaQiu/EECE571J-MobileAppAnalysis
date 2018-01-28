package lina.ubc.pathsensitivity;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * This is a test case used to check whether the tool is path sensitive. If the tool is path sensitive, it should not report flow in this case.
 * If path insensitive, the tool would report a flow from the source to the sink.
 * If path sensitive, the tool would be able to record the conditions for each flow, then there would not be a flow from the source to the sink in this case.
 * Expected sources: 29: getDeviceId()
 * Expected sinks: 33: Log.e(java.lang.String, java.lang.String)
 * Number of expected leaks: 0
 */
public class PathSensitivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_sensitivity);

        TelephonyManager tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String deviceId = "";

        double condition = Math.random();

        if (condition>0) {
            deviceId=tpm.getDeviceId();     // Source: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_
        }

        if (!(condition<0)){
            Log.e("PathSensitivity", deviceId);     // Sink: <android.util.Log: int e(java.lang.String,java.lang.String)> -> _SINK_
        }
    }
}