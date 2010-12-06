package edu.cs423.mp5.tabs.view;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import edu.cs423.mp5.R;
import edu.cs423.mp5.xmllib.ImageTagXMLObject;

public class ViewTab extends MapActivity {

    private MapView theMapView;
    private static String netIdFilter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view);

        theMapView = (MapView) findViewById(R.id.mapview);
        theMapView.setBuiltInZoomControls(true);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.viewtab_filter_menu, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_option:
                Intent myIntent = new Intent(ViewTab.this, ViewInputTab.class);
                ViewTab.this.startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        theMapView.getOverlays().clear();

        scanAndAddOverlay();
    }
    
    static void setFilter(String filterName) {
    	netIdFilter = filterName;
    }
    
    static String getFilter() {
    	if (netIdFilter != null && netIdFilter.length() > 0) {
    	    return netIdFilter;
    	} else {
    		return "<none>";
    	}
    }

    private static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    private void scanAndAddOverlay() {
        String myStoragePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                + "/" + getString(R.string.local_data);

        File myXMLDirectory = new File(myStoragePath);

        if (myXMLDirectory.isDirectory()) {
            File[] myXMLObjects = myXMLDirectory.listFiles(new FileFilter() {

                @Override
                public boolean accept(File file) {
                    return getExtension(file).equalsIgnoreCase("XML");
                }
            });

            if (netIdFilter == null || netIdFilter.trim().length() == 0) {
            	for (File myXMLObject : myXMLObjects) {
                    addOverlay(myXMLObject);
                }
            } else {
            	for (File myXMLObject : myXMLObjects) {
                    addOverlay(myXMLObject, netIdFilter.toLowerCase().trim());
                }
            }
        } else {
            throw new IllegalStateException(myStoragePath
                    + " is not a directory");
        }
    }

    private void addOverlay(File aXMLTag) {
        ImageTagXMLObject myObject = ImageTagXMLObject.readImageTagXMLObject(
                this, aXMLTag.getAbsolutePath());

        if (myObject != null) {
        	createAndAddOverlay(myObject);
        }
    }
    
    private void addOverlay(File aXMLTag, String filter) {
        ImageTagXMLObject myObject = ImageTagXMLObject.readImageTagXMLObject(
                this, aXMLTag.getAbsolutePath());

        if (myObject != null  && (myObject.getUser().toLowerCase().contains(filter) ||
        		myObject.getUsers().toLowerCase().contains(filter))) {
            createAndAddOverlay(myObject);
        }
    }
    
    private void createAndAddOverlay(ImageTagXMLObject myObject) {
        MapOverlay overlay = new MapOverlay(Drawable
                .createFromPath(myObject.getPreviewPicturePath()), this);

        overlay.addOverlay(new MapOverlayItem(myObject, new GeoPoint(
                (int) (myObject.getLatitude() * 1E6), (int) (myObject
                        .getLongitude() * 1E6)), myObject.getTitle(),
                "Author: " + myObject.getUser()));
        theMapView.getOverlays().add(overlay);
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}

class MapOverlayItem extends OverlayItem {
    private ImageTagXMLObject theXMLObject;

    public MapOverlayItem(ImageTagXMLObject aXMLObject, GeoPoint aGeoPoint,
            String aTitle, String aSnippet) {
        super(aGeoPoint, aTitle, aSnippet);

        theXMLObject = aXMLObject;
    }

    public ImageTagXMLObject getXMLObject() {
        return theXMLObject;
    }
}

class MapOverlay extends ItemizedOverlay<MapOverlayItem> {

    private List<MapOverlayItem> overlays;
    private Context context;

    public MapOverlay(Drawable defaultMarker, Context context) {
        super(boundCenterBottom(defaultMarker));
        overlays = new ArrayList<MapOverlayItem>();
        this.context = context;
    }

    @Override
    protected MapOverlayItem createItem(int i) {
        return overlays.get(i);
    }

    @Override
    public int size() {
        return overlays.size();
    }

    public void addOverlay(MapOverlayItem overlay) {
        overlays.add(overlay);
        this.populate();
    }

    @Override
    protected boolean onTap(int index) {
        MapOverlayItem item = overlays.get(index);
        ImageTagXMLObject myObject = item.getXMLObject();

        String thePictureFilepath = myObject.getPictureFilepath();
        String thePreviewPictureFilepath = myObject.getPreviewPicturePath();
        String theTitle = myObject.getTitle();
        String theUser = myObject.getUser();
        String theUsers = myObject.getUsers();
        String theTime = myObject.getTime();
        Double theLatitude = myObject.getLatitude();
        Double theLongitude = myObject.getLongitude();

        Intent myIntent = new Intent(this.context, OverlayActivity.class);
        myIntent.putExtra(context.getString(R.string.picture_filepath_key),
                thePictureFilepath);
        myIntent.putExtra(context
                .getString(R.string.preview_picture_filepath_key),
                thePreviewPictureFilepath);
        myIntent.putExtra(context.getString(R.string.title_key), theTitle);
        myIntent.putExtra(context.getString(R.string.user_key), theUser);
        myIntent.putExtra(context.getString(R.string.users_key), theUsers);
        myIntent.putExtra(context.getString(R.string.time_key), theTime);
        myIntent.putExtra(context.getString(R.string.latitude_key), theLatitude);
        myIntent.putExtra(context.getString(R.string.longitude_key), theLongitude);

        this.context.startActivity(myIntent);

        return true;
    }
}
