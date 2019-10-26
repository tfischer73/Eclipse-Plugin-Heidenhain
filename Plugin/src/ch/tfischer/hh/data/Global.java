package ch.tfischer.hh.data;
/**
 * ============================================================================
 * Globale Variablen
 * ============================================================================
 **/

import java.util.ArrayList;


import ch.tfischer.hh.toolbar.ToolbarSelectionListener;

public class Global {

	public static ArrayList<FileData> FileList = new ArrayList<FileData>(); //Datenliste mit Dateien die Übertragen werden sollen
	public static String newPasswort;
	public static ToolbarSelectionListener Selectionlistener;
	public static String IP = "localhost";  // IP-Adresse
	public static String[] transferFiles;
	public static String[] binFiles;
	public static ArrayList<String> folders = new ArrayList<String>();
	
	// Preferences
	public static String prefPythonPath;
	public static String prefPasswort;
    public static String[] prefIPs;
	public static String prefPort;
	public static int prefUseIp;
	public static boolean prefLocalhost;
	public static boolean prefNotConfirm;
	public static boolean prefTelegram;
	public static boolean prefTelegramHex;
	public static boolean prefAllwaysBinary;
	public static String prefBinFiles;
	public static String prefTransferFiles;
	public static boolean prefMkDir;
	
}
