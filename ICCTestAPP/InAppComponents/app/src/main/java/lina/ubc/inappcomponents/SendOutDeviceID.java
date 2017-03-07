package lina.ubc.inappcomponents;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SendOutDeviceID extends Service {

    public SendOutDeviceID() {
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent!=null) {
            String deviceID = intent.getStringExtra("DEVICE_ID");
            Log.e("Leakage","received device id: "+deviceID);
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
