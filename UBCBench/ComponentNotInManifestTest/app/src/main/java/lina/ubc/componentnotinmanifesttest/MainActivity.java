package lina.ubc.componentnotinmanifesttest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * This is a test case used to show that DroidSafe will not report flows in the case that when tained intent is leaked directly to sinks.
 * The reason why DroidSafe failed to report the flow in ComponentNotInManifest test case in InterComponentCommunication folder
 * (https://github.com/secure-software-engineering/DroidBench/tree/master/eclipse-project/InterComponentCommunication/ComponentNotInManifest1)
 * from DroidBench is described below:
 * FNs: If a field of an intent is tainted, and the intent is leaked via a sink method directly, the tool will miss such a flow [DS]
 *
 * Expected sources: line 53: getDeviceId()
 * Expected sinks: line 59 & 62 & 65 & (InFlowActivity)22: startActivity(android.content.Intent) && Log.d(java.lang.String, java.lang.String) && Log.d(java.lang.String, java.lang.String) && Log.d(java.lang.String, java.lang.String)
 * Number of expected leaks: 4 (3 inside MainActivity, 1 from MainActivity to InFlowActivity)
 *
 * Flow Paths:
 * Path1:
 * line 53: String deviceId = tpm.getDeviceId() -->
 * line 56: i.putExtra("ComponentNotInManifest", deviceId) -->
 * line 59: startActivity(i) --> leak
 *
 * Path2:
 * line 53: String deviceId = tpm.getDeviceId() -->
 * line 56: i.putExtra("ComponentNotInManifest", deviceId) -->
 * line 62: Log.d("ComponentManifest2", "Intent: "+i.getStringExtra("ComponentNotInManifestTest")) --> leak
 *
 * Path3:
 * line 53: String deviceId = tpm.getDeviceId() -->
 * line 56: i.putExtra("ComponentNotInManifest", deviceId) -->
 * line 65: Log.d("ComponentManifest3", "Intent: "+i) --> leak
 *
 * Path4:
 * line 53: String deviceId = tpm.getDeviceId() -->
 * line 56: i.putExtra("ComponentNotInManifest", deviceId) -->
 * line 59: startActivity(i) -->
 * call InFlowActivity -->
 * line 20: Intent intent = getIntent() -->
 * line 22: Log.d("ComponentManifest4", intent.getStringExtra("ComponentNotInManifestTest")) --> leak
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager tpm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tpm.getDeviceId();    // Source: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_

        Intent i = new Intent(this, InFlowActivity.class);
        i.putExtra("ComponentNotInManifestTest", deviceId);

        // Case 1: source: getDeviceId() --> sink: startActivity(i)
        startActivity(i);   // Sink1: <android.app.Activity: void startActivity(android.content.Intent)> -> _SINK_

        // Case 2: source: getDeviceId() --> sink: Log.d("ComponentManifest2", "Intent: "+i.getStringExtra("ComponentNotInManifestTest"))
        Log.d("ComponentManifest2", "Intent: "+i.getStringExtra("ComponentNotInManifestTest")); // Sink2: <android.util.Log: int d(java.lang.String,java.lang.String)> -> _SINK_

        // Case 3: source: getDeviceId() --> sink: Log.d("ComponentManifest3", "Intent: "+i)
        Log.d("ComponentManifest3", "Intent: "+i);  // Sink3: <android.util.Log: int d(java.lang.String,java.lang.String)> -> _SINK_
    }
}
