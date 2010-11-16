package edu.cs423.mp5;

import java.io.File;

import edu.cs423.mp5.tabs.snap.SnapTab;
import edu.cs423.mp5.tabs.status.StatusTab;
import edu.cs423.mp5.tabs.view.ViewTab;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/* this is a test comment from john */

public class Main extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        setUpDirectories();
        setUpTabs();
    }

    private void setUpDirectories() {
        String myStoragePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                + "/" + getString(R.string.local_data);
        File myFileLocation = new File(myStoragePath);
        if (!myFileLocation.exists()) {
            myFileLocation.mkdirs();
        }
    }

    private void setUpTabs() {
        TabHost myTabHost = getTabHost();
        Resources res = getResources();
        Intent myTabIntent = null;
        TabSpec mySpec = null;

        myTabIntent = new Intent().setClass(this, ViewTab.class);
        mySpec = myTabHost.newTabSpec("view").setIndicator("View",
                res.getDrawable(R.drawable.ic_menu_mapmode)).setContent(
                myTabIntent);
        myTabHost.addTab(mySpec);

        myTabIntent = new Intent().setClass(this, SnapTab.class);
        mySpec = myTabHost.newTabSpec("snap").setIndicator("Snap",
                res.getDrawable(R.drawable.ic_menu_view)).setContent(
                myTabIntent);
        myTabHost.addTab(mySpec);

        myTabIntent = new Intent().setClass(this, StatusTab.class);
        mySpec = myTabHost.newTabSpec("status").setIndicator("Status",
                res.getDrawable(R.drawable.ic_menu_compass)).setContent(
                myTabIntent);
        myTabHost.addTab(mySpec);

        myTabHost.setCurrentTab(0);
    }
}
