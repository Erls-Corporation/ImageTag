package edu.cs423.mp5.tabs.view;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import edu.cs423.mp5.R;
import edu.cs423.mp5.xmllib.ImageTagXMLObject;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;

public class ViewTab extends MapActivity {

    private MapView theMapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view);

        theMapView = (MapView) findViewById(R.id.mapview);
        theMapView.setBuiltInZoomControls(true);

        scanAndAddOverlay();
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

            for (File myXMLObject : myXMLObjects) {
                addOverlay(myXMLObject);
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
            MapOverlay overlay = new MapOverlay(Drawable
                    .createFromPath(myObject.getPreviewPicturePath()), this);

            overlay.addOverlay(new OverlayItem(new GeoPoint((int) (myObject
                    .getLatitude() * 1E6),
                    (int) (myObject.getLongitude() * 1E6)),
                    myObject.getTitle(), myObject.getTime()));
            theMapView.getOverlays().add(overlay);
        }
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}

class MapOverlay extends ItemizedOverlay<OverlayItem> {

    private List<OverlayItem> overlays = new ArrayList<OverlayItem>();
    private Context context;

    public MapOverlay(Drawable defaultMarker, Context context) {
        super(boundCenterBottom(defaultMarker));
        overlays = new ArrayList<OverlayItem>();
        this.context = context;
    }

    @Override
    protected OverlayItem createItem(int i) {
        return overlays.get(i);
    }

    @Override
    public int size() {
        return overlays.size();
    }

    public void addOverlay(OverlayItem overlay) {
        overlays.add(overlay);
        this.populate();
    }

    @Override
    protected boolean onTap(int index) {
        OverlayItem item = overlays.get(index);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.context);
        dialog.setTitle(item.getTitle());
        dialog.setMessage(item.getSnippet());
        dialog.show();
        return true;
    }
}
