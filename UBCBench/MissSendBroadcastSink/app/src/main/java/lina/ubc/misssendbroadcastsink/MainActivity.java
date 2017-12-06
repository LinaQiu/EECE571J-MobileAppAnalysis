package lina.ubc.misssendbroadcastsink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

/**
 * This is a test case used to explain that IccTA and FlowDroid would miss sink: sendBroadcast(); therefore, miss relevant flows.
 * Expected source: getDeviceId()
 * Expected sink: sendBroadcast(Intent intent)
 * Number of expected leaks: 1
 * Flow Path:
 * line 30: deviceId = tpm.getDeviceId() -->
 * line 32: Intent intent = new Intent(Intent.ACTION_ALL_APPS) -->
 * line 33: intent.putExtra("deviceId", deviceId) -->
 * line 34: sendBroadcast(intent) --> leak
 */
public class MainActivity extends Activity {

    private TelephonyManager tpm;
    private static String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        deviceId = tpm.getDeviceId();   // Source: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_

        Intent intent = new Intent(Intent.ACTION_ALL_APPS);
        intent.putExtra("deviceId", deviceId);
        sendBroadcast(intent);  // Sink: <android.content.ContextWrapper: void sendBroadcast(android.content.Intent)> -> _SINK_
    }
}
