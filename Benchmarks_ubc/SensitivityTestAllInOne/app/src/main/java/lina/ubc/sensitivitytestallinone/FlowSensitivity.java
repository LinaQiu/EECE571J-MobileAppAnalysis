package lina.ubc.sensitivitytestallinone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Should only have one leak flow, from 21 --> 22
 */
public class FlowSensitivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_sensitivity);

        TelephonyManager tpm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tpm.getDeviceId();    // Source
        Log.e("FlowSensitivity1", deviceId);    // Sink, Leak

        deviceId = "123";
        Log.e("FlowSensitivity2", deviceId);    // Sink, No leak

        Intent objectIntent = new Intent(this, ObjectSensitivity.class);
        startActivity(objectIntent);
    }

}
