package lina.ubc.implicitflow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * This is a test case used to show that Amandroid occasionally reports implicit flows, whereas, by design, it is not supposed to be able to detect implicit flows.
 * In our comparison/manual analysis, we do not consider implicit flows. We also configured the tool to disable implicit flow detection, if it supports.
 * Expected sources: line 42: getInt(android.content.ContentResolver,java.lang.String,int) && line 55: getDeviceId()
 * Expected sinks: line 48: sendBroadcast(android.content.Intent) && line 60: Log.i(java.lang.String, java.lang.String) && line 65: sendBroadcast(android.content.Intent)
 * Number of expected flows: 0 (because we do not consider implicit flows)
 *
 * False Positive Flows Amandroid reported:
 * Path1:
 * line 42: int oldvalue = Settings.System.getInt(mContext.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) -->
 * line 43: int newvalue = oldvalue == 1 ? 0 : 1 -->
 * line 47: intent.putExtra("state", newvalue) -->
 * line 48: mContext.sendBroadcast(intent) --> leak
 */
public class MainActivity extends Activity {

    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        implicitFlow1(mContext);
        implicitFlow2(mContext);
    }

    // Case 1: simplied the original case study: Connectivity, 2K_org.aja.flightmode_3.apk
    private void implicitFlow1(Context mContext) {
        int oldvalue = Settings.System.getInt(mContext.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0); // Source1: <android.provider.Settings$System: int getInt(android.content.ContentResolver,java.lang.String,int)> -> _SOURCE_
        int newvalue = oldvalue == 1 ? 0 : 1;

        // Case 1: implicit flow: source --> Settings.System.getInt(); sink --> sendBroadcast()
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", newvalue);
        mContext.sendBroadcast(intent); // Sink1: <android.content.Context: void sendBroadcast(android.content.Intent)> -> _SINK_
    }

    // Case 2 & 3: implicit flows with normal sources and sinks.
    // However, Amandroid did not report flow in the following two cases, which is correct, since we did not consider implicit flows during manual analysis and tools' analysis.
    private void implicitFlow2(Context mContext) {
        TelephonyManager tpm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
        String deviceId = tpm.getDeviceId();        // Source2: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_

        boolean deviceIdIsNotNull = deviceId.equals("") ? false : true;

        // Case 2: implicit flow: source --> getDeviceId(); sink --> Log.i()
        Log.i("ImplicitFlow1: ", "Device is null? "+deviceIdIsNotNull); // Sink2: <android.util.Log: int i(java.lang.String,java.lang.String)> -> _SINK_

        // Case 3: implicit flow: source --> getDeviceId(); sink --> sendBroadcast()
        Intent intent = new Intent(Intent.ACTION_ALL_APPS);
        intent.putExtra("deviceIdValue", deviceIdIsNotNull);
        mContext.sendBroadcast(intent);     // Sink3: <android.content.Context: void sendBroadcast(android.content.Intent)> -> _SINK_
    }
}
