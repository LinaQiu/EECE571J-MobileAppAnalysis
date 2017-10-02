package ubc.yingying.interfaceimplementation3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;


/**
 * @testcase_name Interfaceimplementation3
 * @description Test if the analysis tool could handle factory methods.
 * This is a testcase as an extension to the testcase DroidBench-GeneralJava-VirtualDispatch3.
 * In DroidBench-GeneralJava-VirtualDispatch3, it calls createInterfaceImplementation() in factoryTest();
 * Here, we call createOtherImplementation()
 * @expected_source: getDeviceId()
 * @expected_sink: sendTextMessage()
 * @number_of_expected_leaks: 1
 * @flow_path:
 * line 34: onCreate() -->
 * line 38: factoryTest() -->
 * line 55: createOtherImplementation() -->
 * line 56: A() -->
 * line 72: return mgr.getDeviceId() -->
 * line 46: data -->
 * line 48: sms.sendTextMessage("leak", null, data, null, null); [sink]
 */


public class MainActivity extends AppCompatActivity {

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
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("leak", null, data, null, null); // sink, leak

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




