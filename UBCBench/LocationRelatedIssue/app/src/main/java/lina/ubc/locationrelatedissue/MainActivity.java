package lina.ubc.locationrelatedissue;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * This app is used to explain that Amandroid has LocationRelatedIssue (see our issue description).
 * Expected source: getLatitude() && getLongitude()
 * Expected sink: Log.i(java.lang.String, java.lang.String)
 * Number of expected leaks: 2
 * Flow Paths:
 * Path1:
 * line 34: lat = location.getLatitude() -->
 * line 37: Log.i("LOG", "Latitude: " + lat + "  Longtitude: " + lon) --> leak
 *
 * Path2:
 * line 35: lat = location.getLongitude() -->
 * line 37: Log.i("LOG", "Latitude: " + lat + "  Longtitude: " + lon) --> leak
 */
public class MainActivity extends Activity {

    private LocationManager locationManager;

    LocationListener locationListener = new LocationListener() {
        double lat;
        double lon;

        @Override
        public void onLocationChanged(Location location) {
            lat = location.getLatitude();   // Source1:  <android.location.Location: double getLatitude()> -> _SOURCE_
            lon = location.getLongitude();  // Source2:  <android.location.Location: double getLongitude()> -> _SOURCE_

            Log.i("LOG", "Latitude: " + lat + "  Longtitude: " + lon); // Sink: <android.util.Log: int i(java.lang.String,java.lang.String)> -> _SINK_
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }
}
