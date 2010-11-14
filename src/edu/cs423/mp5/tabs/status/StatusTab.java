package edu.cs423.mp5.tabs.status;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.widget.TextView;
import android.widget.Toast;

public class StatusTab extends Activity {
    private LocationManager theLocationService;
    
    @Override
    public void onResume() {
        super.onResume();
        
        theLocationService = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Location myLocation = theLocationService
                .getLastKnownLocation(theLocationService.getBestProvider(
                        new Criteria(), false));

        if (myLocation != null) {

            TextView textview = new TextView(this);
            textview.setText("This is the Status tab ... "
                    + myLocation.getLatitude() + ", "
                    + myLocation.getLongitude());
            
         // this should be defined in layout/statustab.xml
            setContentView(textview);
        } else {
            Toast.makeText(this, "Location Not Available", Toast.LENGTH_SHORT).show();
        }
    }
}
