package abc;

import java.awt.Color;
import java.util.*;


public class ColourUtil {
  
  //-------------------------------------------------------------------------//
  //  Attributes                                                             //
  //-------------------------------------------------------------------------//
  private static Map<String, Color>  lookup ;
  
  
  //-------------------------------------------------------------------------//
  //  getColorObject()                                                       //
  //-------------------------------------------------------------------------//
  public static Color getColorObject( String  sColourName ) {
    
    ensureLookupExists();
    
    Color retVal = null;
    
    if (null != sColourName) {
      String sColour = sColourName.trim().toUpperCase();
      
      if (!"".equals( sColour )) {
        retVal = lookup.get( sColour );
        
        if (null == retVal) {
          int len = sColour.length();
          
          if ((6 == len) || (7 == len)) {
            String sRGB = (6 == len) ? sColour : sColour.substring( 1, 7 );
            retVal = getColor_from_RGB( sRGB );
          } //if
        } //if
      } //if
    } //if
    
    return retVal;
  } //getColorObject()
  
  
  //-------------------------------------------------------------------------//
  //  getColor_from_RGB()                                                    //
  //-------------------------------------------------------------------------//
  private static Color getColor_from_RGB( String  sRGB ) {
    
    int iR = getIntValue( sRGB.substring( 0, 2 ));
    int iG = getIntValue( sRGB.substring( 2, 4 ));
    int iB = getIntValue( sRGB.substring( 4, 6 ));
    
    float[] hsbVals = new float[3];
    
    Color.RGBtoHSB( iR, iG, iB, hsbVals );
    
    Color retVal = Color.getHSBColor( hsbVals[0], hsbVals[1], hsbVals[2] );
    
    return retVal;
  } //getColor_from_RGB()
  
  
  //-------------------------------------------------------------------------//
  //  getIntValue()                                                          //
  //-------------------------------------------------------------------------//
  private static int getIntValue( String  str ) {
    
    int retVal = 128;
    
    try {
      retVal = Integer.parseInt( str );
    }
    catch (Exception ex) {
      //no-op
    } //try
    
    return retVal;
  } //getIntValue()
  
  
  //-------------------------------------------------------------------------//
  //  ensureLookupExists()                                                   //
  //-------------------------------------------------------------------------//
  private static void ensureLookupExists() {
    
    if (null == lookup) {
      
      lookup = new Hashtable<>();
      
      lookup.put("BLACK"      , Color.BLACK      );
      lookup.put("BLUE"       , Color.BLUE       );
      lookup.put("CYAN"       , Color.CYAN       );
      lookup.put("DARK_GRAY"  , Color.DARK_GRAY  );
      lookup.put("GRAY"       , Color.GRAY       );
      lookup.put("GREEN"      , Color.GREEN      );
      lookup.put("LIGHT_GRAY" , Color.LIGHT_GRAY );
      lookup.put("MAGENTA"    , Color.MAGENTA    );
      lookup.put("ORANGE"     , Color.ORANGE     );
      lookup.put("PINK"       , Color.PINK       );
      lookup.put("RED"        , Color.RED        );
      lookup.put("WHITE"      , Color.WHITE      );
      lookup.put("YELLOW"     , Color.YELLOW     );
      
    } //if
    
  } //ensureLookupExists()
  
  
} //class
