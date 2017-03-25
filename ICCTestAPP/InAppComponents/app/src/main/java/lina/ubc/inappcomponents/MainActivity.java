package lina.ubc.inappcomponents;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager telephonyManager = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String deviceID = telephonyManager.getDeviceId();

        // Added in component source sink flow
        Log.e("In component deviceID: ", deviceID);

        Intent intent = new Intent(this, SendOutDeviceID.class);
        intent.putExtra("DEVICE_ID",deviceID);
        startService(intent);
    }
}
