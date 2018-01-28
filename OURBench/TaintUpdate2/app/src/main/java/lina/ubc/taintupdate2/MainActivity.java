package lina.ubc.taintupdate2;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MainActivity extends Activity {

    private TelephonyManager tpm;
    private static String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        deviceId = tpm.getDeviceId();

        if(true) {
            deviceId = null;
        }
        Log.d("deviceId", deviceId + " should equal to null.");
    }
}
