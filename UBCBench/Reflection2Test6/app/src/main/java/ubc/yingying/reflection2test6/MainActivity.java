package ubc.yingying.reflection2test6;

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
 * line 50: getDeviceId()
 * line 51: getDeviceId()
 *
 * @ExpectedSinks:
 * line 54: [leak] Log.i("concrete1, leak", concrete1.foo());
 * line 55: [no leak]  Log.i("concrete2, no leak", concrete2.foo());
 *
 * @NumberOfExpectedLeaks: 1
 *
 * @FlowPaths:
 * Path1:
 * line 50: concrete1.imei = telephonyManager.getDeviceId(); -->
 * line 54: Log.i("concrete1, leak", concrete1.foo()); --> leak
 *
 */

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ConcreteClass1 concrete1 = new ConcreteClass1();
        ConcreteClass2 concrete2 = new ConcreteClass2();

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        concrete1.imei = telephonyManager.getDeviceId(); // source
        concrete2.imei = telephonyManager.getDeviceId(); // source


        Log.i("concrete1, leak", concrete1.foo()); // sink,leak
        Log.i("concrete2, no leak", concrete2.foo()); // sink, no leak



    }




}
