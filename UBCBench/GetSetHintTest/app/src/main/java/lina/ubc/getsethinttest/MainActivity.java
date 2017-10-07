package lina.ubc.getsethinttest;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * This is a test case used to confirm that FD, IccTA, AD can't handle setHint() and getHint() not only for Buttons,
 * but also for other Android widgets. This is a supplementary test case for /Callbacks/Button5 from DroidBench
 * (https://github.com/secure-software-engineering/DroidBench/tree/master/eclipse-project/Callbacks/Button5).
 *
 * Expected Source: line : getDeviceId()
 * Expected Sink: line : Log.i(java.lang.String)
 * Number of expected flows: 1
 *
 * Flow Path:
 * call --> onCreate()
 * line 42: imei = tpm.getDeviceId() -->
 * click the EditText (R.id.et) for the FIRST time --> leakInfoViaHint(View view) -->
 * line 49 & 51: numOfClick++ & ((EditText)view).setHint(imei) -->
 * click the EditText (R.id.et) for the SECOND time --> leakInfoViaHint(View view) -->
 * line 46: if (numOfClick>0) -->
 * line 47: Log.i("DroidBench", ((EditText)view).getHint().toString()) --> leak
 */
public class MainActivity extends Activity {

    EditText et;
    String imei;
    int numOfClick=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText) findViewById(R.id.et);

        TelephonyManager tpm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        imei = tpm.getDeviceId();   // <android.telephony.TelephonyManager: java.lang.String getDeviceId()> -> _SOURCE_
    }

    public void leakInfoViaHint(View view) {
        if (numOfClick>0){
            Log.i("DroidBench", ((EditText)view).getHint().toString());  // <android.util.Log: int i(java.lang.String,java.lang.String)> -> _SINK_
        }
        numOfClick++;

        ((EditText)view).setHint(imei);
    }
}
