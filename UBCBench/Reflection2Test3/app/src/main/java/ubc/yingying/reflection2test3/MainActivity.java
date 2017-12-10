package ubc.yingying.reflection2test3;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;


/**
 * @Description The benchmark is an extension of DroidBench-Reflection-Reflection2.
 *
 * The benchmark is to test if changing the sink we use would affect analysis results
 *
 * (in the original Reflection2 testcase, it used sendTextMessage() as sink)
 *
 *
 * @ExpectedSources:
 * line 50: getDeviceId() [LEAK]
 * line 53: getDeviceId() [NO LEAK]
 *
 * @ExpectedSinks:
 * line 55: Log.i("concrete1,leak", concrete1.foo()); [LEAK]
 * line 56: Log.i("concrete2,no leak", concrete2.foo()); [NO LEAK]
 *
 * @NumberOfExpectedLeaks: 1
 *
 * @FlowPaths:
 * Path1:
 * line 50: concrete1.imei = telephonyManager.getDeviceId(); -->
 * line 55: Log.i("concrete1,leak", concrete1.foo()); --> leak
 *
 *
 */

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            BaseClass concrete1 = (BaseClass) Class.forName("ubc.yingying.reflection2test3.ConcreteClass1").newInstance();
            BaseClass concrete2 = (BaseClass) Class.forName("ubc.yingying.reflection2test3.ConcreteClass2").newInstance();


            TelephonyManager telephonyManager1 = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            concrete1.imei = telephonyManager1.getDeviceId(); // source

            TelephonyManager telephonyManager2 = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            concrete2.imei = telephonyManager2.getDeviceId(); // source

            Log.i("concrete1,leak", concrete1.foo()); // sink, leak
            Log.i("concrete2,no leak", concrete2.foo()); // sink, no leak


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }




}
