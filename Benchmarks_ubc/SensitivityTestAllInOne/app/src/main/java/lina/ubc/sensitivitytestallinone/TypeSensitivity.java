package lina.ubc.sensitivitytestallinone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.LinkedList;

/**
 * Should only have three leak flows, from 25 --> 28, from 31 --> 33, and from 36 --> 38
 * This is a test case used to check whether the tool is type sensitive. If the tool is type sensitive, it should only report the flows mentioned in the below.
 * To test whether a tool is type sensitive, we must be able to disable the object sensitivity, if the tool is.
 * Otherwise, we will not be able to differentiate whether the results we get are due to object sensitive or type sensitive. (Reason behind: type sensitive is a weak-mode of object sensitive.)
 * Expected sources: line 44, 50, 55,: getDeviceId()
 * Expected sinks: line 47, 48, 52, 53, 57, 58,: Log.e(java.lang.String, java.lang.String)
 * Number of expected leaks: 3
 * Flow Paths:
 * Path1:
 * line 44: a.list.add(tpm.getDeviceId()) -->
 * line 47: Log.e("TypeSensitivity1", a.list.get(0)) --> leak
 *
 * Path2:
 * line 50: a.info = tpm.getDeviceId() -->
 * line 52: Log.e("TypeSensitivity3", a.info) --> leak
 *
 * Path3:
 * line 55: a.c.info = tpm.getDeviceId() -->
 * line 57: Log.e("TypeSensitivity5", a.c.info)
 */
public class TypeSensitivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_sensitivity);

        A a = new A();
        B b = new B();

        TelephonyManager tpm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        a.list.add(tpm.getDeviceId());  // Source1: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_
        b.list.add("123");

        Log.e("TypeSensitivity1", a.list.get(0));   // Sink1, Leak1: <android.util.Log: int e(java.lang.String,java.lang.String)> -> _SINK_
        Log.e("TypeSensitivity2", b.list.get(0));   // Sink2, No leak: <android.util.Log: int e(java.lang.String,java.lang.String)> -> _SINK_

        a.info = tpm.getDeviceId();     // Source2: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_
        b.info = "123";
        Log.e("TypeSensitivity3",a.info);           // Sink3, Leak2: <android.util.Log: int e(java.lang.String,java.lang.String)> -> _SINK_
        Log.e("TypeSensitivity4",b.info);           // Sink4, No leak: <android.util.Log: int e(java.lang.String,java.lang.String)> -> _SINK_

        a.c.info = tpm.getDeviceId();   // Source3: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_
        b.c.info = "123";
        Log.e("TypeSensitivity5",a.c.info);         // Sink5, Leak3: <android.util.Log: int e(java.lang.String,java.lang.String)> -> _SINK_
        Log.e("TypeSensitivity6",b.c.info);         // Sink6, No leak: <android.util.Log: int e(java.lang.String,java.lang.String)> -> _SINK_

        Intent pathIntent = new Intent(this, PathSensitivity.class);
        startActivity(pathIntent);
    }

    class A{
        LinkedList<String> list = new LinkedList<String>();
        String info;
        C c;
    }

    class B{
        LinkedList<String> list = new LinkedList<String>();
        String info;
        C c;
    }

    class C{
        String info;
    }
}
