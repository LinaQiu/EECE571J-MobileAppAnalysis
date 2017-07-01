package lina.ubc.sensitivitytestallinone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Should only have two leak flows, from 29 --> 30 and from 35 --> 36
 */
public class ContextSensitivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context_sensitivity);

        fun1();
        fun2();

        Intent flowIntent = new Intent(this, FlowSensitivity.class);
        startActivity(flowIntent);
    }

    void fun1() {
        TelephonyManager tpm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tpm.getDeviceId();    // Source1
        Log.e("ContextSensitivity1", returnString(deviceId));   // Sink1
    }

    void fun2(){
        TelephonyManager tpm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String simNumber = tpm.getSimSerialNumber();    // Source2
        Log.e("ContextSensitivity2", returnString(simNumber));  // Sink2
    }

    private String returnString(String info){
        return info;
    }
}
