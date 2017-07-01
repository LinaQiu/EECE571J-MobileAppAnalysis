package lina.ubc.sensitivitytestallinone;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class AmandroidSourceSinkTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amandroid_source_sink_test);

        TelephonyManager tpm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String networkTypeName = tpm.getSimOperator();

        Log.e("Amandroid test",networkTypeName);
    }
}
