package lina.ubc.taintedintenttosink1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

/**
 * This is one of the three test cases used to show that DroidSafe will not report flows in the case that when tained intent is leaked directly to sinks.
 * The reason why DroidSafe failed to report the flow in ComponentNotInManifest test case in InterComponentCommunication folder
 * (https://github.com/secure-software-engineering/DroidBench/tree/master/eclipse-project/InterComponentCommunication/ComponentNotInManifest1)
 * from DroidBench is described below:
 * FNs: If a field of an intent is tainted, and the intent is leaked via a sink method directly, the tool will miss such a flow [DS]
 *
 * Expected sources: line 35: getDeviceId()
 * Expected sinks: line 41: startActivity(android.content.Intent)
 * Number of expected leaks: 1
 *
 * Flow Path:
 * Path1:
 * line 35: String deviceId = tpm.getDeviceId() -->
 * line 38: i.putExtra("ComponentNotInManifest", deviceId) -->
 * line 41: startActivity(i) --> leak
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

        // Case 1: source: getDeviceId() --> sink: startActivity(i)
        startActivity(i);   // Sink: <android.app.Activity: void startActivity(android.content.Intent)> -> _SINK_
    }
}
