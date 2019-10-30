package ch.tfischer.hh.preferences;
/**
 *=============================================================================
 * Funktionen zum speichern der Preferences
 *============================================================================= 
 **/
import org.eclipse.jface.preference.IPreferenceStore;

import ch.tfischer.hh.Activator;
import ch.tfischer.hh.data.Global;

public class Preferences {


	//=========================================================================
	public static void setPassword(String password) {
		Global.prefPasswort = password;
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
        preferenceStore.setValue(PrefPage.PREF_PASSWORD, Preferences.xor(password,PrefPage.PREF_KEY));
	}
	
	//=========================================================================
	public static String getPassword() {
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		return Preferences.xor(preferenceStore.getString(PrefPage.PREF_PASSWORD),PrefPage.PREF_KEY);
	}


	//=========================================================================
	public static void setPythonPath(String pythonPath) {
		Global.prefPythonPath = pythonPath;
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		preferenceStore.setValue(PrefPage.PREF_PYTHON_PATH, pythonPath);
	}

	//=========================================================================
	public static String getPythonPath() {
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		return preferenceStore.getString(PrefPage.PREF_PYTHON_PATH);
	}


	//=========================================================================
	public static void setLogfilePath(String logfile) {
		Global.prefLogfilePath = logfile;
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		preferenceStore.setValue(PrefPage.PREF_LOGFILE_PATH, logfile);
	}

	//=========================================================================
	public static String getLogfilePath() {
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		return preferenceStore.getString(PrefPage.PREF_LOGFILE_PATH);
	}


	//=========================================================================
	public static void setUseIp(int useIp) {
		Global.prefUseIp = useIp;
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		preferenceStore.setValue(PrefPage.PREF_USE_IP, useIp);
	}

	//=========================================================================
	public static int getUseIp() {
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		return preferenceStore.getInt(PrefPage.PREF_USE_IP);
	}


	//=========================================================================
	public static void setIPs(String[] useIPs) {
		Global.prefIPs = useIPs;
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		preferenceStore.setValue(PrefPage.PREF_IPS, String.join(""+(char)0,useIPs));
	}

	//=========================================================================
	public static String[] getIPs() {
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		return preferenceStore.getString(PrefPage.PREF_IPS).split(""+(char) 0);
	}


	//=========================================================================
	public static void setPort(String port) {
		Global.prefPort = port;
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		preferenceStore.setValue(PrefPage.PREF_PORT, port);
	}

	//=========================================================================
	public static String getPort() {
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		return preferenceStore.getString(PrefPage.PREF_PORT);
	}


	//=========================================================================
	public static void setLocalhost(boolean localhost) {
		Global.prefLocalhost = localhost;
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		preferenceStore.setValue(PrefPage.PREF_LOCALHOST, localhost);
	}

	//=========================================================================
	public static boolean getLocalhost() {
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		return preferenceStore.getBoolean(PrefPage.PREF_LOCALHOST);
	}


	//=========================================================================
	public static void setConfirm(Boolean ask) {
		Global.prefNotConfirm = ask;
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		preferenceStore.setValue(PrefPage.PREF_NOT_CONFIRM, ask);
	}

	//=========================================================================
	public static boolean getConfirm() {
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		return preferenceStore.getBoolean(PrefPage.PREF_NOT_CONFIRM);
	}

	
	//=========================================================================
	public static void setTelegram(boolean telegram) {
		Global.prefTelegram = telegram;
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		preferenceStore.setValue(PrefPage.PREF_TELEGRAM, telegram);
	}

	//=========================================================================
	public static boolean getTelegram() {
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		return preferenceStore.getBoolean(PrefPage.PREF_TELEGRAM);
	}


	//=========================================================================
	public static void setTelegramHex(boolean telegramHex) {
		Global.prefTelegramHex = telegramHex;
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		preferenceStore.setValue(PrefPage.PREF_TELEGRAM_HEX, telegramHex);
	}

	//=========================================================================
	public static boolean getTelegramHex() {
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		return preferenceStore.getBoolean(PrefPage.PREF_TELEGRAM_HEX);
	}


	public static String xor(String pw, String key) {
		final byte[] input = pw.getBytes();
		final byte[] secret = key.getBytes();
	    final byte[] output = new byte[input.length];
	    if (secret.length == 0) {
	        throw new IllegalArgumentException("empty security key");
	    }
	    int spos = 0;
	    for (int pos = 0; pos < input.length; ++pos) {
	        output[pos] = (byte) (input[pos] ^ secret[spos]);
	        ++spos;
	        if (spos >= secret.length) {
	            spos = 0;
	        }
	    }
	    return  new String(output);
	}


	//=========================================================================
	public static void setBinFiles(String extensions) {
		Global.prefBinFiles = extensions;
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		preferenceStore.setValue(PrefPage.PREF_BIN_FILES, extensions);
	}

	//=========================================================================
	public static String getBinFiles() {
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		return preferenceStore.getString(PrefPage.PREF_BIN_FILES);
	}


	//=========================================================================
	public static void setAllwaysBinary(boolean allwaysBinary) {
		Global.prefAllwaysBinary = allwaysBinary;
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		preferenceStore.setValue(PrefPage.PREF_ALLWAYS_BINARY, allwaysBinary);
	}

	//=========================================================================
	public static boolean getAllwaysBinary() {
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		return preferenceStore.getBoolean(PrefPage.PREF_ALLWAYS_BINARY);
	}


	//=========================================================================
	public static void buildTransferFiles() {
		if ( Global.prefTransferFiles == null ) {
			return;
		}
		Global.transferFiles = Global.prefTransferFiles.toLowerCase().split(" ");
	}
	

	//=========================================================================
	public static void buildBinFiles() {
		if ( Global.prefBinFiles == null ) {
			return;
		}
		Global.binFiles = Global.prefBinFiles.toLowerCase().split(" ");
	}
	

}
