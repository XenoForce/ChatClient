package abc;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;


public class Chat {
  
  //-------------------------------------------------------------------------//
  //  main()                                                                 //
  //-------------------------------------------------------------------------//
  public static void main( String[]  args ) {
    
    try {
      Chat app = new Chat();
      app.run();
    }
    catch (Exception ex) {
      ex.printStackTrace( System.err );
    } //try
    
  } //main()
  
  
  //-------------------------------------------------------------------------//
  //  run()                                                                  //
  //-------------------------------------------------------------------------//
  private void run() throws Exception {
    
    Connection dbCon = connect_to_Client_DB();
    
    List<String> arrContact = getList_of_Contacts();
    
    Properties props = readConfigFile();
    
    String chatUser   = props.getProperty("ChatUserNameOrAlias");
    String serverName = props.getProperty("ServerNameOrAddress");
    String serverPort = props.getProperty("ServerPort");
    
    Socket rcvSock = connect_Receive_Socket( chatUser, serverName, serverPort );
    Socket sndSock = connect_Send_Socket   ( chatUser, serverName, serverPort );
    
    GuiWin win = new GuiWin( dbCon, arrContact, rcvSock, sndSock );
    
    win.setVisible( true );
    
    
    //Move this to GUI windows:
    //List<ChatMessage> arr = getMessages_From_Client_DB( dbCon );
    
    
  } //run()
  
  
  
  //-------------------------------------------------------------------------//
  //  connect_Receive_Socket()                                               //
  //-------------------------------------------------------------------------//
  private Socket  connect_Receive_Socket( String  chatUser,
                                          String  serverName,
                                          String  serverPort ) throws Exception {
    
    return null;
    
  } //connect_Receive_Socket()
  
  
  //-------------------------------------------------------------------------//
  //  connect_Send_Socket()                                                  //
  //-------------------------------------------------------------------------//
  private Socket  connect_Send_Socket( String chatUser,
                                       String serverName,
                                       String serverPort ) throws Exception {
    
    return null;
    
  } //connect_Send_Socket()
  
  
  //-------------------------------------------------------------------------//
  //  connect_to_Client_DB()                                                 //
  //-------------------------------------------------------------------------//
  private Connection connect_to_Client_DB() throws Exception {
    
    Class.forName("org.hsqldb.jdbc.JDBCDriver" );
    
    Connection con = DriverManager.getConnection("jdbc:hsqldb:file:chatdb", "SA", "");
    
    return con;
  } //connect_to_Client_DB()
  
  
  //-------------------------------------------------------------------------//
  //  getList_of_Contacts()                                                  //
  //-------------------------------------------------------------------------//
  private List<String>  getList_of_Contacts() throws Exception {
    
    File file = new File("contacts.txt");
    FileInputStream fis = new FileInputStream( file );
    
    Properties props = new Properties();
    props.load( fis );
    
    fis.close();
    
    List<String> arrContact = new ArrayList<>();
    
    IntStream iStream = IntStream.range( 1, 31 );
    
    IntConsumer iConsumer = a -> 
    {
      String key = "Contact" + a;
      String val = props.getProperty( key, "");
      
      if (!"".equals(val)) {
        arrContact.add( val );
      } //if
    };
    
    iStream.forEach( iConsumer );
    
    return arrContact;
  } //getList_of_Contacts()
  
  
  //-------------------------------------------------------------------------//
  //  readConfigFile()                                                       //
  //-------------------------------------------------------------------------//
  private Properties readConfigFile() throws Exception {
    
    File file = new File("config.txt");
    FileInputStream fis = new FileInputStream( file );
    
    Properties props = new Properties();
    props.load( fis );
    
    fis.close();
    
    return props;
  } //readConfigFile()
  
  
} //class
