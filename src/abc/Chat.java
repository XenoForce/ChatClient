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
    
    Map<String, Contact> mapContact = get_All_Contacts();
    
    Properties props = readConfigFile();
    
    String chatUser   = props.getProperty("ChatUserNameOrAlias");
    String serverName = props.getProperty("ServerNameOrAddress");
    String serverPort = props.getProperty("ServerPort");
    
    ColourScheme colourScheme = populateColourScheme( props );
    
    Socket sndSock = connect_Send_Socket   ( chatUser, serverName, serverPort );
    Socket rcvSock = connect_Receive_Socket( chatUser, serverName, serverPort );
    
    GuiWin win = new GuiWin( dbCon        ,
                             chatUser     ,
                             mapContact   ,
                             colourScheme ,
                             sndSock      ,
                             rcvSock      );
    
    win.setVisible( true );
    
    
    //Move this to GUI windows:
    //List<ChatMessage> arr = getMessages_From_Client_DB( dbCon );
    
    
  } //run()
  
  
  //-------------------------------------------------------------------------//
  //  connect_Send_Socket()                                                  //
  //-------------------------------------------------------------------------//
  private Socket  connect_Send_Socket( String chatUser,
                                       String serverName,
                                       String serverPort ) throws Exception {
    
    return null;
    
  } //connect_Send_Socket()
  
  
  //-------------------------------------------------------------------------//
  //  connect_Receive_Socket()                                               //
  //-------------------------------------------------------------------------//
  private Socket  connect_Receive_Socket( String  chatUser,
                                          String  serverName,
                                          String  serverPort ) throws Exception {
    
    return null;
    
  } //connect_Receive_Socket()
  
  
  //-------------------------------------------------------------------------//
  //  connect_to_Client_DB()                                                 //
  //-------------------------------------------------------------------------//
  private Connection connect_to_Client_DB() throws Exception {
    
    Class.forName("org.hsqldb.jdbc.JDBCDriver" );
    
    Connection con = DriverManager.getConnection("jdbc:hsqldb:file:chatdb", "SA", "");
    
    return con;
  } //connect_to_Client_DB()
  
  
  //-------------------------------------------------------------------------//
  //  get_All_Contacts()                                                     //
  //-------------------------------------------------------------------------//
  private Map<String, Contact>  get_All_Contacts() throws Exception {
    
    File file = new File("contacts.txt");
    FileInputStream fis = new FileInputStream( file );
    
    Properties props = new Properties();
    props.load( fis );
    
    fis.close();
    
    //- - - - - - - - - - - - - - - - - - -
    
    Map<String, Contact> mapContact = new TreeMap<>();
    
    IntStream iStream = IntStream.range( 1, 31 );
    
    IntConsumer iConsumer = a -> 
    {
      String keyName   = "Contact" + a;
      String valName   = props.getProperty( keyName, "");
      
      String keyColour = "Colour_" + a;
      String valColour = props.getProperty( keyColour );
      
      if (!"".equals( valName )) {
        Contact contact = new Contact();
          contact.contactName    = valName;
          contact.fontColourName = valColour;
          contact.fontColour     = ColourUtil.getColorObject( valColour );
        
        mapContact.put( contact.contactName, contact );
      } //if
    };
    
    iStream.forEach( iConsumer );
    
    return mapContact;
  } //get_All_Contacts()
  
  
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
  
  
  //-------------------------------------------------------------------------//
  //  populateColourScheme()                                                 //
  //-------------------------------------------------------------------------//
  private ColourScheme populateColourScheme( Properties  props ) {
    
    ColourScheme retVal = new ColourScheme();
    
    retVal.textColour             = ColourUtil.getColorObject( props.getProperty("TextColour"));
    retVal.textBackground         = ColourUtil.getColorObject( props.getProperty("TextBackground"));
    
    retVal.windowForegroundColour = ColourUtil.getColorObject( props.getProperty("WindowForegroundColour"));
    retVal.windowBackgroundColour = ColourUtil.getColorObject( props.getProperty("WindowBackgroundColour"));
    
    return retVal;
  } //populateColourScheme()
  
  
} //class
