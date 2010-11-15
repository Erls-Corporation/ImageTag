package edu.cs423.mp5.xmllib;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class ImageTagXMLObject {
    public boolean isMutable;
    private String thePictureFilepath;
    private String theTitle;
    private String theUser;
    private String theUsers;

    public ImageTagXMLObject() {
        isMutable = true;

        thePictureFilepath = null;
        theTitle = null;
        theUser = null;
        theUsers = null;
    }

    public boolean finalizeObject() {
        if (thePictureFilepath != null && theTitle != null && theUser != null
                && theUsers != null) {
            isMutable = false;
            return true;
        } else {
            return false;
        }
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

    public boolean writeImageTagXMLObject() {
        if (isMutable) {
            throw new UnsupportedOperationException(
                    "Cannot Write Out Mutable Object");
        } else {
            //TODO:
            
            return false;
        }
    }

    public static ImageTagXMLObject readImageTagXMLObject(String aFilepath) {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();

            XMLReader xr = sp.getXMLReader();

            ImageTagSAXHandler handler = new ImageTagSAXHandler();
            xr.setContentHandler(handler);
            xr.setErrorHandler(handler);

            FileReader r = new FileReader(aFilepath);

            xr.parse(new InputSource(r));
            return handler.getParsedObject();

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
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

    public void startDocument() {
        theObject = new ImageTagXMLObject();
    }

    public void endDocument() {
        if (!theObject.finalizeObject()) {
            Log.d("ERROR", "Error Parsing XML");
            theObject = null;
        }
    }

    public void startElement(String uri, String name, String qName,
            Attributes atts) {
    }

    public void endElement(String uri, String name, String qName) {
        if (name.equals("PICTURE_FILEPATH")) {
            theObject.setPictureFilepath(theData.toString());
        } else if (name.equals("TITLE")) {
            theObject.setTitle(theData.toString());
        } else if (name.equals("USER_NETID")) {
            theObject.setUser(theData.toString());
        } else if (name.equals("PEOPLE")) {
            theObject.setUsers(theData.toString());
        }
    }

    public void characters(char ch[], int start, int length) {
        theData = new StringBuilder();
        for (int i = start; i < start + length; i++) {
            theData.append(ch[i]);
        }
    }
}
