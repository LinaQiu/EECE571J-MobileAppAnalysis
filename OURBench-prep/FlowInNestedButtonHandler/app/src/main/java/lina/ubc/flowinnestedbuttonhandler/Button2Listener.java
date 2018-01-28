package lina.ubc.flowinnestedbuttonhandler;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

/**
 * Created by lina on 2017-12-06.
 */

public class Button2Listener implements View.OnClickListener {
    private final MainActivity act;

    public Button2Listener(MainActivity parentActivity) {
        this.act = parentActivity;
    }

    @Override
    public void onClick(View arg0) {

        TelephonyManager telephonyManager = (TelephonyManager) act.getSystemService(Context.TELEPHONY_SERVICE);
        act.imei = telephonyManager.getDeviceId();  // Source: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_

        Log.i("TAG", "Button2 - IMEI: " + act.imei);    // Sink: <android.util.Log: int i(java.lang.String,java.lang.String)> -> _SINK_
    }
}
