package lina.ubc.sensitivitytestallinone;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * If path insensitive, the tool would report a flow from the source to the sink.
 * If path sensitive, the tool would be able to record the conditions for each flow, then there would not be a flow from the source to the sink in this case.
 */
public class PathSensitivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_sensitivity);

        TelephonyManager tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String deviceId = "";

        double condition = Math.random();

        if (condition>0) {
            deviceId=tpm.getDeviceId();     // source
        }

        if (!(condition<0)){
            Log.i("PathSensitivity", deviceId);     // sink
        }
    }
}
