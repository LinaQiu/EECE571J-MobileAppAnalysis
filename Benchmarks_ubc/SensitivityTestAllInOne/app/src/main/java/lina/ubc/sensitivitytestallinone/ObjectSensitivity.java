package lina.ubc.sensitivitytestallinone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.LinkedList;

/**
 * Should only have one leak flow, from 26 --> 29
 */
public class ObjectSensitivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_sensitivity);

        LinkedList<String> list1 = new LinkedList<String>();
        LinkedList<String> list2 = new LinkedList<String>();

        TelephonyManager tpm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        list1.add(tpm.getDeviceId());   // Source
        list2.add("123");

        Log.e("ObjectSensitivity1", list1.get(0));  // Sink, Leak
        Log.e("ObjectSensitivity2", list2.get(0));  // Sink, No leak

        Intent typeIntent = new Intent(this, TypeSensitivity.class);
        startActivity(typeIntent);
    }
}
