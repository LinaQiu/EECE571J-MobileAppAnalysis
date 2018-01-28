package ubc.yingying.interfaceimplementation2;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;


/**
 * @testcase_name Interfaceimplementation2
 * @description Test if the analysis tool could distinguish different implementations of an interface.
 * This is a testcase as an extension to the testcase DroidBench-GeneralJava-VirtualDispatch3.
 * Also, a simpler version of UBCBench-InterfaceImplementation1
 * @expected_source: Line 48: getDeviceId()
 * @expected_sink: Line 34 & 35: Log.i(java.lang.String, java.lang.String)
 * @number_of_expected_leaks: 1
 * @flow_path:
 * line 34: a.getString() -->
 * line 48: mgr.getDeviceId() -->
 * line 34: Log.i("leak", a.getString()) --> leak
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyInterface a = new A();
        MyInterface b = new B();



        Log.i("leak", a.getString()); //sink, leak
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




