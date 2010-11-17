package edu.cs423.mp5.tabs.view;

import com.google.android.maps.MapActivity;

import edu.cs423.mp5.R;
import android.os.Bundle;

public class ViewTab extends MapActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view);
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}
