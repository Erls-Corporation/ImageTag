package edu.cs423.mp5.tabs.snap;

import edu.cs423.mp5.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class InputTab extends Activity {
    private boolean theSavedStatus;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        theSavedStatus = false;

        setContentView(R.layout.snap_input);
    }
    
    @Override
    public void finish() {
        super.finish();
        
        if (theSavedStatus) {
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.snaptab_input_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_snap_option:
                save();
                theSavedStatus = true;
                finish();
                
                return true;
            case R.id.back_snap_option:
                theSavedStatus = false;
                finish();
                
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private void save() {
        
    }
}
