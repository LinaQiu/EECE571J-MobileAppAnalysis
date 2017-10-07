package lina.ubc.componentnotinmanifesttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by lina on 2017-07-10.
 */

public class InFlowActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        Log.d("ComponentManifest4", intent.getStringExtra("ComponentNotInManifestTest"));  // Sink: <android.util.Log: int d(java.lang.String,java.lang.String)> -> _SINK_
    }
}
