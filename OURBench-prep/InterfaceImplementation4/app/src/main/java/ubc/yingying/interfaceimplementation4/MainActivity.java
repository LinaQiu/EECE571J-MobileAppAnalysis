package ubc.yingying.interfaceimplementation4;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;


/**
 * @testcase_name Interfaceimplementation4
 * @description Test if the analysis tool could handle factory methods.
 * This is an exact duplicate of the testcase DroidBench-GeneralJava-VirtualDispatch3.
 * The reason why we duplicate the testcase into UBCBench is for convenience to compare with Interfaceimplementation3.
 * Link for DroidBench-GeneralJava-VirtualDispatch3 is: [link](https://github.com/secure-software-engineering/DroidBench/blob/master/eclipse-project/GeneralJava/VirtualDispatch3/src/de/ecspride/MainActivity.java)
 * @expected_source: Line 63: getDeviceId()
 * @expected_sink: Line 39: Log.i(java.lang.String, java.lang.String)
 * @number_of_expected_leaks: 0
 * @flow_path: N/A
 */


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        factoryTest();

    }

    private void factoryTest() {
        MyInterface myif = createInterfaceImplementation();
       // MyInterface myif = createOtherImplementation();

        String data = myif.getString();

        Log.i("no leak", data); // sink, no leak

        MyInterface foo = createOtherImplementation();
        // MyInterface foo = createInterfaceImplementation();
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




