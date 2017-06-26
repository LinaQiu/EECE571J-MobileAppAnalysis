package lina.ubc.setintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

/**
 * This is a test case used to explain the false positive related to setIntent() that Amandroid reports.
 * Expected source: getDeviceId()
 * Expected sink: startActivity(android.content.Intent)
 * Number of expected leaks: 1
 * Flow path:
 * line 31: String deviceId = getDeviceId() -->
 * line 37: newIntent.putExtra("deviceId", deviceId) -->
 * line 38: startActivity(newIntent) --> leak
 */
public class MainActivity extends Activity {

    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        TelephonyManager tpm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
        String deviceId = tpm.getDeviceId();    // Source: Landroid/telephony/TelephonyManager;.getDeviceId:()Ljava/lang/String; SENSITIVE_INFO -> _SOURCE_

        Intent receivedIntent = getIntent();

        if (receivedIntent != null) {
            Intent newIntent = new Intent(this, MainActivity.class);
            newIntent.putExtra("deviceId", deviceId);
            startActivity(newIntent);   // Sink: Landroid/app/Activity;.startActivity:(Landroid/content/Intent;)V -> _SINK_
        }
    }
}
