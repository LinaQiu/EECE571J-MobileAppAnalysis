package lina.ubc.eventorderingtest;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * This is a test case to explain that FlowDroid, IccTA, Amandroid, and DroidSafe can handle event ordering flows.
 *
 * However, FlowDroid, IccTA and Amandroid failed to detect the flow in EventOrdering1 test case in InterComponentCommunication from DroidBench.
 *
 * Taking this test case and our SharedPreference test case into consideration, the reason why FlowDroid, IccTA and Amandroid failed to detect the flow
 * in EventOrdering1 (in ICC folder from DroidBench) is that those tools are not able to handle reading/writing data from/to SharedPreference.
 *
 * Expected sources: tpm.getDeviceId()
 * Expected sinks: Log.i("EventOrderingTest", deviceId)
 * Number of expected leaks: 1
 *
 * Flow path:
 * Launch ActivityWithFlow for the first time from MainActivity -->
 * line 39: assignSensitivityInfo() -->
 * line 44: deviceId = tpm.getDeviceId() -->
 * Launch ActivityWithFlow for the second time from MainActivity -->
 * line 37: Log.i("EventOrderingTest", deviceId) --> leaks
 *
 */
public class ActivityWithFlow extends Activity {

    private static String deviceId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_flow);

        Log.i("EventOrderingTest", deviceId);  //Sink: <android.util.Log: int i(java.lang.String,java.lang.String)> -> _SINK_

        assignSensitivityInfo();
    }

    private void assignSensitivityInfo() {
        TelephonyManager tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        deviceId = tpm.getDeviceId(); // Source: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_
    }
}
