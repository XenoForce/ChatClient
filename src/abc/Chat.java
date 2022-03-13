package abc;

import abc.bos.*;
import abc.dbio.*;
import abc.gui.*;
import abc.json.JsonConnTypeUtil;
import abc.netio.*;
import abc.util.*;

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
    
    getMessages_From_Client_DB( chatUser, mapContact, dbCon );
    
    int iPortNo = Server_PortNo_Util.getServerPortNo( serverPort );
    
    Socket sndSock = connect_Send_Socket   ( chatUser, serverName, iPortNo );
    Socket rcvSock = connect_Receive_Socket( chatUser, serverName, iPortNo );
    
    GuiWin win = new GuiWin( dbCon        ,
                             chatUser     ,
                             mapContact   ,
                             colourScheme ,
                             sndSock      ,
                             rcvSock      );
    
    win.setVisible( true );
  } //run()
  
  
  //-------------------------------------------------------------------------//
  //  getMessages_From_Client_DB()                                           //
  //-------------------------------------------------------------------------//
  private void getMessages_From_Client_DB( String                chatUser ,
                                           Map<String, Contact>  allContacts ,
                                           Connection            dbCon )
                                    throws Exception {
    
    for (Contact contact : allContacts.values()) {
      contact.arrMessage = ClientDbMgr.getAllMessages_for_Contact(
                                         chatUser,
                                         contact.contactName,
                                         dbCon );
    } //for
    
  } //getMessages_From_Client_DB()
  
  
  //-------------------------------------------------------------------------//
  //  connect_Send_Socket()                                                  //
  //-------------------------------------------------------------------------//
  private Socket  connect_Send_Socket( String  chatUser,
                                       String  serverName,
                                       int     portNo ) throws Exception {
    
    ConnTypeRequest  req = new ConnTypeRequest();
      req.chatUser       = chatUser;
      req.connectionType = ConnectionTypes.ACTIVE_CLIENT;
    
    String sjReq = JsonConnTypeUtil.requestToJson( req );
    
    //- - - - - - -
    
    Socket retVal = null;
    Socket sock   = new Socket( serverName, portNo );
    
    OutputStream        outS = sock.getOutputStream();
    ObjectOutputStream  oos  = new ObjectOutputStream( outS );
    
    oos.writeObject( sjReq );
    
    //- - - - - - -
    
    InputStream         inS  = sock.getInputStream();
    ObjectInputStream   ois  = new ObjectInputStream( inS );
    
    Object obj = ois.readObject();
    
    if (obj instanceof String) {
      String str = (String) obj;
      ConnTypeResponse resp = JsonConnTypeUtil.jsonToResponse( str );
      
      if (null != resp) {
        if (null != resp.theResponse) {
          if (ResponseCodes.OK.equals( resp.theResponse )) {
            retVal = sock;
          } //if
        } //if
      } //if
    } //if
    
    return retVal;
  } //connect_Send_Socket()
  
  
  //-------------------------------------------------------------------------//
  //  connect_Receive_Socket()                                               //
  //-------------------------------------------------------------------------//
  private Socket  connect_Receive_Socket( String  chatUser,
                                          String  serverName,
                                          int     portNo ) throws Exception {
    
    ConnTypeRequest  req = new ConnTypeRequest();
      req.chatUser       = chatUser;
      req.connectionType = ConnectionTypes.PASSIVE_CLIENT;
    
    String sjReq = JsonConnTypeUtil.requestToJson( req );
    
    //- - - - - - -
    
    Socket retVal = null;
    Socket sock   = new Socket( serverName, portNo );
    
    OutputStream        outS = sock.getOutputStream();
    ObjectOutputStream  oos  = new ObjectOutputStream( outS );
    
    oos.writeObject( sjReq );
    
    //- - - - - - -
    
    InputStream         inS  = sock.getInputStream();
    ObjectInputStream   ois  = new ObjectInputStream( inS );
    
    Object obj = ois.readObject();
    
    if (obj instanceof String) {
      String str = (String) obj;
      ConnTypeResponse resp = JsonConnTypeUtil.jsonToResponse( str );
      
      if (null != resp) {
        if (null != resp.theResponse) {
          if (ResponseCodes.OK.equals( resp.theResponse )) {
            retVal = sock;
          } //if
        } //if
      } //if
    } //if
    
    return retVal;
  } //connect_Receive_Socket()
  
  
  //-------------------------------------------------------------------------//
  //  connect_to_Client_DB()                                                 //
  //-------------------------------------------------------------------------//
  private Connection connect_to_Client_DB() throws Exception {
    
    Class.forName("org.hsqldb.jdbc.JDBCDriver" );
    
    Connection con = DriverManager.getConnection("jdbc:hsqldb:file:datadir/chatdb", "SA", "");
    
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
