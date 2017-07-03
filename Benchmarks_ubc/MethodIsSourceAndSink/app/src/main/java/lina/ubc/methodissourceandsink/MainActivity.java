package lina.ubc.methodissourceandsink;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // <java.lang.Runtime: java.lang.Process exec(java.lang.String[])> -> _SINK_/_SOURCE_
            Process proc = Runtime.getRuntime().exec(new String[] { "su", "-c", "reboot", "-p" });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
