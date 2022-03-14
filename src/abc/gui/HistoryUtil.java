package abc.gui;

import abc.bos.*;
import abc.util.*;

import javax.swing.*;


public class HistoryUtil {
  
  //-------------------------------------------------------------------------//
  //  Attributes                                                             //
  //-------------------------------------------------------------------------//
  private static HistoryWorker  worker ;
  
  
  //-------------------------------------------------------------------------//
  //  initialize()                                                           //
  //-------------------------------------------------------------------------//
  public static void initialize( JTextArea  historyTextArea ) {
    
    worker = new HistoryWorker( historyTextArea );
  } //initialize()
  
  
  //-------------------------------------------------------------------------//
  //  add_One_Msg_to_History()                                               //
  //-------------------------------------------------------------------------//
  public static void add_One_Msg_to_History( ChatMessage  msg ) {
    
    synchronized( worker ) {
      worker.add_One_Msg_to_History( msg );
    } //sychronize()
    
  } //add_One_Msg_to_History()
  
  
  //-------------------------------------------------------------------------//
  //  handle_New_Incoming_ChatMessage()                                      //
  //-------------------------------------------------------------------------//
  public static void handle_New_Incoming_ChatMessage( ChatMessage  msg ) {
    
    synchronized( worker ) {
      worker.handle_New_Incoming_ChatMessage( msg );
    } //sychronize()
    
  } //handle_New_Incoming_ChatMessage()
  
  
  //-------------------------------------------------------------------------//
  //  Inner class: HistoryWorker                                             //
  //-------------------------------------------------------------------------//
  private static class HistoryWorker {
    
    //-------------------------------------------------------------------------//
    //  Attributes                                                             //
    //-------------------------------------------------------------------------//
    private JTextArea  history ;
    
    
    //-------------------------------------------------------------------------//
    //  Constructor                                                            //
    //-------------------------------------------------------------------------//
    private HistoryWorker( JTextArea  historyTextArea ) {
      
      history = historyTextArea;
    } //Constructor
    
    
    //-------------------------------------------------------------------------//
    //  add_One_Msg_to_History()                                               //
    //-------------------------------------------------------------------------//
    private void add_One_Msg_to_History( ChatMessage  msg ) {
      
      String sTime = MessageDateUtil.formatTimeStamp( msg.timeStamp );
      
      StringBuilder sb = new StringBuilder();
      
      sb.append( msg.sender + "  (" + sTime + ")\r\n");
      sb.append( msg.body   + "\r\n");
      
      String txtNew = sb.toString();
      
      String hist = history.getText();
      hist = hist + "\r\n" + txtNew;
      history.setText( hist );
      history.updateUI();
      
    } //add_One_Msg_to_History()
    
    
    //-------------------------------------------------------------------------//
    //  handle_New_Incoming_ChatMessage()                                      //
    //-------------------------------------------------------------------------//
    private void handle_New_Incoming_ChatMessage( ChatMessage  msg ) {
      
      //ToDo: write to client DB.
      
      //ToDo: Add to Contacts structure.
      
      add_One_Msg_to_History( msg );
      
    } //handle_New_Incoming_ChatMessage()
    
    
  } //inner class
  
  
} //class
