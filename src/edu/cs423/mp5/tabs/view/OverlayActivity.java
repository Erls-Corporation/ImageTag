package edu.cs423.mp5.tabs.view;

import edu.cs423.mp5.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class OverlayActivity extends Activity {
    
    private String thePictureFilepath;
    private String thePreviewPictureFilepath;
    private String theTitle;
    private String theUser;
    private String theUsers;
    private String theTime;
    private Double theLatitude;
    private Double theLongitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        thePictureFilepath = getBundleString(R.string.picture_filepath_key);
        thePreviewPictureFilepath = getBundleString(R.string.preview_picture_filepath_key);
        theTitle = getBundleString(R.string.title_key);
        theUser = getBundleString(R.string.user_key);
        theUsers = getBundleString(R.string.users_key);
        theTime = getBundleString(R.string.time_key);
        theLatitude = getBundleDouble(R.string.latitude_key);
        theLongitude = getBundleDouble(R.string.longitude_key);
        
        StringBuilder output = new StringBuilder();
        output.append("Picture Filepath: " + thePictureFilepath + "\n");
        output.append("Preview Picture Filepath: " + thePreviewPictureFilepath + "\n");
        output.append("Title: " + theTitle + "\n");
        output.append("User: " + theUser + "\n");
        output.append("Users: " + theUsers + "\n");
        output.append("Time: " + theTime + "\n");
        output.append("Latitude: " + theLatitude + "\n");
        output.append("Longitude: " + theLongitude);
        
        TextView tv = new TextView(this);
        tv.setText(output.toString());
        setContentView(tv);
    }
    
    private String getBundleString(int key) {
        return getIntent().getExtras().getString(getString(key));
    }
    
    private Double getBundleDouble(int key) {
        return getIntent().getExtras().getDouble(getString(key));
    }
}
