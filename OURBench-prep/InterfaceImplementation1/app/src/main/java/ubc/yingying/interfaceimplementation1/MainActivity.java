package ubc.yingying.interfaceimplementation1;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;


/**
 * @testcase_name Interfaceimplementation1
 * @description Test if the analysis tool could distinguish different implementations of an interface.
 * This is a testcase as an extension to the testcase DroidBench-GeneralJava-VirtualDispatch3.
 * @expected_source: Line 60: getDeviceId()
 * @expected_sink: Line 39 & 40: Log.i(java.lang.String, java.lang.String)
 * @number_of_expected_leaks: 1
 * @flow_path:
 * line 41 : test1.method(a) -->
 * line 60 : A.getString() -->
 * line 62 : mgr.getDeviceId() -->
 * line 41 : Log.i("leak", test1.method(a)) --> leak
 */


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Test test1 = new Test();
        Test test2 = new Test();

        MyInterface a = new A();
        MyInterface b = new B();



        Log.i("leak", test1.method(a)); //sink, leak
        Log.i("no leak", test2.method(b)); //sink, no leak
    }


    class Test {
        public String method(MyInterface a) {
            return a.getString();
        }
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




