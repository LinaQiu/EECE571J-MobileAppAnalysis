package ubc.yingying.interfaceimplementation2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;


/**
 * @testcase_name Interfaceimplementation2
 * @description Test if the analysis tool could distinguish different implementations of an interface.
 * This is a testcase as an extension to the testcase DroidBench-GeneralJava-VirtualDispatch3.
 * Also, a simpler version of UBCBench-InterfaceImplementation1
 * @expected_source: getDeviceId()
 * @expected_sink: sendTextMessage(), Log.i()
 * @number_of_expected_leaks: 1
 * @flow_path:
 * line 38 : a.getString() -->
 * line 52 : mgr.getDeviceId() -->
 * line 38 : smsmanager.sendTextMessage() [sink]
 */


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyInterface a = new A();
        MyInterface b = new B();

        SmsManager smsmanager = SmsManager.getDefault();

        smsmanager.sendTextMessage("leak", null, a.getString(), null, null); //sink, leak
        Log.i("no leak", b.getString()); //sink, no leak
    }


    interface MyInterface {
        String getString();
    }

    class A implements MyInterface {

        @Override
        public String getString() {
            TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            return mgr.getDeviceId();	// source
        }
    }

    class B implements MyInterface {

        @Override
        public String getString() {
            return "constant";
        }

    }

}




