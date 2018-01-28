package com.ourbench.icclogic5;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class AnotherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_flow);

        Log.d("IMEI", getIntent().getStringExtra("imei"));  // Sink2: <android.util.Log: int d(java.lang.String,java.lang.String)> -> _SINK_, leak
    }
}
