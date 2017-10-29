package lina.ubc.uncalledactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class UncalledActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uncalled);

        Intent receivedIntent = getIntent();
        Log.d("UncalledActivity", receivedIntent.getStringExtra("IMEI2"));
    }

}
