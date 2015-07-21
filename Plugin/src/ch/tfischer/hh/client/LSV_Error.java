package ch.tfischer.hh.client;
/**
 * ============================================================================
 * Gibt den Text zu einer Fehlernummer zurück 
 * ============================================================================
 **/


public class LSV_Error {
	
	private final static char T_ER_BAD_FORMAT             = 20; // Formatfehler im Telegramm 
	private final static char T_ER_UNEXPECTED_TELE        = 21; // gesendetes Telegramm passt nicht zum Protokoll 
	private final static char T_ER_UNKNOWN_TELE           = 22; // Telegrammtyp nicht bekannt 
	private final static char T_ER_NO_PRIV                = 23; // Kein Privileg für Ausführung 
	private final static char T_ER_WRONG_PARA             = 24; // Parameter nicht zulässig 
	private final static char T_ER_BREAK                  = 25; // Vom Benutzer abgebrochen 
	private final static char T_ER_BAD_KEY                = 30; // falsche Schlüsselzahl für Login 
	private final static char T_ER_BAD_FNAME              = 31; // Dateiname / Pfadname nicht zulässig 
	private final static char T_ER_NO_FILE                = 32; // Datei existiert nicht 
	private final static char T_ER_OPEN_FILE              = 33; // Datei kann nicht geöffnet werden 
	private final static char T_ER_FILE_EXISTS            = 34; // Datei existiert bereits 
	private final static char T_ER_BAD_FILE               = 35; // Dateityp darf nicht geladen werden (z.B. Programmname == Userzyklusname) oder Dateityp nicht bekannt oder Dateityp gesperrt 
	private final static char T_ER_NO_DELETE              = 36; // Datei kann nicht gelöscht werden 
	private final static char T_ER_NO_NEW_FILE            = 37; // Datei kann nicht angelegt werden 
	private final static char T_ER_NO_CHANGE_ATT          = 38; // Readonly Attrib. kann nicht verändert werden 
	private final static char T_ER_BAD_EMULATEKEY         = 39; // Tastenemulation fehlerhaft 
	private final static char T_ER_NO_MP                  = 40; // Maschinenparameter nicht vorhanden oder nicht charerpretierbar 
	private final static char T_ER_NO_WIN                 = 41; // falsche Window- oder Screennummer 
	private final static char T_ER_WIN_NOT_AKTIV          = 42; // Window nicht aktiv 
	private final static char T_ER_ANZ                    = 43; // Fehler bei Befehlsabarbeitung in der Anzeigetask 
	private final static char T_ER_FONT_NOT_DEFINED       = 44; // Anfrage eines nicht vorhandenen Fonts 
	private final static char T_ER_FILE_ACCESS            = 45; // Fehler beim Dateizugriff 
	private final static char T_ER_WRONG_DNC_STATUS       = 46; // Telegramm im aktuellen DNC-Zustand nicht zulässig 
	private final static char T_ER_CHANGE_PATH            = 47; // Verzeichniswechsel nicht möglich 
	private final static char T_ER_NO_RENAME              = 48; // Umbenennen nicht möglich 
	private final static char T_ER_NO_LOGIN               = 49; // Login mit angegebenem Privileg nicht möglich (z.B. dieses schon an andere Schnittstelle vergeben) 
	private final static char T_ER_BAD_PARAMETER          = 50; // Parameter im Telegramm nicht o.k. 
	private final static char T_ER_BAD_NUMBER_FORMAT      = 51; // Falsches Zahlenformat 
	private final static char T_ER_BAD_MEMADR_            = 52; // Falsche Speicheradresse 
	private final static char T_ER_NO_FREE_SPACE          = 53; // Nicht genug Speicher 
	private final static char T_ER_DEL_DIR                = 54; // Verzeichnis kann nicht gelöscht werden 
	private final static char T_ER_NO_DIR                 = 55; // Verzeichnis nicht vorhanden 
	private final static char T_ER_OPERATING_MODE         = 56; // Falsche Betriebsart 
	private final static char T_ER_NO_NEXT_ERROR          = 57; // Kein weiterer Fehler erhältlich 
	private final static char T_ER_ACCESS_TIMEOUT         = 58; // Wartezeit beim Abholen von Daten abgelaufen 
	private final static char T_ER_NO_WRITE_ACCESS        = 59; // kein Schreibzugriff (Preset: gelockte Zeilen) 
	private final static char T_ER_STIB                   = 60; // DNC Betrieb kann nicht aufgenommen werden, da Abarbeiten noch aktiv (STIB: Steuerung in Betrieb) 
	private final static char T_ER_REF_NECESSARY          = 61; // Vor DNC Betrieb muß noch Referenz gefahren werden 
	private final static char T_ER_PLC_BUF_FULL           = 62; // PLC Puffer ist voll 
	private final static char T_ER_NOT_FOUND              = 63; // Angeforderte Information nicht verfügbar (TableLine) 
	private final static char T_ER_WRONG_FILE             = 64; // Falscher Dateityp (Versionsinfo in 1. Zeile defekt) 
	private final static char T_ER_NO_MATCH               = 65; // Zu ersetzender PLC Binärcode stimmt nicht mit dem Binärcode auf der Steuerung überein (PLC Debug) 
	private final static char T_ER_TOO_MANY_TPTS          = 66; // Zu viele Trace-Punkte 
	private final static char T_ER_NOT_ACTIVATED          = 67; // Datei kann nicht aktiviert werden 
	private final static char T_ER_DSP_CHANNEL            = 70; // Angegebener DSP Kanal nicht vorhanden 
	private final static char T_ER_DSP_PARA               = 71; // Die gewünschten Daten können nicht gesendet werden 
	private final static char T_ER_OUT_OF_RANGE           = 72; // Parameter ausserhalb des gültigen Bereichs 
	private final static char T_ER_INVALID_AXIS           = 73; // gewählte Achsen ungültig 
	private final static char T_ER_STREAMING_ACTIVE       = 74; // Achs-Streaming bereits aktiv 
	private final static char T_ER_NO_STREAMING_ACTIVE    = 75; // Achs-Streaming nicht aktiv 
	private final static char T_ER_TO_MANY_OPEN_TCP       = 80; // Zu viele TCP Ports auf Steuerung geöffnet 
	private final static char T_ER_NO_FREE_HANDLE         = 81; // Kein freies (LSV-2) Handle 
	private final static char T_ER_PLCMEMREMA_CLEAR       = 82; // Remanenter PLC Speicher wurde gelöscht 
	private final static char T_ER_OSZI_CHSEL             = 83; // Kanal-Auswahl für Remote-Oszilloskop fehlerhaft 
	private final static char LSV2_BUSY                   = 90; // Telegrammübertragung noch aktiv (Backgroundtransfer) 
	private final static char LSV2_X_BUSY                 = 91; // letztes X_PC Telegramm noch nicht quittiert 
	private final static char LSV2_NOCONNECT              = 92; // Keine Verbindung 
	private final static char LSV2_BAD_BACKUP_FILE        = 93; // Format der Backupdatei fehlerhaft 
	private final static char LSV2_RESTORE_NOT_FOUND      = 94; // Wiederherzustellende Datei in Backup nicht gefunden 
	private final static char LSV2_DLL_NOT_INSTALLED      = 95; // ASCII-Binär Konverter DLL nicht installiert  
	private final static char LSV2_BAD_CONVERT_DLL        = 96; // ASCII-Binär Konverter DLL nicht gefunden oder fehlerhaft 
	private final static char LSV2_BAD_BACKUP_LIST        = 97; // Backup-Listdatei fehlerhaft 
	private final static char LSV2_UNKNOWN_ERROR          = 99; // Nicht genauer spezifizierter Fehler 
	private final static char T_BD_NO_NEW_FILE            = 100; // Datei kann nicht geöffnet werden 
	private final static char T_BD_NO_FREE_SPACE          = 101; // nicht genügend Platz für Datei 
	private final static char T_BD_FILE_NOT_ALLOWED       = 102; // Programm und Dateiname stimmen nicht überein 
	private final static char T_BD_BAD_FORMAT             = 103; // kein LF oder T_FD gesendet 
	private final static char T_BD_BAD_BLOCK              = 104; // Fehler in Programmzeile (kann nicht binär gewandelt werden) 
	private final static char T_BD_END_PGM                = 105; // Programmende bereits erreicht 
	private final static char T_BD_ANZ                    = 106; // Fehler bei Befehlsabarbeitung in der Anzeigetask 
	private final static char T_BD_WIN_NOT_DEFINED        = 107; // Window noch gar nicht definiert 
	private final static char T_BD_WIN_CHANGED            = 108; // Window hat sich in Zwischenzeit geändert 
	private final static char T_BD_DNC_WAIT               = 110; // DNC-Puffer voll; Fileübertragung wird unterbrochen 
	private final static char T_BD_CANCELLED              = 111; // Übertragung vom Benutzer abgebrochen 
	private final static char T_BD_OSZI_OVERRUN           = 112; // Datenüberlauf (Baudrate zu niedrig) 
	private final static char T_BD_FD                     = 200; // Blockübertragung beendet (eigentlich kein Fehler) 
	private final static char T_USER_ERROR                = 255; // Fehlernummer, wenn ein Fehlertelegramm der Fehlerkasse 2 ( benutzerdefinierter Klarschriftfehler) empfangen wurde 
	
	public static String getError(char error){
	      switch (error) {
	      case T_ER_BAD_FORMAT          : return("Formatfehler im Telegramm");
	      case T_ER_UNEXPECTED_TELE     : return("gesendetes Telegramm passt nicht zum Protokoll");
	      case T_ER_UNKNOWN_TELE        : return("Telegrammtyp nicht bekannt");
	      case T_ER_NO_PRIV             : return("Kein Privileg für Ausführung");
	      case T_ER_WRONG_PARA          : return("Parameter nicht zulässig");
	      case T_ER_BREAK               : return("Vom Benutzer abgebrochen");
	      case T_ER_BAD_KEY             : return("falsche Schlüsselzahl für Login");
	      case T_ER_BAD_FNAME           : return("Dateiname / Pfadname nicht zulässig");
	      case T_ER_NO_FILE             : return("Datei existiert nicht");
	      case T_ER_OPEN_FILE           : return("Datei kann nicht geöffnet werden");
	      case T_ER_FILE_EXISTS         : return("Datei existiert bereits");
	      case T_ER_BAD_FILE            : return("Dateityp darf nicht geladen werden (z.B. Programmname == Userzyklusname) oder Dateityp nicht bekannt oder Dateityp gesperrt");
	      case T_ER_NO_DELETE           : return("Datei kann nicht gelöscht werden");
	      case T_ER_NO_NEW_FILE         : return("Datei kann nicht angelegt werden");
	      case T_ER_NO_CHANGE_ATT       : return("Readonly Attrib. kann nicht verändert werden");
	      case T_ER_BAD_EMULATEKEY      : return("Tastenemulation fehlerhaft");
	      case T_ER_NO_MP               : return("Maschinenparameter nicht vorhanden oder nicht interpretierbar");
	      case T_ER_NO_WIN              : return("falsche Window- oder Screennummer");
	      case T_ER_WIN_NOT_AKTIV       : return("Window nicht aktiv");
	      case T_ER_ANZ                 : return("Fehler bei Befehlsabarbeitung in der Anzeigetask");
	      case T_ER_FONT_NOT_DEFINED    : return("Anfrage eines nicht vorhandenen Fonts");
	      case T_ER_FILE_ACCESS         : return("Fehler beim Dateizugriff");
	      case T_ER_WRONG_DNC_STATUS    : return("Telegramm im aktuellen DNC-Zustand nicht zulässig");
	      case T_ER_CHANGE_PATH         : return("Verzeichniswechsel nicht möglich");
	      case T_ER_NO_RENAME           : return("Umbenennen nicht möglich");
	      case T_ER_NO_LOGIN            : return("Login mit angegebenem Privileg nicht möglich (z.B. dieses schon an andere Schnittstelle vergeben)");
	      case T_ER_BAD_PARAMETER       : return("Parameter im Telegramm nicht o.k.");
	      case T_ER_BAD_NUMBER_FORMAT   : return("Falsches Zahlenformat");
	      case T_ER_BAD_MEMADR_         : return("Falsche Speicheradresse");
	      case T_ER_NO_FREE_SPACE       : return("Nicht genug Speicher");
	      case T_ER_DEL_DIR             : return("Verzeichnis kann nicht gelöscht werden");
	      case T_ER_NO_DIR              : return("Verzeichnis nicht vorhanden");
	      case T_ER_OPERATING_MODE      : return("Falsche Betriebsart");
	      case T_ER_NO_NEXT_ERROR       : return("Kein weiterer Fehler erhältlich");
	      case T_ER_ACCESS_TIMEOUT      : return("Wartezeit beim Abholen von Daten abgelaufen");
	      case T_ER_NO_WRITE_ACCESS     : return("kein Schreibzugriff (Preset: gelockte Zeilen)");
	      case T_ER_STIB                : return("DNC Betrieb kann nicht aufgenommen werden, da Abarbeiten noch aktiv (STIB: Steuerung in Betrieb)");
	      case T_ER_REF_NECESSARY       : return("Vor DNC Betrieb muß noch Referenz gefahren werden");
	      case T_ER_PLC_BUF_FULL        : return("PLC Puffer ist voll");
	      case T_ER_NOT_FOUND           : return("Angeforderte Information nicht verfügbar (TableLine)");
	      case T_ER_WRONG_FILE          : return("Falscher Dateityp (Versionsinfo in 1. Zeile defekt)");
	      case T_ER_NO_MATCH            : return("Zu ersetzender PLC Binärcode stimmt nicht mit dem Binärcode auf der Steuerung überein (PLC Debug)");
	      case T_ER_TOO_MANY_TPTS       : return("Zu viele Trace-Punkte");
	      case T_ER_NOT_ACTIVATED       : return("Datei kann nicht aktiviert werden");
	      case T_ER_DSP_CHANNEL         : return("Angegebener DSP Kanal nicht vorhanden");
	      case T_ER_DSP_PARA            : return("Die gewünschten Daten können nicht gesendet werden");
	      case T_ER_OUT_OF_RANGE        : return("Parameter ausserhalb des gültigen Bereichs");
	      case T_ER_INVALID_AXIS        : return("gewählte Achsen ungültig");
	      case T_ER_STREAMING_ACTIVE    : return("Achs-Streaming bereits aktiv");
	      case T_ER_NO_STREAMING_ACTIVE : return("Achs-Streaming nicht aktiv");
	      case T_ER_TO_MANY_OPEN_TCP    : return("Zu viele TCP Ports auf Steuerung geöffnet");
	      case T_ER_NO_FREE_HANDLE      : return("Kein freies (LSV-2) Handle");
	      case T_ER_PLCMEMREMA_CLEAR    : return("Remanenter PLC Speicher wurde gelöscht");
	      case T_ER_OSZI_CHSEL          : return("Kanal-Auswahl für Remote-Oszilloskop fehlerhaft");
	      case LSV2_BUSY                : return("Telegrammübertragung noch aktiv (Backgroundtransfer)");
	      case LSV2_X_BUSY              : return("letztes X_PC Telegramm noch nicht quittiert");
	      case LSV2_NOCONNECT           : return("Keine Verbindung");
	      case LSV2_BAD_BACKUP_FILE     : return("Format der Backupdatei fehlerhaft");
	      case LSV2_RESTORE_NOT_FOUND   : return("Wiederherzustellende Datei in Backup nicht gefunden");
	      case LSV2_DLL_NOT_INSTALLED   : return("ASCII-Binär Konverter DLL nicht installiert ");
	      case LSV2_BAD_CONVERT_DLL     : return("ASCII-Binär Konverter DLL nicht gefunden oder fehlerhaft");
	      case LSV2_BAD_BACKUP_LIST     : return("Backup-Listdatei fehlerhaft");
	      case LSV2_UNKNOWN_ERROR       : return("Nicht genauer spezifizierter Fehler");
	      case T_BD_NO_NEW_FILE         : return("Datei kann nicht geöffnet werden");
	      case T_BD_NO_FREE_SPACE       : return("nicht genügend Platz für Datei");
	      case T_BD_FILE_NOT_ALLOWED    : return("Programm und Dateiname stimmen nicht überein");
	      case T_BD_BAD_FORMAT          : return("kein LF oder T_FD gesendet");
	      case T_BD_BAD_BLOCK           : return("Fehler in Programmzeile (kann nicht binär gewandelt werden)");
	      case T_BD_END_PGM             : return("Programmende bereits erreicht");
	      case T_BD_ANZ                 : return("Fehler bei Befehlsabarbeitung in der Anzeigetask");
	      case T_BD_WIN_NOT_DEFINED     : return("Window noch gar nicht definiert");
	      case T_BD_WIN_CHANGED         : return("Window hat sich in Zwischenzeit geändert");
	      case T_BD_DNC_WAIT            : return("DNC-Puffer voll; Fileübertragung wird unterbrochen");
	      case T_BD_CANCELLED           : return("Übertragung vom Benutzer abgebrochen");
	      case T_BD_OSZI_OVERRUN        : return("Datenüberlauf (Baudrate zu niedrig)");
	      case T_BD_FD                  : return("Blockübertragung beendet (eigentlich kein Fehler)");
	      case T_USER_ERROR             : return("Fehlernummer, wenn ein Fehlertelegramm der Fehlerkasse 2 ( benutzerdefinierter Klarschriftfehler) empfangen wurde");
      }
	      return null;
	}
}
