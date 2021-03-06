package abc.gui;

import abc.bos.*;
import abc.netio.*;
import abc.util.*;

import java.awt.Component;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;


public class GuiWin extends JFrame {
  
  //-------------------------------------------------------------------------//
  //  Attributes                                                             //
  //-------------------------------------------------------------------------//
  private Connection            dbCon          ;
  private String                chatUser       ;
  private Map<String, Contact>  allContacts    ;
  private ColourScheme          colourScheme   ;
  private Socket                sndSock        ;
  public  Contact               currentContact ;
  
  
  //-------------------------------------------------------------------------//
  //  Constructor                                                            //
  //-------------------------------------------------------------------------//
  public GuiWin( Connection            dbConnection   ,
                 String                theChatUser    ,
                 Map<String, Contact>  allTheContacts ,
                 ColourScheme          colourSet      ,
                 Socket                sendSock       ,
                 String                serverName     ,
                 int                   serverPort ) {
    
    dbCon        = dbConnection   ;
    chatUser     = theChatUser    ;
    allContacts  = allTheContacts ;
    colourScheme = colourSet      ;
    sndSock      = sendSock       ;
    
    
    initComponents();
    
    history  .setLineWrap( true );
    freshText.setLineWrap( true );
    
    apply_Colour_Scheme();
    
    HistoryUtil.initialize( history );
    
    setup_List_of_Contacts();
    
    
    // Trap when ENTER key is pressed:
    
    FreshTextEnterAction freshTextEnterAction = new FreshTextEnterAction( dbCon     ,
                                                                          chatUser  ,
                                                                          sndSock   ,
                                                                          history   ,
                                                                          freshText ,
                                                                          this      );
    
    InputMap  iMap = freshText.getInputMap( freshText.WHEN_FOCUSED );
    ActionMap aMap = freshText.getActionMap();
    
    iMap.put( KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0 ), "onEnter");
    aMap.put("onEnter", freshTextEnterAction );
    
    
    // Connect the Receiving socket:
    
    ObjectInputStream  ois = null;
    
    try {
      ois = ReceiveConnectionMgr.connect_Receiving_Object_Stream( chatUser,
                                                                  serverName,
                                                                  serverPort );
    }
    catch (Exception ex) {
      ex.printStackTrace( System.err );
      ois = null;
      
      Component  parent      = this;
      String     errMsg      = "Error connecting object receiving socket connection.\r\n\r\n" + ex.toString();
      String     title       = "ERROR";
      int        messageType = JOptionPane.ERROR_MESSAGE;
      
      JOptionPane.showMessageDialog( parent, errMsg, title, messageType );
    } //try
    
    // Start the Receive thread:
    
    if (null != ois) {
      ReceiveThread rcvThread = new ReceiveThread( ois );
      rcvThread.start();
    } //if
    
  } //Constructor
  
  
  //-------------------------------------------------------------------------//
  //  apply_Colour_Scheme()                                                  //
  //-------------------------------------------------------------------------//
  private void apply_Colour_Scheme() {
    
    /*
    try {
      UIManager.setLookAndFeel( (LookAndFeel) null );
    }
    catch (Exception ex) {
      ex.printStackTrace( System.err );
    } //try
    */
    
    if (null != colourScheme.textColour) {
      nameList .setForeground( colourScheme.textColour );
      history  .setForeground( colourScheme.textColour );
      freshText.setForeground( colourScheme.textColour );
    } //if
    
    if (null != colourScheme.textBackground) {
      nameList .setBackground( colourScheme.textBackground );
      history  .setBackground( colourScheme.textBackground );
      freshText.setBackground( colourScheme.textBackground );
    } //if
    
    if (null != colourScheme.windowForegroundColour) {
      setForeground( colourScheme.windowForegroundColour );
    } //if
    
    if (null != colourScheme.windowBackgroundColour) {
      setBackground( colourScheme.windowBackgroundColour );
    } //if
    
    
    nameList .updateUI();
    history  .updateUI();
    freshText.updateUI();
    
    
    //LookAndFeel laf = UIManager.getLookAndFeel();
    
    //UIDefaults defaults = laf.getDefaults();
    
    //UIDefaults defaults = UIManager.getLookAndFeelDefaults();
    
    //defaults.put("TextArea.background", colourScheme.textBackground );
    
    //defaults.put("TextArea.background","javax.swing.plaf.ColorUIResource[r  =  200,g  =  21,b  =  24]");
    //defaults.put("window"             ,"javax.swing.plaf.ColorUIResource[r  =  200,g  =  21,b  =  24]");
    
    /*
    try {
      UIManager.setLookAndFeel( laf );
      
      nameList .updateUI();
      history  .updateUI();
      freshText.updateUI();
    }
    catch (Exception ex) {
      ex.printStackTrace( System.err );
    } //try
    */
    
    //javax.swing.plaf.ColorUIResource[r  =  200,g  =  221,b  =  242]
    
    //System.out.println( defaults );
    
  } //apply_Colour_Scheme()
  
  
  //-------------------------------------------------------------------------//
  //  setup_List_of_Contacts()                                               //
  //-------------------------------------------------------------------------//
  private void setup_List_of_Contacts() {
    
    ListModel<String> lstModel = new ContactListModel( new ArrayList( allContacts.keySet() ));
    nameList.setModel( lstModel );
    nameList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
    
  } //setup_List_of_Contacts()
  
  
  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
   * content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jScrollPane1 = new javax.swing.JScrollPane();
    nameList = new javax.swing.JList<>();
    jScrollPane2 = new javax.swing.JScrollPane();
    history = new javax.swing.JTextArea();
    jScrollPane3 = new javax.swing.JScrollPane();
    freshText = new javax.swing.JTextArea();
    btnAttachFile = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("Chat (v1.0)"); // NOI18N
    setResizable(false);

    nameList.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
    nameList.setName(""); // NOI18N
    nameList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
        nameListValueChanged(evt);
      }
    });
    jScrollPane1.setViewportView(nameList);

    history.setEditable(false);
    history.setColumns(20);
    history.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
    history.setRows(5);
    history.setName(""); // NOI18N
    jScrollPane2.setViewportView(history);

    freshText.setColumns(20);
    freshText.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
    freshText.setRows(5);
    freshText.setName(""); // NOI18N
    jScrollPane3.setViewportView(freshText);

    btnAttachFile.setLabel("Attach File");
    btnAttachFile.setName(""); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(btnAttachFile)
            .addGap(44, 44, 44)))
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jScrollPane2)
          .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 937, Short.MAX_VALUE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
          .addGroup(layout.createSequentialGroup()
            .addGap(48, 48, 48)
            .addComponent(btnAttachFile)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents
  
  
  private void nameListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_nameListValueChanged
    
    boolean bIsAdjusting = evt.getValueIsAdjusting();
    
    if (!bIsAdjusting) {
      if (null != currentContact) {
        currentContact.draft = freshText.getText();
      } //if
      
      String selectedName = nameList.getSelectedValue();
      
      currentContact = allContacts.get( selectedName );
      show_History_Messages( currentContact );
      freshText.setText( currentContact.draft );
      
    } //if
  }//GEN-LAST:event_nameListValueChanged
  
  
  //-------------------------------------------------------------------------//
  //  show_History_Messages()                                                //
  //
  //  Replace this whole method with one call to HistoryUtil.add__()
  //
  //  (There is a need to be synchronized.)
  //
  //-------------------------------------------------------------------------//
  private void show_History_Messages( Contact  theContact ) {
    
    //history.setText("");   //...unnecessary.
    
    StringBuilder sb = new StringBuilder();
    
    for (ChatMessage msg : theContact.arrMessage) {
      String sTime = MessageDateUtil.formatTimeStamp( msg.timeStamp );
        //  (must sandardize)
      sb.append("\r\n");
      sb.append( msg.sender + "  (" + sTime + ")\r\n");
      sb.append( msg.body   + "\r\n");
    } //for
    
    String fullHistory = sb.toString();
    
    history.setText( fullHistory );
    
  } //show_History_Messages()
  
  
  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(GuiWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(GuiWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(GuiWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(GuiWin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        //new GuiWin().setVisible(true);
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton btnAttachFile;
  private javax.swing.JTextArea freshText;
  private javax.swing.JTextArea history;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JList<String> nameList;
  // End of variables declaration//GEN-END:variables
}
