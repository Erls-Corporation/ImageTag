package edu.cs423.mp5.tabs.view;

import edu.cs423.mp5.R;
import edu.cs423.mp5.xmllib.ImageTagXMLObject;
import android.app.Activity;
import android.os.Bundle;

public class ViewTab extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view);
        
        ImageTagXMLObject.readImageTagXMLObject("/sdcard/test.xml");
    }
}
