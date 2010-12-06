package edu.cs423.mp5.tabs.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import edu.cs423.mp5.R;

public class ViewInputTab extends Activity {
    private boolean theFilterStatus;
    private String theFilterName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        theFilterStatus = false;
        theFilterName = null;

        setContentView(R.layout.view_input);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.viewtab_input_menu, menu);
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        
        String filterText;
        if (theFilterStatus) {
        	filterText = "filtering on "+theFilterName;
        } else {
        	filterText = "current filter: " + ViewTab.getFilter();
        }
        Toast.makeText(this, filterText, Toast.LENGTH_SHORT).show();
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_filter_option:
                theFilterName = ((EditText) findViewById(R.id.title_entry)).getText().toString().trim();
                ViewTab.setFilter(theFilterName);
                if (theFilterName.length() == 0) {
                	theFilterStatus = false;
                } else {
                	theFilterStatus = true;
                }              
                finish();

                return true;
            case R.id.back_filter_option:
                theFilterStatus = false;
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
