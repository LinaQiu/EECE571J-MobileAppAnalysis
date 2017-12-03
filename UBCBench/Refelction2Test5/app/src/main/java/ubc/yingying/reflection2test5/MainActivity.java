package ubc.yingying.reflection2test5;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.telephony.TelephonyManager;



/**
 * @Description The benchmark is an extension of DroidBench-Reflection-Reflection2.
 *
 * This benchmark is to test
 * (1) if try-catch block and the sink we use has effects on tool performance
 * (2) further investigates ReflectionTest4. Compared with which, introduced 1 more flow from a different baseClass from the instance concrete1;
 *
 *
 *
 *
 * @ExpectedSources:
 * line 60: getDeviceId()
 * line 61: getDeviceId()
 * line 62: getDeviceId()
 * line 63: getDeviceId()
 *
 * @ExpectedSinks:
 * line 65: [leak] Log.i("concrete1,leak", concrete1.foo());
 * line 66: [no leak] Log.i("concrete2,no leak", concrete2.foo());
 * line 67: [leak] Log.i("concrete3,leak", concrete3.foo());
 * line 68: [no leak] Log.i("concrete4,no leak", concrete4.foo());
 *
 * @NumberOfExpectedLeaks: 2
 *
 * @FlowPaths:
 * Path1:
 * line 60: concrete1.imei = telephonyManager.getDeviceId(); -->
 * line 65: Log.i("concrete1,leak", concrete1.foo());  --> leak
 *
 * Path2:
 * line 62: concrete3.imei = telephonyManager.getDeviceId(); -->
 * line 67: Log.i("concrete4,no leak", concrete4.foo());  --> leak
 *
 */

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConcreteClass1 concrete1 = new ConcreteClass1();
        ConcreteClass2 concrete2 = new ConcreteClass2();
        ConcreteClass3 concrete3 = new ConcreteClass3();
        ConcreteClass4 concrete4 = new ConcreteClass4();

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        concrete1.imei = telephonyManager.getDeviceId(); // source
        concrete2.imei = telephonyManager.getDeviceId(); // source
        concrete3.imei = telephonyManager.getDeviceId(); // source
        concrete4.imei = telephonyManager.getDeviceId(); // source

        Log.i("concrete1,leak", concrete1.foo());  // sink, leak
        Log.i("concrete2,no leak", concrete2.foo()); // sink, no leak
        Log.i("concrete3,leak", concrete3.foo()); // sink, leak
        Log.i("concrete4,no leak", concrete4.foo()); // sink, no leak
    }




}
