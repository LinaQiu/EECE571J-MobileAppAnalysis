package lina.ubc.sensitivitytestallinone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.LinkedList;

/**
 * This is a test case used to check whether the tool is object sensitive. If the tool is object sensitive, it should only report one flow as mentioned below.
 * Expected sources: getDeviceId()
 * Expected sinks: line 35: Log.e(java.lang.String, java.lang.String) && line 36: Log.e(java.lang.String, java.lang.String)
 * Number of expected leaks: 1
 * Flow Path:
 * line 32: list1.add(tpm.getDeviceId()) -->
 * line 35: Log.e("ObjectSensitivity1", list1.get(0)) --> leak
 */
public class ObjectSensitivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_sensitivity);

        LinkedList<String> list1 = new LinkedList<String>();
        LinkedList<String> list2 = new LinkedList<String>();

        TelephonyManager tpm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        list1.add(tpm.getDeviceId());   // Source: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_
        list2.add("123");

        Log.e("ObjectSensitivity1", list1.get(0));  // Sink1, Leak: <android.util.Log: int e(java.lang.String,java.lang.String)> -> _SINK_
        Log.e("ObjectSensitivity2", list2.get(0));  // Sink2, No leak: <android.util.Log: int e(java.lang.String,java.lang.String)> -> _SINK_

        Intent typeIntent = new Intent(this, TypeSensitivity.class);
        startActivity(typeIntent);
    }
}
