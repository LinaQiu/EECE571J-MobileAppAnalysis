package lina.ubc.doubletagging;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

/**
 * This is a test case used to explain the nesting source issue of FlowDroid and DroidSafe.
 * Expected sources: getParameters() && getSupportedPreviewSizes()
 * Expected sinks: Log.i(java.lang.String, java.lang.String)
 * Number of expected leaks: 2
 * Flow Paths:
 * Path1:
 * line 35: Camera.Paremeters parameters = camera.getParameters() -->
 * line 36: List<Camera.Size> cameraSize = parameters.getSupportedPreviewSizes() -->
 * line 38: Log.i("NestingSource", "Camera info.: "+ cameraSize) --> leak
 *
 * Path2:
 * line 36: List<Camera.Size> cameraSize = parameters.getSupportedPreviewSizes() -->
 * line 38: Log.i("NestingSource", "Camera info.: "+cameraSize) --> leak
 */
public class MainActivity extends Activity {

    private static Camera camera = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera.open();
        Camera.Parameters parameters = camera.getParameters();  // Source1: <android.hardware.Camera: android.hardware.Camera$Parameters getParameters()> -> _SOURCE_
        List<Camera.Size> cameraSize = parameters.getSupportedPreviewSizes();   // Source2: <android.hardware.Camera$Parameters: java.util.List getSupportedPreviewSizes()> -> _SOURCE_

        Log.i("NestingSource", "Camera info.: "+cameraSize);    // Sink: <android.util.Log: int i(java.lang.String,java.lang.String)> -> _SINK_

    }

}
