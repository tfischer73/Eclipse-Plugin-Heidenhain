package ch.tfischer.hh.client;
/**
 * ============================================================================
 * Schlüsselt die Systemparameter in einzelne Variablen auf
 * ============================================================================
 **/


public class LSV_SysParam {

	public static long markerstart;
	public static long markers;
	public static long inputstart;
	public static long inputs;
	public static long outputstart;
	public static long outputs;
	public static long counterstart;
	public static long counters;
	public static long timerstart;
	public static long timers;
	public static long wordstart;
	public static long words;
	public static long stringstart;
	public static long strings;
	public static byte stringlength;
	public static byte reserved57;
	public static byte reserved58;
	public static byte reserved59;
	public static byte reserved60;
	public static byte reserved61;
	public static byte reserved62;
	public static byte reserved63;
	public static long inputwordstart;
	public static long inputwords;
	public static long outputwordstart;
	public static long outputwords;
	public static long reserved80;
	public static long reserved84;
	public static long reserved88;
	public static long reserved92;
	public static byte lsv2version;
	public static byte lsv2version_flags;
	public static long MaxBlockLen;
	public static byte HdhBinVersion;
	public static byte HdhBinRevision;
	public static byte IsoBinVersion;
	public static byte IsoBinRevision;
	public static long HardwareVersion;
	public static long lsv2version_flags_ex;
	public static long MaxTraceLine;
	public static long ScopeChannels;
	public static long PWEncryptionKey;      	

	public static byte[] buffer;

	public static void makeParam(String param){
		// programmierplatz TNC640 sendet nur 120 Bytes
		// damit kein Fehler bei bei zukurzem String entsteht auf 124 auffüllen
		while ( param.length() < 124) {
			param = param.concat(" ");
		}
		buffer = param.getBytes();
		markerstart          = makeLong(param,  0); 
		markers              = makeLong(param,  4);
		inputstart           = makeLong(param,  8);
		inputs               = makeLong(param, 12);
		outputstart          = makeLong(param, 16);
		outputs              = makeLong(param, 20);
		counterstart         = makeLong(param, 24);
		counters             = makeLong(param, 28);
		timerstart           = makeLong(param, 32);
		timers               = makeLong(param, 36);
		wordstart            = makeLong(param, 40);
		words                = makeLong(param, 44);
		stringstart          = makeLong(param, 48);
		strings              = makeLong(param, 52);
		stringlength         = buffer[56]; 
		reserved57           = buffer[57];
		reserved58           = buffer[58];
		reserved59           = buffer[59];
		reserved60           = buffer[60];
		reserved61           = buffer[61];
		reserved62           = buffer[62];
		reserved63           = buffer[63];
		inputwordstart       = makeLong(param, 64);
		inputwords           = makeLong(param, 68);
		outputwordstart      = makeLong(param, 72);
		outputwords          = makeLong(param, 76);
		reserved80           = makeLong(param, 80);
		reserved84           = makeLong(param, 84);
		reserved88           = makeLong(param, 88);
		reserved92           = makeLong(param, 92);
		lsv2version          = buffer[96];
		lsv2version_flags    = buffer[97];
		MaxBlockLen          = makeInteger(param, 98);
		HdhBinVersion        = buffer[100];
		HdhBinRevision       = buffer[101];
		IsoBinVersion        = buffer[102];
		IsoBinRevision       = buffer[103];
		HardwareVersion      = makeLong(param,104);
		lsv2version_flags_ex = makeLong(param,108);
		MaxTraceLine         = makeInteger(param,112);
		ScopeChannels        = makeInteger(param, 116);
		PWEncryptionKey		 = makeLong(param, 120);
	}
	
	private static int makeInteger(String data, int pos){
		return ((buffer[pos] & 0xFF) << 8 + (buffer[pos+1] & 0xFF));
	}

	private static int makeLong(String data, int pos){
		return ((buffer[pos] & 0xFF) << 24 | (buffer[pos + 1] & 0xFF) << 16 | (buffer[pos + 2] & 0xFF) << 8 | (buffer[pos + 1] & 0xFF));
	}
}
