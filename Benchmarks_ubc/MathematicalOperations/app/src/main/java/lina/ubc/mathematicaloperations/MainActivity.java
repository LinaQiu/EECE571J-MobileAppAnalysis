package lina.ubc.mathematicaloperations;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * This is a test case used to explain that Amandroid is not able to handle flows that are related to mathematical operations.
 * Expected source: getDeviceId()
 * Expected sink: line 45 & 48 & 51 & 54: Log.d(java.lang.String, java.lang.String)
 * Number of expected leaks: 4
 * Flow Paths:
 * Path1:
 * line 42: String devcieId = tpm.getDeviceId() -->
 * line 44: double deviceIdPlus1 = Double.parseDouble(deviceId)+1.0 -->
 * line 45: Log.d("MathematicalOperations1", "The value of deviceId plus 1.0: "+deviceIdPlus1) --> leak
 *
 * Path2:
 * line 42: String devcieId = tpm.getDeviceId() -->
 * line 47: double deviceIdMinus1 = Double.parseDouble(deviceId)-1.0 -->
 * line 48: Log.d("MathematicalOperations2", "The value of deviceId minus 1.0: "+deviceIdMinus1) --> leak
 *
 * Path3:
 * line 42: String devcieId = tpm.getDeviceId() -->
 * line 50: double deviceIdMultiplyBy1 = Double.parseDouble(deviceId)*1.0 -->
 * line 51: Log.d("MathematicalOperations3", "The value of deviceId multiply by 1.0: "+deviceIdMultiplyBy1) --> leak
 *
 * Path4:
 * line 42: String devcieId = tpm.getDeviceId() -->
 * line 53: double deviceIdDividedBy1 = Double.parseDouble(deviceId)/1.0 -->
 * line 54: Log.d("MathematicalOperation4", "The value of deviceId divided by 1.0: "+deviceIdDividedBy1) --> leak
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String deviceId = tpm.getDeviceId();

        double deviceIdPlus1 = Double.parseDouble(deviceId)+1.0;
        Log.d("MathematicalOperations1", "The value of deviceId plus 1.0: "+deviceIdPlus1);

        double deviceIdMinus1 = Double.parseDouble(deviceId)-1.0;
        Log.d("MathematicalOperations2", "The value of deviceId minus 1.0: "+deviceIdMinus1);

        double deviceIdMultiplyBy1 = Double.parseDouble(deviceId)*1.0;
        Log.d("MathematicalOperations3", "The value of deviceId multiply by 1.0: "+deviceIdMultiplyBy1);

        double deviceIdDividedBy1 = Double.parseDouble(deviceId)/1.0;
        Log.d("MathematicalOperation4", "The value of deviceId divided by 1.0: "+deviceIdDividedBy1);
    }
}
