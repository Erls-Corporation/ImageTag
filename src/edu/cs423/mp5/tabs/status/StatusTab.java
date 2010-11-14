package edu.cs423.mp5.tabs.status;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class StatusTab extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Status tab");
        
        //this should be defined in layout/statustab.xml
        setContentView(textview);
    }
}
