package lina.ubc.contexttaintissue2;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

/**
 * This is a test case used to explain Amandroid's another kind of context taint issue.
 * To be more specific, Amandroid taints $this for all variables that are tainted by any sources.
 * Expected Source: getDeviceId()
 * Expected Sink: line 33 & 34: sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)
 * Number of expected flows: 0
 *
 * False positives that Amandroid reported:
 * FP1: deviceId = tpm.getDeviceId() --> smsManager.sendTextMessage("+49 4444", null, "Current context"+this, null, null)
 * FP2: deviceId = tpm.getDeviceId() --> sms.sendTextMessage("+49 4444", null, "This variable should not be tainted. "+anotherVariable, null, null)
 */
public class MainActivity extends Activity {

    private String deviceId;
    private String anotherVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        deviceId = tpm.getDeviceId();   // Source: <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("+49 4444", null, "Current context"+this, null, null); // Sink2: <android.telephony.SmsManager: void sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)> -> _SINK_
        sms.sendTextMessage("+49 4444", null, "This variable should not be tainted. "+anotherVariable, null, null); // Sink1: <android.telephony.SmsManager: void sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)> -> _SINK_
    }
}
