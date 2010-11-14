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

public class ImageTagXMLObject {
    private final String thePictureFilepath;
    private String theTitle;
    private String theUser;
    private String theUsers;

    public ImageTagXMLObject(String aPictureFilepath) {
        thePictureFilepath = aPictureFilepath;
    }
    
    public String getPictureFilepath() {
        return thePictureFilepath;
    }

    public String getTitle() {
        return theTitle;
    }

    public void setTitle(String aTitle) {
        theTitle = aTitle;
    }

    public String getUser() {
        return theUser;
    }

    public void setUser(String aUser) {
        theUser = aUser;
    }

    public String getUsers() {
        return theUsers;
    }

    public void setUsers(String aUsers) {
        theUsers = aUsers;
    }

    public boolean writeImageTagXMLObject() {
        return false;
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
    public ImageTagSAXHandler() {
        super();
    }

    public void startDocument() {
        System.out.println("Start document");
    }

    public void endDocument() {
        System.out.println("End document");
    }

    public void startElement(String uri, String name, String qName,
            Attributes atts) {
        if ("".equals(uri))
            System.out.println("Start element: " + qName);
        else
            System.out.println("Start element: {" + uri + "}" + name);
    }

    public void endElement(String uri, String name, String qName) {
        if ("".equals(uri))
            System.out.println("End element: " + qName);
        else
            System.out.println("End element:   {" + uri + "}" + name);
    }

    public void characters(char ch[], int start, int length) {
        System.out.print("Characters:    \"");
        for (int i = start; i < start + length; i++) {
            switch (ch[i]) {
                case '\\':
                    System.out.print("\\\\");
                    break;
                case '"':
                    System.out.print("\\\"");
                    break;
                case '\n':
                    System.out.print("\\n");
                    break;
                case '\r':
                    System.out.print("\\r");
                    break;
                case '\t':
                    System.out.print("\\t");
                    break;
                default:
                    System.out.print(ch[i]);
                    break;
            }
        }
        System.out.print("\"\n");
    }
}
