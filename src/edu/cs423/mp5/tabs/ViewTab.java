package edu.cs423.mp5.tabs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewTab extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the View tab");
        setContentView(textview);
    }
}
