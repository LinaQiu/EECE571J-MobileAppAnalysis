package lina.ubc.branching;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * This is a test case used to explain the branching issue of FlowDroid and IccTA.
 * Expected source: getLatitude()
 * Expected sink: Log.i(java.lang.String, java.lang.String)
 * Number of expected leaks: 2
 * Flow Paths:
 * Path1:
 * line 50: lat = location.getLatitude() -->
 * line 52: double lat_constant = 0.0 -->
 * line 56: lat_constant = lat/constant -->
 * line 61: Log.i("Branching test", "Location data: "+lat_constant+", "+constant_lat) --> leak
 *
 * Path2:
 * line 50: lat = location.getLatitude() -->
 * line 53: double constant_lat = 0.0 -->
 * line 58: constant_lat = constant/lat -->
 * line 61: Log.i("Branching test", "Location data: "+lat_constant+", "+constant_lat) --> leak
 */
public class MainActivity extends Activity {

    private static Context mContext;
    private LocationManager locationManager;
    private static double lat;
    private static double constant = 1.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lat = location.getLatitude();   // Source:  <android.location.Location: double getLatitude()> -> _SOURCE_

            double lat_constant = 0.0;
            double constant_lat = 0.0;

            if (lat>50){
                lat_constant = lat/constant;
            }else {
                constant_lat = constant/lat;
            }

            Log.i("Branching test", "Location data: "+lat_constant+", "+constant_lat);  // Sink: <android.util.Log: int i(java.lang.String,java.lang.String)> -> _SINK_
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
}
