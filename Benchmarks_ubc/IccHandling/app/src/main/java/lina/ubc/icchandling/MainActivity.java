package lina.ubc.icchandling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * This is a test case used to explain the ICC handling issue of Flowdroid.
 * Expected source: none
 * Expected sink: MainActivity (line 21): sendBroadcast(android.content.Intent) && Receiver (line 22): Log.i(java.lang.String, java.lang.String)
 * Number of expected leaks: 0
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(Intent.ACTION_SEND);
        sendBroadcast(intent);
    }

}
