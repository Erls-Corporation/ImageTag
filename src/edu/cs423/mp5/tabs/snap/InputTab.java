package edu.cs423.mp5.tabs.snap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import edu.cs423.mp5.R;
import edu.cs423.mp5.xmllib.ImageTagXMLObject;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class InputTab extends Activity {
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
    public void finish() {
        super.finish();

        if (theSavedStatus) {
            Toast.makeText(this, theFilepath + " Saved", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast
                    .makeText(this, theFilepath + " Not Saved",
                            Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_snap_option:
                theSavedStatus = true;
                if (tryToSave()) {
                    finish();
                }

                return true;
            case R.id.back_snap_option:
                theSavedStatus = false;
                delete();
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void delete() {
        new File(theFilepath).delete();
    }

    private boolean tryToSave() {
        Toast.makeText(this, "Creating Preview...", Toast.LENGTH_SHORT).show();
        
        if (tryToCreatePreview()) {
            ImageTagXMLObject myAssociatedXMLObject = new ImageTagXMLObject(
                    this);
            myAssociatedXMLObject.setPictureFilepath(theFilepath);
            myAssociatedXMLObject.setPreviewPicturePath("." + theFilepath);
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
        }
        return false;
    }

    private boolean tryToCreatePreview() {
        Bitmap myBitmap = BitmapFactory.decodeFile(theFilepath);
        int width = myBitmap.getWidth();
        int height = myBitmap.getHeight();
        int newWidth = 60;
        int newHeight = 80;
        
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
       
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        matrix.postRotate(90);
        Bitmap resizedBitmap = Bitmap.createBitmap(myBitmap, 0, 0,
                          width, height, matrix, true);
        FileOutputStream out;
        try {
            out = new FileOutputStream("." + theFilepath);
            resizedBitmap.compress(CompressFormat.JPEG, 90, out);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    private boolean saveXMLObject(ImageTagXMLObject myAssociatedXMLObject) {
        if (myAssociatedXMLObject.finalizeObject()) {
            myAssociatedXMLObject.writeImageTagXMLObject(theFilepath + ".xml");

            return true;
        } else {
            Toast.makeText(this,
                    "Error Saving " + theFilepath + " (Complete All Fields)",
                    Toast.LENGTH_SHORT).show();

            return false;
        }
    }
}
