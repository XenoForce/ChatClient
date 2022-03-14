package abc.netio;

import abc.bos.*;
import abc.gui.*;
import abc.json.*;

import java.io.*;
import java.net.*;


public class ReceiveThread extends Thread {
  
  //-------------------------------------------------------------------------//
  //  Attributes                                                             //
  //-------------------------------------------------------------------------//
  private Socket  rcvSock ;
  
  
  //-------------------------------------------------------------------------//
  //  Constructor                                                            //
  //-------------------------------------------------------------------------//
  public ReceiveThread( Socket  receiveSock ) {
    
    rcvSock = receiveSock;
  } //Constructor
  
  
  //-------------------------------------------------------------------------//
  //  run()                                                                  //
  //-------------------------------------------------------------------------//
  @Override
  public void run() {
    
    try {
      InputStream        inS = rcvSock.getInputStream();
      ObjectInputStream  ois = new ObjectInputStream( inS );
      
      while (true) {
        Object obj = ois.readObject();
        
        if (null != obj) {
          if (obj instanceof String) {
            String str = (String) obj;
            ChatMessage msg = null;
            
            try {
              msg = JsonMessageUtil.jsonToMsg( str );
            } catch (Exception ex) {}
            
            if (null != msg) {
              HistoryUtil.handle_New_Incoming_ChatMessage( msg );
            } //if
          } //if
        } //if
      } //while
    }
    catch (Exception ex) {
      ex.printStackTrace( System.err );
    } //try
    
  } //run()
  
  
} //class
