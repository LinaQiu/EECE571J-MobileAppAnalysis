package ubc.yingying.interfaceimplementation3;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;


/**
 * @testcase_name Interfaceimplementation3
 * @description Test if the analysis tool could handle factory methods.
 * This is a testcase as an extension to the testcase DroidBench-GeneralJava-VirtualDispatch3.
 * In DroidBench-GeneralJava-VirtualDispatch3, it calls createInterfaceImplementation() in factoryTest();
 * Here, we call createOtherImplementation()
 * @expected_source: Line 68: getDeviceId()
 * @expected_sink: Line 44: Log.i(java.lang.String, java.lang.String)
 * @number_of_expected_leaks: 1
 * @flow_path:
 * line 30: onCreate() -->
 * line 38: factoryTest() -->
 * line 51: createOtherImplementation() -->
 * line 52: A() -->
 * line 68: return mgr.getDeviceId() -->
 * line 42: data -->
 * line 44: Log.i("leak", data) --> leak
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        factoryTest();

    }

    private void factoryTest() {
        // MyInterface myif = createInterfaceImplementation();
        MyInterface myif = createOtherImplementation();

        String data = myif.getString();

        Log.i("leak", data); // sink, leak

        //MyInterface foo = createOtherImplementation();
        MyInterface foo = createInterfaceImplementation();
        System.out.println(foo);
    }

    private MyInterface createOtherImplementation() {
        return new A();
    }

    private MyInterface createInterfaceImplementation() {
        return new B();
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




