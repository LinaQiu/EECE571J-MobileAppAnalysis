package ubc.yingying.reflection2test4;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;


/**
 * @Description The benchmark is an extension of DroidBench-Reflection-Reflection2.
 *
 * This benchmark is to test if the sink we choose & try-catch exception block will affect tool's
 * analysis result
 *
 * (in the original Reflection2 testcase, it used sendTextMessage() as sink, and includes a try-catch
 * block in the source code)
 *
 *
 * @ExpectedSources:
 * line 53: getDeviceId()
 * line 54: getDeviceId()
 * line 55: getDeviceId()
 *
 * @ExpectedSinks:
 * line 57: [leak] Log.i("concrete1,leak", concrete1.foo());
 * line 58: [no leak] Log.i("concrete2,no leak", concrete2.foo());
 * line 59: [no leak] Log.i("concrete4,no leak", concrete4.foo());
 *
 * @NumberOfExpectedLeaks: 1
 *
 * @FlowPaths:
 * Path1:
 * line 53: concrete1.imei = telephonyManager.getDeviceId(); -->
 * line 57: Log.i("concrete1,leak", concrete1.foo()); --> leak
 *
 */

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ConcreteClass1 concrete1 = new ConcreteClass1();
        ConcreteClass2 concrete2 = new ConcreteClass2();
        ConcreteClass4 concrete4 = new ConcreteClass4();

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        concrete1.imei = telephonyManager.getDeviceId(); // source
        concrete2.imei = telephonyManager.getDeviceId(); // source
        concrete4.imei = telephonyManager.getDeviceId(); // source

        Log.i("concrete1,leak", concrete1.foo()); // sink, leak
        Log.i("concrete2,no leak", concrete2.foo()); // sink, no leak
        Log.i("concrete4,no leak", concrete4.foo()); // sink, no leak


    }




}
