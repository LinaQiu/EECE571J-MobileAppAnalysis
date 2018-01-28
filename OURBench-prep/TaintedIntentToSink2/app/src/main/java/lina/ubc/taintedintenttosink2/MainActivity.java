package lina.ubc.taintedintenttosink2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * This is one of the three test cases used to show that DroidSafe will not report flows in the case that when tained intent is leaked directly to sinks.
 * The reason why DroidSafe failed to report the flow in ComponentNotInManifest test case in InterComponentCommunication folder
 * (https://github.com/secure-software-engineering/DroidBench/tree/master/eclipse-project/InterComponentCommunication/ComponentNotInManifest1)
 * from DroidBench is described below:
 * FNs: If a field of an intent is tainted, and the intent is leaked via a sink method directly, the tool will miss such a flow [DS]
 *
 * Expected sources: line 36: getDeviceId()
 * Expected sinks: line 42: Log.i(java.lang.String, java.lang.String)
 * Number of expected leaks: 1
 *
 * Flow Path:
 * Path1:
 * line 36: String deviceId = tpm.getDeviceId() -->
 * line 39: i.putExtra("ComponentNotInManifest", deviceId) -->
 * line 42: Log.i("ComponentManifest2", "Intent: "+i.getStringExtra("ComponentNotInManifestTest")) --> leak
 *
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

        // Case 2: source: getDeviceId() --> sink: Log.d("ComponentManifest2", "Intent: "+i.getStringExtra("ComponentNotInManifestTest"))
        Log.i("ComponentManifest2", "Intent: "+i.getStringExtra("ComponentNotInManifestTest")); // Sink: <android.util.Log: int d(java.lang.String,java.lang.String)> -> _SINK_
    }
}
