package lina.ubc.missstartactivitysink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

/**
 * This is a test case used to explain that IccTA would miss sink: startActivity().
 * We created an Intent, pointing to an activity class, which does nothing; and then we put deviceId to this intent,
 * and fire the activity via startActivity(intent) in MainActivity.
 * Expected source: getDeviceId()
 * Expected sink: startActivity(Intent intent)
 * Number of expected leaks: 1
 * Flow Path:
 * line 32: deviceId = tpm.getDeviceId() -->
 * line 34: Intent intent = new Intent(this, ReceiverActivity.class) -->
 * line 35: intent.putExtra("deviceId", deviceId) -->
 * line 36: startActivity(intent) --> leak
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

        Intent intent = new Intent(this, ReceiverActivity.class);
        intent.putExtra("deviceId", deviceId);
        startActivity(intent);  // Sink: <android.app.Activity: void startActivity(android.content.Intent)> -> _SINK_
    }
}
