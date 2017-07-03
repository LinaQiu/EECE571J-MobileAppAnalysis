package lina.ubc.multientryissue;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.concurrent.Executors;

/**
 * This is a test case used to explain DroidSafe's multi entry issue.
 * To be more specific, DroidSafe reported flow with sink's entry point, instead of source's entry point.
 * Expected Sources: getDeviceId()
 * Expected Sinks: Log.d(java.lang.String, java.lang.String)
 * Number of expected flows: 1
 * Path:
 * line 28: Executors.newCachedThreadPool().execute(new MyRunnable(tpm.getDeviceId())) -->
 * line 40: Log.d("ActivityWithRunnable", deviceId) --> leak
 */
public class ActivityWithRunnable extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_runnable);

        TelephonyManager tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        Executors.newCachedThreadPool().execute(new MyRunnable(tpm.getDeviceId()));
    }

    private class MyRunnable implements Runnable {
        private final String deviceId;

        public MyRunnable(String deviceId) {
            this.deviceId = deviceId;
        }

        @Override
        public void run() {
            Log.d("ActivityWithRunnable", deviceId);
        }
    }


}
