package abc.netio;

import abc.bos.*;
import abc.json.*;

import java.io.*;
import java.net.Socket;


public class MessageSender {
  
  //-------------------------------------------------------------------------//
  //  postMessage()                                                          //
  //-------------------------------------------------------------------------//
  public static ChatMessage postMessage( ChatMessage  msg ,
                                         Socket       sndSock ) throws Exception {
    
    String json = JsonMessageUtil.msgToJson( msg );
    
    //- - - - - - -
    
    OutputStream        outS = sndSock.getOutputStream();
    ObjectOutputStream  oos  = new ObjectOutputStream( outS );
    
    oos.writeObject( json );
    
    //- - - - - - -
    
    InputStream        inS = sndSock.getInputStream();
    ObjectInputStream  ois = new ObjectInputStream( inS );
    
    Object obj = ois.readObject();
    
    if (null != obj) {
      if (obj instanceof ChatMessage) {
        ChatMessage msg2 = (ChatMessage) obj;
        
        if (null != msg2.timeStamp) {
          msg.timeStamp = msg2.timeStamp;
        } //if
      } //if
    } //if
    
    if (null == msg.timeStamp) {
      msg.timeStamp = new java.util.Date();
    } //if
    
    return msg;
  } //postMessage()
  
  
} //class
