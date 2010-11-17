package edu.cs423.mp5.xmllib;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

public class ImageTagXMLObject {
    public boolean isMutable;
    private final Context theContext;
    private String thePictureFilepath;
    private String theTitle;
    private String theUser;
    private String theUsers;
    private String theTime;
    private String theLatitude;
    private String theLongitude;

    public ImageTagXMLObject(Context aContext) {
        isMutable = true;

        theContext = aContext;
        thePictureFilepath = null;
        theTitle = null;
        theUser = null;
        theUsers = null;
        theTime = null;
        theLatitude = null;
        theLongitude = null;
    }

    public ImageTagXMLObject() {
        isMutable = true;

        theContext = null;
        thePictureFilepath = null;
        theTitle = null;
        theUser = null;
        theUsers = null;
        theTime = null;
        theLatitude = null;
        theLongitude = null;
    }

    public boolean finalizeObject() {
        if (checkNullField() && checkEmptyField()) {
            isMutable = false;
            return true;
        } else {
            return false;
        }
    }

    private boolean checkNullField() {
        return thePictureFilepath != null && theTitle != null
                && theUser != null && theUsers != null;
    }

    private boolean checkEmptyField() {
        return (!thePictureFilepath.equals("")) && (!theTitle.equals(""))
                && (!theUser.equals("")) && (!theUsers.equals(""));
    }

    public String getPictureFilepath() {
        return thePictureFilepath;
    }

    public void setPictureFilepath(String aPictureFilepath) {
        if (isMutable) {
            thePictureFilepath = aPictureFilepath;
        } else {
            throw new UnsupportedOperationException(
                    "Cannot Modify Immutable Object");
        }
    }

    public String getTitle() {
        return theTitle;
    }

    public void setTitle(String aTitle) {
        if (isMutable) {
            theTitle = aTitle;
        } else {
            throw new UnsupportedOperationException(
                    "Cannot Modify Immutable Object");
        }
    }

    public String getUser() {
        return theUser;
    }

    public void setUser(String aUser) {
        if (isMutable) {
            theUser = aUser;
        } else {
            throw new UnsupportedOperationException(
                    "Cannot Modify Immutable Object");
        }
    }

    public String getUsers() {
        return theUsers;
    }

    public void setUsers(String aUsers) {
        if (isMutable) {
            theUsers = aUsers;
        } else {
            throw new UnsupportedOperationException(
                    "Cannot Modify Immutable Object");
        }
    }

    public String getTime() {
        return theTime;
    }

    public void setTime(String aTime) {
        if (isMutable) {
            theTime = aTime;
        } else {
            throw new UnsupportedOperationException(
                    "Cannot Modify Immutable Object");
        }
    }

    public String getLatitude() {
        return theLatitude;
    }

    public void setLatitude(String aLatitude) {
        if (isMutable) {
            theLatitude = aLatitude;
        } else {
            throw new UnsupportedOperationException(
                    "Cannot Modify Immutable Object");
        }
    }

    public String getLongitude() {
        return theLongitude;
    }

    public void setLongitude(String aLongitude) {
        if (isMutable) {
            theLongitude = aLongitude;
        } else {
            throw new UnsupportedOperationException(
                    "Cannot Modify Immutable Object");
        }
    }

    public boolean writeImageTagXMLObject(String aFilepath) {
        if (isMutable) {
            throw new UnsupportedOperationException(
                    "Cannot Write Out Mutable Object");
        } else {
            try {
                FileWriter myFileWriter = new FileWriter(aFilepath);
                BufferedWriter myOutputStream = new BufferedWriter(myFileWriter);

                StringBuilder myXMLOutput = new StringBuilder();

                myXMLOutput.append(getXMLHeader());
                myXMLOutput.append(getXMLPictureFilepath());
                myXMLOutput.append(getXMLTitle());
                myXMLOutput.append(getXMLUser());
                myXMLOutput.append(getXMLUsers());
                myXMLOutput.append(getXMLLocation());
                myXMLOutput.append(getXMLTime());
                myXMLOutput.append(getXMLFooter());

                myOutputStream.write(myXMLOutput.toString());
                myOutputStream.close();
                myFileWriter.close();

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    private String getXMLHeader() {
        return "<INFORMATION_CHUNK>";
    }

    private String getXMLFooter() {
        return "</INFORMATION_CHUNK>";
    }

    private String getXMLPictureFilepath() {
        return "<PICTURE_FILEPATH>" + thePictureFilepath
                + "</PICTURE_FILEPATH>";
    }

    private String getXMLTitle() {
        return "<TITLE>" + theTitle + "</TITLE>";
    }

    private String getXMLUser() {
        return "<USER_NETID>" + theUser + "</USER_NETID>";
    }

    private String getXMLUsers() {
        return "<PEOPLE_NETID>" + theUsers + "</PEOPLE_NETID>";
    }

    private String getXMLTime() {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd:hhmm");
        String myTime = myFormatter.format(new Date());

        return "<TIME>" + myTime + "</TIME>";
    }

    private String getXMLLocation() {
        LocationManager myLocationManager = (LocationManager) theContext
                .getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = myLocationManager
                .getLastKnownLocation(myLocationManager.getBestProvider(
                        new Criteria(), false));

        if (myLocation != null) {

            return "<LOCATION_LATITUDE>" + myLocation.getLatitude()
                    + "</LOCATION_LATITUDE>" + "<LOCATION_LONGITUDE>"
                    + myLocation.getLongitude() + "</LOCATION_LONGITUDE>";
        } else {
            return "";
        }
    }

    public static ImageTagXMLObject readImageTagXMLObject(Context aContext,
            String aFilepath) {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();

            XMLReader xr = sp.getXMLReader();

            ImageTagSAXHandler handler = new ImageTagSAXHandler();
            xr.setContentHandler(handler);
            xr.setErrorHandler(handler);

            FileReader r = new FileReader(aFilepath);

            xr.parse(new InputSource(r));
            ImageTagXMLObject myObject = handler.getParsedObject();

            if (myObject == null) {
                Toast.makeText(aContext, "Error: Not Valid ImageTag",
                        Toast.LENGTH_SHORT).show();
            }
            
            return myObject;

        } catch (SAXException e) {
            Toast.makeText(aContext, "Error: Parsing XML",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            Toast.makeText(aContext, "Error: File Not Found",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(aContext, "Error: IOException",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            Toast.makeText(aContext, "Error: Parsing XML",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return null;
    }
}

class ImageTagSAXHandler extends DefaultHandler {
    private ImageTagXMLObject theObject;
    private StringBuilder theData;

    public ImageTagSAXHandler() {
        super();

        theObject = null;
    }

    public ImageTagXMLObject getParsedObject() {
        if (theObject.isMutable) {
            return null;
        } else {
            return theObject;
        }
    }

    @Override
    public void startDocument() {
        theObject = new ImageTagXMLObject();
    }

    @Override
    public void endDocument() {
        if (!theObject.finalizeObject()) {
            theObject = null;
        }
    }

    @Override
    public void startElement(String uri, String name, String qName,
            Attributes atts) {
    }

    @Override
    public void endElement(String uri, String name, String qName) {
        if (name.equals("PICTURE_FILEPATH")) {
            theObject.setPictureFilepath(theData.toString());
        } else if (name.equals("TITLE")) {
            theObject.setTitle(theData.toString());
        } else if (name.equals("USER_NETID")) {
            theObject.setUser(theData.toString());
        } else if (name.equals("PEOPLE")) {
            theObject.setUsers(theData.toString());
        } else if (name.equals("TIME")) {
            theObject.setTime(theData.toString());
        } else if (name.equals("LOCATION_LATITUDE")) {
            theObject.setLatitude(theData.toString());
        } else if (name.equals("LOCATION_LONGITUDE")) {
            theObject.setLongitude(theData.toString());
        }
    }

    @Override
    public void characters(char ch[], int start, int length) {
        theData = new StringBuilder();
        for (int i = start; i < start + length; i++) {
            theData.append(ch[i]);
        }
    }
}
