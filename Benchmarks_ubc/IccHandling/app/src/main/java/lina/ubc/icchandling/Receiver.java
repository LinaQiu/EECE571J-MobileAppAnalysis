package lina.ubc.icchandling;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Due to unsound ICC handling, FlowDroid considers callbacks' parameters as sources.
 * Therefore, FlowDroid reports a false positive flow as show below:
 * False Positive Flow:
 * line 21: onReceive(Context context, Intent intent) -->
 * line 22: Log.i("Receiver", intent.getAction()) --> false positive leak
 */
public class Receiver extends BroadcastReceiver {

    public Receiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Receiver", intent.getAction());
    }
}
