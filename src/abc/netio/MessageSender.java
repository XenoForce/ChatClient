package abc.netio;

import abc.bos.*;

import java.net.Socket;


public class MessageSender {
  
  //-------------------------------------------------------------------------//
  //  postMessage()                                                          //
  //-------------------------------------------------------------------------//
  public static ChatMessage postMessage(
      ChatMessage  msg ,
      Socket       sndSock )
  throws Exception
  {
    
    
    msg.timeStamp = new java.util.Date();
    
    
    
    return msg;
  } //postMessage()
  
  
} //class
