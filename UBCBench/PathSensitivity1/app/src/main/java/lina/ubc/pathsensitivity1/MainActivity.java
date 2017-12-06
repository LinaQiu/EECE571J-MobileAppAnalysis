package lina.ubc.pathsensitivity1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * This is a test case used to explain that Amandroid will report false positives related to path sensitivity.
 * This happens in FDroid apps: 1. System and 9. Navigation.
 * Expected source: getString()
 * Expected sink: startActivity(Intent intent)
 * Number of expected flow: 0
 *
 * FP that Amandroid reports:
 * line 33: Toast.makeText(this, getString(R.string.app_name), Toast.LENGTH_LONG).show() -->
 * line 31: startActivity(Intent.createChooser(i, "read"));
 *
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        double randomNum = Math.random();

        Intent i = new Intent();
        if(randomNum>0.5) {
            startActivity(Intent.createChooser(i, "read"));
        }else{
            Toast.makeText(this, getString(R.string.app_name), Toast.LENGTH_LONG).show();
        }
    }
}
