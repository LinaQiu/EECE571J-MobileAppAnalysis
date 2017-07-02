package lina.ubc.contexttaintissue;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * This is a test case used to explain Amandroid's false positive related to $this taint issue.
 * To be more specific, Amandroid taints $this for all api_source.
 * Expected source: getDeviceId()
 * Expected sink: Log.i(java.lang.String, java.lang.String)
 * Number of expected flow: 0
 *
 * False positive flow that Amandroid reported:
 * tpm.getDeviceId() --> Log.i("ContextTaint", "Telephony manager: "+tpm)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tpm.getDeviceId();      // Source:
        Log.i("ContextTaint", "Telephony manager: "+tpm);

    }

}
