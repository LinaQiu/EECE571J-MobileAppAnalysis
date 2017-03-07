package lina.ubc.betweenappcomponents;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(
            Context.TELEPHONY_SERVICE);

        String deviceID = telephonyManager.getDeviceId();

        Intent intent = new Intent();
        intent.setAction("sending.device.id");
        intent.putExtra("DEVICE_ID", deviceID);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setComponent(new ComponentName("lina.ubc.inappcomponents","lina.ubc.inappcomponents.DeviceIDReceiver"));
        sendBroadcast(intent);
    }
}
