package lina.ubc.multientryissue1;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * This is a test case used to explain DroidSafe's multi entry issue.
 * To be more specific, DroidSafe occasionally reported the same flow twice, while one flow was reported with source's entry point, and another flow was reported with sink's entry point.
 * Expected Sources: getDeviceId()
 * Expected Sinks: Log.i(java.lang.String, java.lang.String)
 * Number of expected flows: 1
 * Path:
 * line 27: new MyThread(tpm.getDeviceId()).start -->
 * line 35: this.deviceId = deviceId -->
 * line 40: Log.i("ActivityWithThread", deviceId) --> leak
 */
public class ActivityWithThread extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_thread);

        TelephonyManager tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        new MyThread(tpm.getDeviceId()).start();
    }

    private class MyThread extends Thread {

        private final String deviceId;

        public MyThread(String deviceId) {
            this.deviceId = deviceId;
        }

        @Override
        public void run() {
            Log.i("ActivityWithThread", deviceId);
        }
    }
}
