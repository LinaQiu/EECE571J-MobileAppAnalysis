package lina.ubc.implicitflow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

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
        int oldvalue = Settings.System.getInt(mContext.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
        int newvalue = oldvalue == 1 ? 0 : 1;

        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", newvalue);
        mContext.sendBroadcast(intent);
    }

    // Case 2 & 3: implicit flows with normal sources and sinks.
    // However, Amandroid did not report flow in the following two cases.
    private void implicitFlow2(Context mContext) {
        TelephonyManager tpm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
        String deviceId = tpm.getDeviceId();

        boolean deviceIdIsNotNull = deviceId.equals("") ? false : true;

        // Case 2: source --> getDeviceId(); sink --> Log.i()
        Log.i("ImplicitFlow1: ", "Device is null? "+deviceIdIsNotNull);

        // Case 3: source --> getDeviceId(); sink --> sendBroadcast()
        Intent intent = new Intent(Intent.ACTION_ALL_APPS);
        intent.putExtra("deviceIdValue", deviceIdIsNotNull);
        mContext.sendBroadcast(intent);
    }
}
