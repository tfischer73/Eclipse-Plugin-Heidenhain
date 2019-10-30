package ch.tfischer.hh.client;
/**
 * ============================================================================
 * Funktionen zur Verbindung und Kommunikation über LSV2-Protokoll mit der 
 * Steuerung
 * ============================================================================
 **/


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import ch.tfischer.hh.console.Console;
import ch.tfischer.hh.console.SyncWithUi;
import ch.tfischer.hh.data.Global;
import ch.tfischer.hh.toolbar.ToolbarSelectionListener;
import ch.tfischer.hh.view.LogfileView;


public class LSV_Client {
	
	/**  Konstanten für Login **/
	public static final String PW_INSPECT      = "INSPECT";     // nur lesende Funktionen ausführbar
	public static final String PW_DIAG         = "DIAGNOSTICS"; // Logbuch / Recover
	public static final String PW_PLCDEBUG     = "PLCDEBUG";    // Schreibender PLC
	public static final String PW_FILETRANSFER = "FILE";        // Dateisystem
	public static final String PW_MONITOR      = "MONITOR";     // TNC Fernbedienung und Screendump
	public static final String PW_DSP          = "DSP";         // DSP Funktionen
	public static final String PW_DNC          = "DNC";         // DNC-Funktionen
	public static final String PW_SCOPE        = "OSZI";        // Remote Scope
	public static final String PW_STREAMAXES   = "STREAMAXES";  // Streamen von Achsdaten
	//Nur mit Passwort
	public static final String PW_FILEPLC      = "FILEPLC";    // Dateisystem mit Zugriff auf PLC:-Drive
	public static final String PW_FILESYS      = "FILESYS";    // Dateisystem mit Zugriff auf PLC:-Drive
	
	/**  Konstanten für SetSysCmd **/
	public static final int LSV2_RESET_TNC            = 1;
	public static final int LSV2_STOP_TIMEUPDATE      = 2;
	public static final int LSV2_SET_BUF1024          = 3; 
	public static final int LSV2_SET_BUF512           = 4; 
	public static final int LSV2_SET_BUF2048          = 5; 
	public static final int LSV2_SET_BUF3072          = 6; 
	public static final int LSV2_SET_BUF4096          = 7; 
	public static final int LSV2_RESET_DNC            = 8; 
	public static final int LSV2_RESET_LSV2           = 9;  // not implemented
	public static final int LSV2_UPDATE_TNCOPT        = 10;
	public static final int LSV2_PUSH_PRESET_INTO_LOG = 11;
	public static final int LSV2_SCREENDUMP           = 12;
	public static final int LSV2_ACTIVATE_PLCPGM      = 13; // cmdpara: Dateiname
	public static final int LSV2_OBSERVE_ADD_FILE     = 15; // cmdpara: Dateiname
	public static final int LSV2_OBSERVE_REMOVE_FILE  = 16; // cmdpara: Dateiname
	public static final int LSV2_OBSERVE_REMOVE_ALL   = 17;
	public static final int LSV2_ACTIVATE_MFSK        = 18;
	public static final int LSV2_SECURE_FILE_SEND     = 19; // C_FL: T_FD wird mit Antworttelegramm (T_OK/T_ER) quittiert
	public static final int LSV2_DELETE_TABLE_ENTRY   = 20;

	/**  Konstanten für return **/
	public static final byte OK         = 0;
	public static final byte TIMEOUT    = 1;
	public static final byte ERROR      = 2;

	/** Konstanten **/
	private static final String CHAR0 = Character.toString ((char) 0);

	/** Variablen **/
	public static boolean is_timedout = false;
	public static String Steuerung = "";
	public static String NcVersion = "";
	public static String PlcVersion = "";
	public static String Zusatzoptionen = "";
	
	public static String LSV_info = "";
	public static String LSV_data = "";
	public static int BufferLength = 256-10;

	private static Socket client;
	private static SimpleDateFormat now = new SimpleDateFormat("HH:mm:ss");
	private static String newPasswort = null;
	private static InputStream inFromServer = null;
	private static OutputStreamWriter outToServer = null;
	private static InputStreamReader in;

	//static final ISchedulingRule mutex = new Mutex();


	//=========================================================================
	public static boolean Connect(String serverName, int port, int timeout) {
		is_timedout = false;
		try
		{
			SocketAddress sockaddr = new InetSocketAddress(serverName, port);
			client  = new Socket();
			client.setSoTimeout(timeout);
			client.setTcpNoDelay(false);
			client.setSendBufferSize(65535);
			//Connect with timeout (ms)
			client.connect(sockaddr, timeout);
			OutputStream outTo = client.getOutputStream();
			inFromServer = client.getInputStream();
			outToServer = new OutputStreamWriter(outTo , "ISO-8859-1");
		}
		catch(IOException e)
		{
			SyncWithUi.ConsolePrint( now.format(new Date()).concat(" - "), Console.BLACK, 0);
			SyncWithUi.ConsolePrintln("ERROR: ".concat(e.toString()),Console.RED); 
			ToolbarSelectionListener.requestRefresh();
			return false;
		}
		ToolbarSelectionListener.requestRefresh();
		return true;
	}

	
	//=========================================================================
	public static void Disconnect() {
		try{ 
			if (client != null) { 
				outToServer.close();
				inFromServer.close();
				client.close(); 
	        	Steuerung = "";
	        	NcVersion = "";
	        	PlcVersion = "";
	        	Zusatzoptionen = "";
			} 
		} catch(IOException e) { 
			SyncWithUi.ConsolePrint( now.format(new Date()).concat(" - "), Console.BLACK, 0);
			SyncWithUi.ConsolePrintln("ERROR: ".concat(e.getLocalizedMessage()),Console.RED); 
		}
	}


	// String senden / empfangen
	//=========================================================================
	private static boolean Send(String data) {
		int length = 0;
		String LSV_length="";
		byte[] buffer = new byte[4];
    	
    	//alte Daten löschen
    	LSV_data = "";

		try{
			outToServer.write(data); //.writeBytes(data);
			outToServer.flush();

			inFromServer.read(buffer);
			// Achtung!! Byte müssen umgerechnet werden da es kein unsigned gibt !!!!
			length = ((buffer[0] & 0xFF) << 24) | ((buffer[1] & 0xFF) << 16) | ((buffer[2] & 0xFF) << 8) | buffer[3] & 0xFF;
			LSV_length = new String(buffer);//, "UTF-8");
			//SyncWithUi.ConsolePrintln("length " + length,0);
			SyncWithUi.ConsolePrint("",0,0); // TODO ohne SyncWithUi gibt es einen Fehler!
	        //Info
	        inFromServer.read(buffer);
	        LSV_info = new String(buffer);//, "UTF-8");
	        if ( length > 4096-8 ) {
				SyncWithUi.ConsolePrint( now.format(new Date()).concat(" - "), Console.BLACK, 0 );
				SyncWithUi.ConsolePrintln("ERROR: Buffer overflow" + length + " > 4088",Console.RED); 
				is_timedout = true;
				return false;	        
	        }
	        if ( length > 0 ) {
				//Daten
				buffer = new byte[length];
		        inFromServer.read(buffer);
		        LSV_data = new String(buffer, "UTF-8");
				//SyncWithUi.ConsolePrintln("LSV_data " + LSV_data,0);	        
			}
		}catch ( IOException e ) {
			SyncWithUi.ConsolePrint( now.format(new Date()).concat(" - "), Console.BLACK, 0 );
			SyncWithUi.ConsolePrintln("ERROR: ".concat(e.toString()),Console.RED); 
			is_timedout = true;
			return false;
		}
		if ( Global.prefTelegram ) {
			if ( Global.prefTelegramHex ) {
				SyncWithUi.ConsolePrintln("Senden :\n".concat(hexString(data)).concat("\n"), Console.BLACK);
				SyncWithUi.ConsolePrintln("Empfangen :\n".concat(hexString(LSV_length.concat(LSV_info).concat(LSV_data))).concat("\n"), 0);
			} else {
				SyncWithUi.ConsolePrintln("Senden   : ".concat(data),0);
				SyncWithUi.ConsolePrintln("Empfangen: ".concat(LSV_length).concat(LSV_info).concat(LSV_data),Console.BLACK);
			}
		}
		buffer = null;
		return true;
	}

	// Debug Ausgabe als HEX-Darstellung erstellen
	//=========================================================================
	private static String hexString(String data){
		char sendbyte[];
		sendbyte = data.toCharArray();// .getBytes("UTF-8");
		String regex = "[" + (char) 0 + "\\a\\t\\n\\v\\f\\r]";
		String hex = "0000   ";
		String ret = "";
		String ascii = "";
		int row = 0;
		int i = 0;
		for ( char chr : sendbyte){
			hex = hex.concat(" ").concat(String.format("%02x", chr  & 0xff ));
			ascii += chr;
			i++;
			if ( i % 16 == 0 ){
				ret = ret.concat(hex).concat("    ").concat(ascii.replaceAll(regex, ".")).concat("\n");
				ascii="";
				hex = String.format("%04x   ", row += 16 );
			}
		}
		if ( hex.length() > 8){
			while (hex.length() < 55){
				hex += " ";
			}
			ret = ret.concat(hex).concat("    ").concat(ascii.replaceAll(regex, "."));
		} else if ( ret.endsWith("\n") ) {
			return ret.substring(0, ret.length() -1);
		}
		return ret;
	}
	

	//=========================================================================
	public static byte LoginDrive(String drive) {
	 	byte loggedin = ERROR;
	 	newPasswort = Global.prefPasswort;
		while (loggedin == ERROR) {
	    	// Login PLC or SYS
			
			/** 
			 * Leeres Passwort ausfiltern! 
			 * Vermutlich Bug der iTNC530 das mit leerem Passwort auf PLC:\ 
			 * eingeloggt werden kann
			 **/
			if ( newPasswort.isEmpty() == false) {
				if (drive.equalsIgnoreCase("PLC:")) {
					loggedin = Login(PW_FILEPLC, newPasswort);
				} else if (drive.equalsIgnoreCase("LOG:")) {
					loggedin = Login(PW_FILEPLC, newPasswort);
				} else if (drive.equalsIgnoreCase("SYS:")) {
					loggedin = Login(PW_FILESYS, newPasswort);
				} else {
					loggedin = OK;
				}
			}
			if ( loggedin == TIMEOUT ) { 
				return TIMEOUT; 
			} else if (loggedin == ERROR) {
				newPasswort = SyncWithUi.Passwort(newPasswort);
				if (newPasswort == null) {
					return ERROR;
				}
			}
		}
		return OK;
	}

	
	//=========================================================================
	public static byte Login(String logon,String Password) {
		String s = "A_LG".concat(logon).concat(CHAR0);
		if ( Password.isEmpty() == false ) {
			s = s.concat(Password).concat(CHAR0);
		}
		int length = s.length() -4;
		String loByte = Character.toString (((char) (length & 0xFF)));
		String hiByte = Character.toString (((char) ((length >> 8) & 0xFF)));
		String data = CHAR0.concat(CHAR0).concat(hiByte).concat(loByte).concat(s);
		if ( Send(data) == false ) { return TIMEOUT; }
		if ( LSV_info.equals("T_OK") ) {
			return OK; 
		}
		showLSVError();
		return ERROR; 
	}


	//=========================================================================
	public static byte Logout(String logon) {
		String s = "A_LO";
		if ( logon.length() > 0 ) {
			s = s.concat(logon).concat(CHAR0);
		}
		int length = s.length() -4;
		String loByte = Character.toString (((char) (length & 0xFF)));
		String hiByte = Character.toString (((char) ((length >> 8) & 0xFF)));
		String data = CHAR0.concat(CHAR0).concat(hiByte).concat(loByte).concat(s);
		if ( Send(data) == false ) { return TIMEOUT; }
		if ( LSV_info.equals("T_OK") ) {
			return OK; 
		} else { 
			return ERROR; 
		}
	}


	//=========================================================================
	public static byte getInfo() {
		String data = CHAR0.concat(CHAR0).concat(CHAR0).concat(CHAR0).concat("R_VR");
		if ( Send(data) == false ) { return TIMEOUT; }
		if ( LSV_info.equals("S_VR") ) {
			String split[] = LSV_data.split(CHAR0);
			int l = split.length;
			if ( l >= 0 ) { Steuerung = split[0]; }
			if ( l >= 1 ) { NcVersion = split[1]; }
			if ( l >= 2 ) { PlcVersion = split[2]; }
			if ( l >= 3 ) { Zusatzoptionen = split[3]; }
			return OK; 
		} else { 
			showLSVError();
			return ERROR; 
		}
	}


	//=========================================================================
	public static byte SetSysCmd(int cmd) {
		String data = CHAR0 + CHAR0 + CHAR0 + (char)2 + "C_CC" + CHAR0 + (char)cmd;
		if ( Send(data) == false ) { return TIMEOUT; }
		if ( LSV_info.equals("T_OK") ) {
			return OK; 
		} else { 
			showLSVError();
			return ERROR; 
		}
	}


	//=========================================================================
	public static byte GetSysPar() {
		String data = CHAR0.concat(CHAR0).concat(CHAR0).concat(CHAR0).concat("R_PR");
		if ( Send(data) == false ) { return TIMEOUT; }
		if ( LSV_info.equals("S_PR") ) {
			LSV_SysParam.makeParam(LSV_data);
			return OK; 
		} else { 
			showLSVError();
			return ERROR; 
		}
	}

	
	//=========================================================================
	public static byte ChDir(String directory) {
		String s = "C_DC".concat(directory).concat(CHAR0);
		int length = (s.length()-4);
		String loByte = Character.toString (((char) (length & 0xFF)));
		String hiByte = Character.toString (((char) ((length >> 8) & 0xFF)));
		String data = CHAR0.concat(CHAR0).concat(hiByte).concat(loByte).concat(s);
		if ( Send(data) == false ) { return TIMEOUT; }
		if ( LSV_info.equals("T_OK") ) {
			return OK; 
		} else { 
			showLSVError();
			return ERROR; 
		}
	}

	
	//=========================================================================
	public static byte MkDir(String directory) {
		String s = "C_DM".concat(directory).concat(CHAR0);
		int length = (s.length()-4);
		String loByte = Character.toString (((char) (length & 0xFF)));
		String hiByte = Character.toString (((char) ((length >> 8) & 0xFF)));
		String data = CHAR0.concat(CHAR0).concat(hiByte).concat(loByte).concat(s);
		if ( Send(data) == false ) { return TIMEOUT; }
		if ( LSV_info.equals("T_OK") ) {
			return OK; 
		} else { 
			showLSVError();
			return ERROR; 
		}
	}


	//=========================================================================
	public static byte FileInfo(String filename) {
		String s = "R_FI".concat(filename).concat(CHAR0);
		int length = (s.length()-4);
		String loByte = Character.toString (((char) (length & 0xFF)));
		String hiByte = Character.toString (((char) ((length >> 8) & 0xFF)));
		String data = CHAR0.concat(CHAR0).concat(hiByte).concat(loByte).concat(s);
		if ( Send(data) == false ) { return TIMEOUT; }
		if ( LSV_info.equals("S_FI") ) {
			return OK;
		} else { 
			showLSVError();
			return ERROR; 
		}
	}


	//=========================================================================
	public static byte DeleteFile(String filename) {
		int length = 0;
		String s = "C_FD".concat(filename).concat(CHAR0);
		length = (s.length()-4);
		String loByte = Character.toString (((char) (length & 0xFF)));
		String hiByte = Character.toString (((char) ((length >> 8) & 0xFF)));
		String data = CHAR0.concat(CHAR0).concat(hiByte).concat(loByte).concat(s);
		if ( Send(data) == false ) { return TIMEOUT; }
		if ( LSV_info.equals("T_OK") ) {
			return OK;
		} else { 
			showLSVError();
			return ERROR; 
		}
	}


	//=========================================================================
	public static byte SendFile(String filename, String pcFile) {

		String s = filename.concat(CHAR0);

		@SuppressWarnings("unused")
		boolean binary = false;
		if (Global.prefAllwaysBinary == false) {
			for ( String binFile : Global.binFiles ) {
				if ( filename.toLowerCase().endsWith(binFile) ){
					s += Character.toString ((char) 1);
					binary = true;
					break;
				}
			}
		} else {
			binary = true;
		}

		int length = s.length();
		String loByte = Character.toString (((char) (length & 0xFF)));
		String hiByte = Character.toString (((char) ((length >> 8) & 0xFF)));
		String data = CHAR0.concat(CHAR0).concat(hiByte).concat(loByte).concat("C_FL").concat(s);
		// Daten Senden/Empfangen
		if ( Send(data) == false ) {
			// socket Fehler beim senden
			return TIMEOUT; 
		}
		if (LSV_info.equals("T_OK")) {
			try {
				in = new InputStreamReader(new FileInputStream(pcFile), "ISO-8859-1");
			} catch (UnsupportedEncodingException e2) {
				SyncWithUi.ConsolePrintlnOkError( false, (char)0 );
				SyncWithUi.ConsolePrintln("Datei-Encoding nicht unterstützt", 0);
				return ERROR;
			} catch (FileNotFoundException e2) {
				SyncWithUi.ConsolePrintlnOkError( false, (char)0 );
				SyncWithUi.ConsolePrintln("Datei nicht gefunden", 0);
				return ERROR;
			}
			try{
				char[] buffer = new char[BufferLength];
				while ((length = in.read(buffer)) != -1) {

					//Buffer in String wandeln
					s = String.valueOf(buffer,0,length);
					
					if ( binary = false ) {
					//	Zeilenumbrüche entfernen und durch char 0 ersetzen
						s = s.replaceAll("\r\n", CHAR0);
						s = s.replaceAll("\n\r", CHAR0);
						//s = s.replaceAll("[\r|\n]", CHAR0);
					}

					// String-Länge in hi & low Byte ausgeben 
					length = s.length();
					loByte = Character.toString (((char) (length & 0xFF)));
					hiByte = Character.toString (((char) ((length >> 8) & 0xFF)));
					
					// S_FL Kommando erstellen
					data = CHAR0.concat(CHAR0).concat(hiByte).concat(loByte).concat("S_FL").concat(s);
					
					// Daten senden
					if ( Send(data) == false ) {
						// socket Fehler beim senden
						in.close();
						return ERROR; 
					} else if ( LSV_info.equals("T_OK") == false) {
						// senden war fehlerhaft
						showLSVError();
						in.close();
						return ERROR;
					}
				}
				
				// buffer löschen und Datei schliessen
				buffer = null;
				in.close();
				
				// File Done
				data = CHAR0.concat(CHAR0).concat(CHAR0).concat(CHAR0).concat("T_FD");
				if ( Send(data) == false ) { return ERROR; }
				if ( LSV_info.equals("T_OK") == false ) {
					showLSVError();
					return ERROR; 
				}
				return OK; 
			} catch ( IOException e ) {
				SyncWithUi.ConsolePrintln("exception ".concat(e.getMessage()),Console.RED);
				if ( in != null ) {
					try {
						in.close();
					} catch ( IOException e1 ) { 
						
					}
				return ERROR;
				}
			}
		} 
		showLSVError();
		return ERROR;
	}



	//=========================================================================
	public static byte ReceiveFile(String filename, String pcFile) {

		String s = filename.concat(CHAR0);

		@SuppressWarnings("unused")
		boolean binary = false;
		if (Global.prefAllwaysBinary == false) {
			for ( String binFile : Global.binFiles ) {
				if ( filename.toLowerCase().endsWith(binFile) ){
					s += Character.toString ((char) 1);
					binary = true;
					break;
				}
			}
		} else {
			binary = true;
		}

		// R_FL Kommando erstellen
		int length = s.length();
		String loByte = Character.toString (((char) (length & 0xFF)));
		String hiByte = Character.toString (((char) ((length >> 8) & 0xFF)));
		String data = CHAR0.concat(CHAR0).concat(hiByte).concat(loByte).concat("R_FL").concat(s);
		try {
			// Daten empfangen
			if ( Send(data) == false ) {
				// socket Fehler beim senden
				in.close();
				return ERROR; 
			} 
			String logfileData = "";
			while ( LSV_info.equals("S_FL") ) {
				logfileData += LSV_data;
				data = CHAR0.concat(CHAR0).concat(CHAR0).concat(CHAR0).concat("T_OK");
	 			if ( Send(data) == false ) {
					// socket Fehler beim senden
					in.close();
					return ERROR; 
				} 
			}

			if (LSV_info.equals("T_FD") ) {
				LogfileView.show();
				LogfileView.setTextSync(logfileData);
 				return OK;
 			}
	
		} catch ( IOException e ) {
			SyncWithUi.ConsolePrintln("exception ".concat(e.getMessage()),Console.RED);
			if ( in != null ) {
				try {
					in.close();
				} catch ( IOException e1 ) { 
					
				}
			}
			return ERROR;
		}
		showLSVError();
		return ERROR;
	}
	
	
	//=========================================================================
	public static void showLSVError() {
		if ( LSV_data.isEmpty() == false ) {
			@SuppressWarnings("unused")
			char zeichen = LSV_data.charAt(LSV_data.length()-1);
			//SyncWithUi.Notification("Übertragungsfehler","Error " +(int)zeichen + " : " + LSV_Error.getError(zeichen),0);
		}
	}

}
