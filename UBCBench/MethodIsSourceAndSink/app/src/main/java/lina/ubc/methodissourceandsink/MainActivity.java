package lina.ubc.methodissourceandsink;

import android.app.Activity;
import android.os.Bundle;

/**
 * This is a test case used to show that in the full source and sinks list that DroidSafe generates for FlowDroid, there are cases when DroidSafe
 * will consider the same method as source and sink at the same time, which is not right.
 *
 * "<java.lang.Runtime: java.lang.Process exec(java.lang.String[])>" would be a good example for explaining this.
 *
 * There should not be any flow in this test case.
 */
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
