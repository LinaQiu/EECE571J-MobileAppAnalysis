package lina.ubc.sensitivitytestallinone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * This is a test case used to check whether the tool is context sensitive. If the tool is context sensitive, it should only report the flows mentioned in the below.
 * Expected sources: getDeviceId() && getSimSerialNumber()
 * Expected sinks: line 41: Log.e(java.lang.String, java.lang.String) && line 47: Log.e(java.lang.String, java.lang.String)
 * Number of expected leaks: 2
 * Flow Paths:
 * Path1:
 * line 40: String deviceId = tpm.getDeviceId() -->
 * line 41: Log.e("ContextSensitivity1", returnString(deviceId)) --> leak
 *
 * Path2:
 * line 46: String simNumber = tpm.getSimSerialNumber() -->
 * line 47: Log.e("ContextSensitivity2", returnString(simNumber)) --> leak
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
        String deviceId = tpm.getDeviceId();    // Source1: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_
        Log.e("ContextSensitivity1", returnString(deviceId));   // Sink1, leak1: <android.util.Log: int e(java.lang.String,java.lang.String)> -> _SINK_
    }

    void fun2(){
        TelephonyManager tpm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String simNumber = tpm.getSimSerialNumber();    // Source2: <android.telephony.TelephonyManager: java.lang.String getSimSerialNumber()> -> _SOURCE_
        Log.e("ContextSensitivity2", returnString(simNumber));  // Sink2, leak2: <android.util.Log: int e(java.lang.String,java.lang.String)> -> _SINK_
    }

    private String returnString(String info){
        return info;
    }
}
