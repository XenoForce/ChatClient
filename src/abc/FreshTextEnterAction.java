package abc;

import java.awt.event.ActionEvent;
import java.net.Socket;
import java.sql.Connection;
import javax.swing.*;


public class FreshTextEnterAction extends AbstractAction {
  
  //-------------------------------------------------------------------------//
  //  Attributes                                                             //
  //-------------------------------------------------------------------------//
  private Connection  dbCon     ;
  private Socket      sndSock   ;
  private JTextArea   freshText ;
  private JTextArea   history   ;
  
  
  //-------------------------------------------------------------------------//
  //  Constructor                                                            //
  //-------------------------------------------------------------------------//
  public FreshTextEnterAction( Connection  dbConnection  ,
                               Socket      sendSocket    ,
                               JTextArea   freshTextArea ,
                               JTextArea   historyTextArea ) {
    
    dbCon     = dbConnection    ;
    sndSock   = sendSocket      ;
    freshText = freshTextArea   ;
    history   = historyTextArea ;
    
  } //Constructor
  
  
  //-------------------------------------------------------------------------//
  //  actionPerformed()                                                      //
  //-------------------------------------------------------------------------//
  @Override
  public void actionPerformed( ActionEvent  ae ) {
    
    String txt = freshText.getText().trim();
    
    if (!"".equals( txt )) {
      ChatMessage msg = new ChatMessage();
        msg.id = UUIdUtil.makeNewUUID();
        //msg.from = ___;
        //msg.to   = ___;
        msg.body = txt;
      
      //msg = MessageSender.postMessage( sndSock, msg );
      
      //DbMgr.storeMessage( dbCon, msg );
      
      String hist = history.getText();
      hist = hist + "\r\n" + txt;
      history.setText( hist );
      
      freshText.setText("");
    } //if
    
  } //actionPerformed()
  
  
} //class
