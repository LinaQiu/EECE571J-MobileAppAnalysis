package lina.ubc.uncalledactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class CalledActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_called);

        Intent receivedIntent = getIntent();
        Log.d("CalledActivity", receivedIntent.getStringExtra("IMEI1"));
    }

}
