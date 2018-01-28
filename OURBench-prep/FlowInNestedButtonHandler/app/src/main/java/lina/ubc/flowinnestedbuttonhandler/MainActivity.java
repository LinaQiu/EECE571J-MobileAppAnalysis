package lina.ubc.flowinnestedbuttonhandler;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 * This is a test case used to show that Amandroid will miss flow in nested button handler, based on our findings from DroidBench app: Button3
 * Expected source: line 23 (Button2Listener): getDeviceId()
 * Expected sink: line 25 (Button2Listener): Log.i(java.lang.String, java.lang.String)
 * Number of expected flow: 1
 *
 * Flow Path:
 * Path1:
 * line 23: act.imei = telephonyManager.getDeviceId() -->
 * line 25: Log.i("TAG", "Button2 - IMEI: " + act.imei) --> leak
 */
public class MainActivity extends Activity {

    String imei = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new Button1Listener(this));
    }

}