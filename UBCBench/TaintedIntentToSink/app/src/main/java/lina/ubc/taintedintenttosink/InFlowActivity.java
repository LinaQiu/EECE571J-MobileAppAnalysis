package lina.ubc.taintedintenttosink;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class InFlowActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("ComponentManifest4", "Nothing");  // Sink: <android.util.Log: int d(java.lang.String,java.lang.String)> -> _SINK_
    }
}

