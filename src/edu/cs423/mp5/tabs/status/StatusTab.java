package edu.cs423.mp5.tabs.status;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class StatusTab extends Activity {
    private LocationManager theLocationService;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        theLocationService = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Location myLocation = theLocationService
                .getLastKnownLocation(theLocationService.getBestProvider(
                        new Criteria(), false));

        TextView textview = new TextView(this);
        textview.setText("This is the Status tab ... "
                + myLocation.getLongitude() + ", " + myLocation.getLatitude());

        // this should be defined in layout/statustab.xml
        setContentView(textview);
    }
}
