package abc.netio;

import abc.json.*;

import java.io.*;
import java.net.*;


public class ReceiveConnectionMgr {
  
  //-------------------------------------------------------------------------//
  //  connect_Receiving_Object_Stream()                                      //
  //-------------------------------------------------------------------------//
  public static ObjectInputStream  connect_Receiving_Object_Stream( String  chatUser,
                                                                    String  serverName,
                                                                    int     portNo )
                                                             throws Exception {
    
    ObjectInputStream retVal = null;
    
    //- - - - - - -
    
    ConnTypeRequest  req = new ConnTypeRequest();
      req.chatUser       = chatUser;
      req.connectionType = ConnectionTypes.PASSIVE_CLIENT;
    
    String sjReq = JsonConnTypeUtil.requestToJson( req );
    
    //- - - - - - -
    
    Socket sock = new Socket( serverName, portNo );
    
    OutputStream        outS = sock.getOutputStream();
    ObjectOutputStream  oos  = new ObjectOutputStream( outS );
    
    oos.writeObject( sjReq );
    
    //- - - - - - -
    
    InputStream        inS  = sock.getInputStream();
    ObjectInputStream  ois  = new ObjectInputStream( inS );
    
    Object obj = ois.readObject();
    
    if (obj instanceof String) {
      String str = (String) obj;
      ConnTypeResponse resp = JsonConnTypeUtil.jsonToResponse( str );
      
      if (null != resp) {
        if (null != resp.theResponse) {
          if (ResponseCodes.OK.equals( resp.theResponse )) {
            retVal = ois;
          } //if
        } //if
      } //if
    } //if
    
    return retVal;
  } //(m)
  
  
} //class
