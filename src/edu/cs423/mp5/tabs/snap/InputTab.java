package edu.cs423.mp5.tabs.snap;

import java.io.File;

import edu.cs423.mp5.R;
import edu.cs423.mp5.xmllib.ImageTagXMLObject;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class InputTab extends Activity implements saveXMLObject {
    private boolean theSavedStatus;
    private String theFilepath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        theSavedStatus = false;
        theFilepath = getIntent().getExtras().getString(
                getString(R.string.filepath_key));

        setContentView(R.layout.snap_input);
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
                theSavedStatus = true;
                if (finalizeSave()) {
                    finish();
                }
                
                return true;
            case R.id.back_snap_option:
                theSavedStatus = false;
                if (finalizeSave()) {
                    finish();
                }
                
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean finalizeSave() {
        if (theSavedStatus) {
            ImageTagXMLObject myAssociatedXMLObject = new ImageTagXMLObject(this);
            myAssociatedXMLObject.setPictureFilepath(theFilepath);
            myAssociatedXMLObject
                    .setTitle(((EditText) findViewById(R.id.title_entry))
                            .getText().toString());
            myAssociatedXMLObject
                    .setUser(((EditText) findViewById(R.id.user_entry))
                            .getText().toString());
            myAssociatedXMLObject
                    .setUsers(((EditText) findViewById(R.id.users_entry))
                            .getText().toString());

            return saveXMLObject(myAssociatedXMLObject);

        } else {
            new File(theFilepath).delete();
            Toast
                    .makeText(this, theFilepath + " Not Saved",
                            Toast.LENGTH_SHORT).show();

            return true;
        }
    }

    private boolean saveXMLObject(ImageTagXMLObject myAssociatedXMLObject) {
        if (myAssociatedXMLObject.finalizeObject()) {
            myAssociatedXMLObject.writeImageTagXMLObject(theFilepath + ".xml");
            Toast.makeText(this, theFilepath + " Saved", Toast.LENGTH_SHORT)
                    .show();

            return true;
        } else {
            Toast.makeText(this,
                    "Error Saving " + theFilepath + " (Complete All Fields)",
                    Toast.LENGTH_SHORT).show();

            return false;
        }
    }
}
