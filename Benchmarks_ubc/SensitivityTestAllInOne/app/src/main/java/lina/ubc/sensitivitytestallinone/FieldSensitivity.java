package lina.ubc.sensitivitytestallinone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Should only have one leak flow, from 23 --> 26
 */
public class FieldSensitivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_sensitivity);

        A a = new A();

        TelephonyManager tpm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        a.info1 = tpm.getDeviceId();    // Source
        a.info2 = "123";

        Log.e("FieldSensitivity1", a.info1);    // Sink, Leak
        Log.e("FieldSensitivity2", a.info2);    // Sink, No leak

        Intent contextIntent = new Intent(this, ContextSensitivity.class);
        startActivity(contextIntent);
    }

    class A{
        String info1;
        String info2;
    }

}
