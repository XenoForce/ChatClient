package abc.gui;

import abc.bos.*;
import abc.util.*;

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
      
      //msg = MessageSender.postMessage( sndSock, msg );
      
      //DbMgr.storeMessage( dbCon, msg );
      
      theContact.arrMessage.add( msg );
      
      // Update the GUI:
      
      String hist = history.getText();
      hist = hist + "\r\n" + txt;
      history.setText( hist );
      
      freshText.setText("");
    } //if
    
  } //actionPerformed()
  
  
} //class
