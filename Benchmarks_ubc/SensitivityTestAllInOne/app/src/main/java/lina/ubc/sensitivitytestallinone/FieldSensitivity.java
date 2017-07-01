package lina.ubc.sensitivitytestallinone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * This is a test case used to check whether the tool is field sensitive. If the tool is field sensitive, it should only report one flow as mentioned below.
 * Expected sources: getDeviceId()
 * Expected sinks: line 32: Log.e(java.lang.String, java.lang.String) && line 33: Log.e(java.lang.String, java.lang.String)
 * Number of expected leaks: 1
 * Flow Path:
 * line 29: a.info1 = tpm.getDeviceId() -->
 * line 32: Log.e("FieldSensitivity1", a.info1) --> leak
 */
public class FieldSensitivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_sensitivity);

        A a = new A();

        TelephonyManager tpm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        a.info1 = tpm.getDeviceId();    // Source: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_
        a.info2 = "123";

        Log.e("FieldSensitivity1", a.info1);    // Sink1, Leak: <android.util.Log: int e(java.lang.String,java.lang.String)> -> _SINK_
        Log.e("FieldSensitivity2", a.info2);    // Sink2, No leak: <android.util.Log: int e(java.lang.String,java.lang.String)> -> _SINK_

        Intent contextIntent = new Intent(this, ContextSensitivity.class);
        startActivity(contextIntent);
    }

    class A{
        String info1;
        String info2;
    }

}
