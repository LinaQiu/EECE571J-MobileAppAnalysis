package lina.ubc.sensitivitytestallinone;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.LinkedList;

/**
 * Should only have three leak flows, from 25 --> 28, from 31 --> 33, and from 36 --> 38
 */
public class TypeSensitivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_sensitivity);

        A a = new A();
        B b = new B();

        TelephonyManager tpm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        a.list.add(tpm.getDeviceId());  // Source
        b.list.add("123");

        Log.e("TypeSensitivity1", a.list.get(0));   // Sink, Leak
        Log.e("TypeSensitivity2", b.list.get(0));   // Sink, No leak

        a.info = tpm.getDeviceId();
        b.info = "123";
        Log.e("TypeSensitivity3",a.info);           // Sink, Leak
        Log.e("TypeSensitivity4",b.info);           // Sink, No leak

        a.c.info = tpm.getDeviceId();
        b.c.info = "123";
        Log.e("TypeSensitivity5",a.c.info);         // Sink, Leak
        Log.e("TypeSensitivity6",b.c.info);         // Sink, No leak
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
