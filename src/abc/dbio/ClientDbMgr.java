package abc.dbio;

import abc.bos.*;
import abc.util.*;

import java.sql.*;
import java.util.*;


public class ClientDbMgr {
  
  //-------------------------------------------------------------------------//
  //  getAllMessages_for_Contact()                                           //
  //-------------------------------------------------------------------------//
  public static List<ChatMessage> getAllMessages_for_Contact( String      chatUser ,
                                                              String      contactName ,
                                                              Connection  con )
                                                       throws Exception {
    
    List<ChatMessage> arrMsg = new ArrayList<>();
    
    String sFilter = "('" + chatUser + "','" + contactName + "')";
    
    String sql = "select "
               + " TimeStamp, "
               + " Id, "
               + " Sender, "
               + " Destination, "
               + " Body, "
               + " Shown "
               + " from   ChatMessage "
               + " where  Sender      in " + sFilter
               + " and    Destination in " + sFilter
               + " order  by TimeStamp ";
    
    Statement stmt = con.createStatement();
    ResultSet rs   = stmt.executeQuery( sql );
    
    while (rs.next()) {
      ChatMessage msg = new ChatMessage();
      
      msg.timeStamp   = rs.getTimestamp("TimeStamp");
      msg.id          = rs.getString("Id");
      msg.sender      = rs.getString("Sender");
      msg.destination = rs.getString("Destination");
      msg.body        = rs.getString("Body");
      msg.shown       = rs.getBoolean("Shown");
      
      arrMsg.add( msg );
    } //while
    
    rs.close();
    stmt.close();
    
    return arrMsg;
  } //getAllMessages_for_Contact()
  
  
} //class
