package edu.cs423.mp5.tabs.snap;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import edu.cs423.mp5.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class SnapTab extends Activity {
    private SnapView theSnapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        theSnapView = new SnapView(this);

        setContentView(theSnapView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.snaptab_snap_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.snap_option:
                LocationManager theLocationService = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                Location myLocation = theLocationService
                        .getLastKnownLocation(theLocationService
                                .getBestProvider(new Criteria(), false));

                if (myLocation != null) {
                    Toast.makeText(this, "Taking Photo!", Toast.LENGTH_SHORT)
                            .show();

                    savePicture();
                } else {
                    Toast.makeText(this, "Location Not Available. Taking Pictures Is Disabled!",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startInputActivity(String aPictureFilepath) {
        if (aPictureFilepath != null) {
            Intent myIntent = new Intent(SnapTab.this, InputTab.class);
            myIntent.putExtra(getString(R.string.filepath_key),
                    aPictureFilepath);
            SnapTab.this.startActivity(myIntent);
        } else {
            Toast.makeText(getBaseContext(), "Invalid Filepath",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void savePicture() {
        Camera myCamera = theSnapView.getCamera();

        if (myCamera != null) {
            myCamera.takePicture(new ShutterCallback() {

                @Override
                public void onShutter() {

                }
            }, new PictureCallback() {

                @Override
                public void onPictureTaken(byte[] data, Camera aCamera) {

                }
            }, new PictureCallback() {

                @Override
                public void onPictureTaken(byte[] data, Camera aCamera) {
                    String theFilepath = null;
                    try {
                        String myStoragePath = Environment
                                .getExternalStorageDirectory()
                                .getAbsolutePath()
                                + "/" + getString(R.string.local_data);

                        theFilepath = String.format(myStoragePath + "/"
                                + "%d.jpg", System.currentTimeMillis());

                        FileOutputStream outStream = new FileOutputStream(
                                theFilepath);
                        outStream.write(data);
                        outStream.close();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(getBaseContext(),
                                "Error: FileNotFoundException",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (IOException e) {
                        Toast.makeText(getBaseContext(), "Error: IOException",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } finally {
                        startInputActivity(theFilepath);
                    }
                }
            });

        } else {
            Toast.makeText(this, "Camera Preview Not Available",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

class SnapView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;

    SnapView(Context context) {
        super(context);

        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open();
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            mCamera.release();
            mCamera = null;
        } catch (NullPointerException e) {
            Toast.makeText(getContext(), "Camera Not Available",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        Camera.Parameters mParameters = mCamera.getParameters();
        mParameters.setRotation(270);
        mCamera.setParameters(mParameters);
        mCamera.startPreview();
    }

    public Camera getCamera() {
        return mCamera;
    }
}
