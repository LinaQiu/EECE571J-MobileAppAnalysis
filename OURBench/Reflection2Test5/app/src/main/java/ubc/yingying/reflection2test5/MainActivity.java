package ubc.yingying.reflection2test5;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;



/**
 * @Description The benchmark is an extension of DroidBench-Reflection-Reflection2 and UBCBench-Reflection2test1, Reflection2test2, and Reflection2test6.
 *
 * When there are two derived class from different base class, the tool should be able to distinguish them during reflection
 *
 * (Specific to this testcase, the tool should know ConcreteClass2 is derived from BaseClass and ConcreteClass4 is
 * derived from BaseClass2, but there is no flow as the two sources are not tainted at all)
 *
 * @ExpectedSources:
 * line 44: getDeviceId()
 * line 52: getDeviceId()
 *
 * @ExpectedSinks:
 * line 45: [leak] sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)
 * line 53: [leak] sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)
 *
 * @NumberOfExpectedLeaks: 0
 *
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            TelephonyManager telephonyManager2 = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            SmsManager sms2 = SmsManager.getDefault();

            BaseClass bc2 = (BaseClass) Class.forName("ubc.yingying.reflection2test5.ConcreteClass2").newInstance();
            bc2.imei = telephonyManager2.getDeviceId(); // source
            sms2.sendTextMessage("+49 2222", null, bc2.foo(), null,null); // sink, no leak


            TelephonyManager telephonyManager4 = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            SmsManager sms4 = SmsManager.getDefault();

            BaseClass2 bc4 = (BaseClass2) Class.forName("ubc.yingying.reflection2test5.ConcreteClass4").newInstance();
            bc4.imei = telephonyManager4.getDeviceId(); // source
            sms4.sendTextMessage("+49 4444", null, bc4.foo(),null,null);  // sink, no leak



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
