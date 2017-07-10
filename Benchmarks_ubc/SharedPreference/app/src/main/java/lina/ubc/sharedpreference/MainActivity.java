package lina.ubc.sharedpreference;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * This is a test case used to explain that Flowdroid, IccTA and Amandroid can't handle flows related to SharedPreference.
 * Expected source: getDeviceId()
 * Expected sink: Log.d(java.lang.String, java.lang.String)
 * Number of expected leaks: 1
 * Flow path:
 * line 30: String deviceId = tpm.getDeviceId() -->
 * line 34: editor.putString("deviceId", deviceId) -->
 * line 38: String deviceIdFromSharedPreference = settings.getString("deviceId", "") -->
 * line 39: Log.d("SharedPreference", deviceIdFromSharedPreference) --> leak
 */
public class MainActivity extends Activity {

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String deviceId = tpm.getDeviceId();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("deviceId", deviceId);

        editor.apply();

        String deviceIdFromSharedPreference = settings.getString("deviceId", "");
        Log.d("SharedPreference", deviceIdFromSharedPreference);
    }
}
