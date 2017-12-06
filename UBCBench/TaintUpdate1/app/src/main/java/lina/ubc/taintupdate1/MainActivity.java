package lina.ubc.taintupdate1;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    private TelephonyManager tpm;
    private static String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
    }

    public void clickOnButton1(View view) {
        deviceId = tpm.getDeviceId();
    }

    public void clickOnButton2(View view) {
        deviceId = null;
        Log.d("DeviceId", deviceId+" should equal to null.");
    }
}
