package abc;

import java.awt.event.ActionEvent;
import java.net.Socket;
import javax.swing.*;


public class FreshTextEnterAction extends AbstractAction {
  
  //-------------------------------------------------------------------------//
  //  Attributes                                                             //
  //-------------------------------------------------------------------------//
  private Socket     sndSock   ;
  private JTextArea  freshText ;
  private JTextArea  history   ;
  
  
  //-------------------------------------------------------------------------//
  //  Constructor                                                            //
  //-------------------------------------------------------------------------//
  public FreshTextEnterAction( Socket     sendSocket ,
                               JTextArea  freshTextArea ,
                               JTextArea  historyTextArea ) {
    
    sndSock   = sendSocket ;
    freshText = freshTextArea ;
    history   = historyTextArea ;
    
  } //Constructor
  
  
  //-------------------------------------------------------------------------//
  //  actionPerformed()                                                      //
  //-------------------------------------------------------------------------//
  @Override
  public void actionPerformed( ActionEvent  ae ) {
    
    String txt = freshText.getText().trim();
    
    if (!"".equals( txt )) {
      
      String hist = history.getText();
      
      hist = hist + "\r\n" + txt;
      
      history.setText( hist );
      
      freshText.setText("");
      
    } //if
    
  } //actionPerformed()
  
  
} //class
