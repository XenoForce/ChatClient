package abc.gui;

import java.util.*;
import javax.swing.AbstractListModel;


public class ContactListModel extends AbstractListModel {
  
  //-------------------------------------------------------------------------//
  //  Attributes                                                             //
  //-------------------------------------------------------------------------//
  private List<String>  data ;
  
  
  //-------------------------------------------------------------------------//
  //  Constructor                                                            //
  //-------------------------------------------------------------------------//
  public ContactListModel( List<String>  contacts ) {
    
    data = contacts;
  } //Constructor
  
  
  //-------------------------------------------------------------------------//
  //  getElementAt​()                                                         //
  //-------------------------------------------------------------------------//
  public String getElementAt​( int index ) {
    
    String retVal = data.get( index );
    return retVal;
  } //getElementAt​()
  
  
  //-------------------------------------------------------------------------//
  //  getSize()                                                              //
  //-------------------------------------------------------------------------//
  public int getSize() {
    
    int    retVal = data.size();
    return retVal;
  } //getSize()
  
  
} //class
