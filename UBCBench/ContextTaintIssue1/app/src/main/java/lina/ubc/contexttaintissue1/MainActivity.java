package lina.ubc.contexttaintissue1;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

/**
 * This is a test case used to explain Amandroid's false positive related to $this taint issue.
 * To be more specific, Amandroid taints $this for all api_source.
 * Expected source: getDeviceId()
 * Expected sink: sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)
 * Number of expected flow: 0
 *
 * False positive flow that Amandroid reported:
 * tpm.getDeviceId() --> sms.sendTextMessage("+49 4444", null, "Telephony manager: "+tpm, null, null)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tpm.getDeviceId();      // Source: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("+49 4444", null, "Telephony manager: "+tpm, null, null);   // Sink: <android.telephony.SmsManager: void sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)> -> _SINK_

    }

}
