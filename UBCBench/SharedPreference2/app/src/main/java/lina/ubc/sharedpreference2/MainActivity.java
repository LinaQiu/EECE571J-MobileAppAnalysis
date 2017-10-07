package lina.ubc.sharedpreference2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * This is a test case used to help clarify that: for the /Lifecycle/SharedPreferenceChanged1/ test case in DroidBench
 * (https://github.com/secure-software-engineering/DroidBench/tree/master/eclipse-project/Lifecycle/SharedPreferenceChanged1),
 * the reason why FD, IccTA, and AD failed to detect this flow is not about handling the lifecycle events of shared
 * preferences. Those tools can successfully detect the changes in shared preferences, and call the callback listener
 * OnSharedPreferenceChangeListener. The reason why the three tools failed to detect the flow is as described below:
 * FNs: Fails to model SharedPreference. Specifically, the tool fails to detect the value read/write from/to the shared preferences
 *
 * Expected Source: line 48 & 49: getDeviceId()
 * Expected Sink: line 62 & 63: Log.i(java.lang.String, java.lang.String)
 * Number of expected flows: 2
 *
 * Flow Paths:
 * Path1:
 * line 48: String imei = mgr.getDeviceId() -->
 * line 52: settings.registerOnSharedPreferenceChangeListener(prefsListener) -->
 * line 55: editor.putString("imei", imei) -->
 * call --> SharedPreferences.OnSharedPreferenceChangeListener prefsListener -->
 * line 61: String imei = sharedPreferences.getString(key, "") -->
 * line 62: Log.i("DroidBench", imei) --> leak
 *
 * Path2:
 * line 49: globalImei = mgr.getDeviceId() -->
 * line 52: settings.registerOnSharedPreferenceChangeListener(prefsListener) -->
 * line 55: editor.putString("imei", imei) -->
 * call --> SharedPreferences.OnSharedPreferenceChangeListener prefsListener -->
 * line 63: Log.i("DroidBench: GlobalImei", globalImei) --> leak
 */

public class MainActivity extends Activity {

    private String globalImei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager mgr = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        String imei = mgr.getDeviceId();    // <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_
        globalImei = mgr.getDeviceId();     // <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_

        SharedPreferences settings = getSharedPreferences("settings", 0);
        settings.registerOnSharedPreferenceChangeListener(prefsListener);

        SharedPreferences.Editor editor = settings.edit();
        editor.putString("imei", imei);
    }

    SharedPreferences.OnSharedPreferenceChangeListener prefsListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            String imei = sharedPreferences.getString(key, "");
            Log.i("DroidBench", imei);  // <android.util.Log: int i(java.lang.String,java.lang.String)> -> _SINK_
            Log.i("DroidBench: GlobalImei", globalImei);    // <android.util.Log: int i(java.lang.String,java.lang.String)> -> _SINK_
        }
    };
}
