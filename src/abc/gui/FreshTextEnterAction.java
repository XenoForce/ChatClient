package abc.gui;

import abc.bos.*;
import abc.dbio.*;
import abc.netio.*;
import abc.util.*;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.net.Socket;
import java.sql.Connection;
import javax.swing.*;


public class FreshTextEnterAction extends AbstractAction {
  
  //-------------------------------------------------------------------------//
  //  Attributes                                                             //
  //-------------------------------------------------------------------------//
  private Connection  dbCon     ;
  private String      chatUser  ;
  private Socket      sndSock   ;
  private JTextArea   history   ;
  private JTextArea   freshText ;
  private GuiWin      guiWin    ;
  
  
  //-------------------------------------------------------------------------//
  //  Constructor                                                            //
  //-------------------------------------------------------------------------//
  public FreshTextEnterAction( Connection  dbConnection    ,
                               String      theChatUser     ,
                               Socket      sendSocket      ,
                               JTextArea   historyTextArea ,
                               JTextArea   freshTextArea   ,
                               GuiWin      theGuiWin       ) {
    
    dbCon     = dbConnection    ;
    chatUser  = theChatUser     ;
    sndSock   = sendSocket      ;
    history   = historyTextArea ;
    freshText = freshTextArea   ;
    guiWin    = theGuiWin       ;
    
  } //Constructor
  
  
  //-------------------------------------------------------------------------//
  //  actionPerformed()                                                      //
  //-------------------------------------------------------------------------//
  @Override
  public void actionPerformed( ActionEvent  ae ) {
    
    String txt = freshText.getText().trim();
    
    if (!"".equals( txt )) {
      Contact theContact = guiWin.currentContact;
      
      ChatMessage msg = new ChatMessage();
        msg.id          = UUIdUtil.makeNewUUID();
        msg.sender      = chatUser;
        msg.destination = theContact.contactName;
        msg.body        = txt;
      
      try {
        msg = RequestSender.postMessage( msg, sndSock );    //TimeStamp is assigned on the server.
        msg.shown = true;
        
        CommonDbMgr.storeMessage( msg, dbCon );
        
        theContact.arrMessage.add( msg );  //This can also be done by the listening thread. Need to be synchronized.
        
        // Update the GUI:
        
        HistoryUtil.add_One_Msg_to_History( msg );
        
        /*
        (Old code, ...before HistoryUtil.)
        String hist = history.getText();
        hist = hist + "\r\n" + txt;
        history.setText( hist );
        */
        
        freshText.setText("");
        freshText.requestFocus();
      }
      catch (Exception ex) {
        Component  parent      = guiWin;
        String     errMsg      = ex.toString();
        String     title       = "ERROR";
        int        messageType = JOptionPane.ERROR_MESSAGE;
        
        JOptionPane.showMessageDialog( parent, errMsg, title, messageType );
      } //try
    } //if
    
  } //actionPerformed()
  
  
} //class
