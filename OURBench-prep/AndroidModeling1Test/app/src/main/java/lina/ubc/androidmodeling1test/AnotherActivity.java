package lina.ubc.androidmodeling1test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AnotherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        Intent receivedIntent = getIntent();

        Log.i("ApplicationModeling1", receivedIntent.getStringExtra("IMEI1"));  // Sink1, Leak: <android.util.Log: int i(java.lang.String,java.lang.String)> -> _SINK_
        Log.i("ApplicationModeling2", receivedIntent.getStringExtra("IMEI2"));  // Sink2, Leak: <android.util.Log: int i(java.lang.String,java.lang.String)> -> _SINK_

        Log.i("ApplicationModeling3", ((MyApplication)getApplication()).imei);  // Sink3, Leak: <android.util.Log: int i(java.lang.String,java.lang.String)> -> _SINK_
    }
}
