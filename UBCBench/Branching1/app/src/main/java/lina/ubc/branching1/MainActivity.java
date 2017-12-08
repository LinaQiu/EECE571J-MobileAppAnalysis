package lina.ubc.branching1;

import android.app.Activity;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;

/**
 * This is a test case used to replicate the branching issue that we found in FDroid app: 8. Multimedia: 4K_org.bitbucket.tickytacky.mirrormirror_4.apk
 * We extacted the relevant sources and sinks, simplified the FDroid multimedia app, and then built our own UBCBench app, branching1.
 * Expected sources: getParameters()
 * Expected sink: setScale(float,float)
 * Number of expected leaks: 2
 * Flow Paths:
 * Path1:
 * line 43: Camera.Parameters parameters = mCamera.getParameters() -->
 * line 45: Camera.Size previewSize = parameters.getPreviewSize() -->
 * line 47: ratioPreview = (double)previewSize.height / previewSize.width -->
 * line 51: scaleY = (float)(constNum / ratioPreview) -->
 * line 56: matrix.setScale(scaleX, scaleY) --> leak1
 *
 * Path2:
 * line 43: Camera.Parameters parameters = mCamera.getParameters() -->
 * line 45: Camera.Size previewSize = parameters.getPreviewSize() -->
 * line 47: ratioPreview = (double)previewSize.height / previewSize.width -->
 * line 53: scaleX = (float)(ratioPreview / randomNum) -->
 * line 56: matrix.setScale(scaleX, scaleY) --> leak2
 *
 */
public class MainActivity extends Activity {
    private Camera mCamera;
    private float randomNum;
    private float scaleX;
    private float scaleY;
    private double ratioPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCamera = Camera.open(1);

        Camera.Parameters parameters = mCamera.getParameters(); // Source: <android.hardware.Camera: android.hardware.Camera$Parameters getParameters()> -> _SOURCE_

        Camera.Size previewSize = parameters.getPreviewSize();

        ratioPreview = (double)previewSize.height / previewSize.width;

        randomNum = (float) Math.random()*2;
        if (randomNum > ratioPreview) {
            scaleY = (float)(randomNum / ratioPreview);
        } else {
            scaleX = (float)(ratioPreview / randomNum);
        }
        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, scaleY);    // Sink: <android.graphics.Matrix: void setScale(float,float)> -> _SINK_

    }
}
