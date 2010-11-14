package edu.cs423.mp5.xmllib;

import java.io.Serializable;
import java.util.List;

public class ImageTagXMLObject implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -4506619761358053593L;
    String theTitle;
    String theUser;
    List<String> theUsers;
    
    public ImageTagXMLObject() {
        
    }
}
