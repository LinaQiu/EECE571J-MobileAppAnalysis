package lina.ubc.inappcomponents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DeviceIDReceiver extends BroadcastReceiver {

    public DeviceIDReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        String deviceID = intent.getStringExtra("DEVICE_ID");
        Log.e("Leakage", "BetweenAppComponents device id: "+deviceID);
    }
}
