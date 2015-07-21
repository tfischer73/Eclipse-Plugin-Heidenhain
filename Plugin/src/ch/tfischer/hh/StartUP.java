package ch.tfischer.hh;


import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IStartup;


import ch.tfischer.hh.console.Console;
import ch.tfischer.hh.data.Global;
import ch.tfischer.hh.preferences.PrefPage;
import ch.tfischer.hh.preferences.Preferences;
import ch.tfischer.hh.toolbar.ToolbarSelectionListener;



public class StartUP implements IStartup {

		 
	@Override
	public void earlyStartup() {
		// Auto-generated method stub
		// Console suchen und Inhalt löschen
		Console.setName("Heidenhain");
		Console.find();
		Console.clear();
	   
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		Global.prefPasswort = Preferences.xor(store.getString(PrefPage.PREF_PASSWORD),PrefPage.PREF_KEY);
		Global.prefPythonPath = store.getString(PrefPage.PREF_PYTHON_PATH);
        Global.prefIP = store.getString(PrefPage.PREF_IP);
        Global.prefPort = store.getString(PrefPage.PREF_PORT);
        Global.prefLocalhost = store.getBoolean(PrefPage.PREF_LOCALHOST);
        Global.prefNotConfirm = store.getBoolean(PrefPage.PREF_NOT_CONFIRM);
        Global.prefTelegram = store.getBoolean(PrefPage.PREF_TELEGRAM);
        Global.prefTelegramHex = store.getBoolean(PrefPage.PREF_TELEGRAM_HEX);
		Global.prefBinFiles = store.getString(PrefPage.PREF_BIN_FILES);
		Global.prefTransferFiles = store.getString(PrefPage.PREF_TRANSFER_FILES);

        // Prüfe ob die Einstellungen geladen wurden und lade sie wenn nötig nach
        // Da Zahlen und Boolean bei nicht vorhanden sein als 0/false gelesen werden,
        // nehmen wir an wenn alle Strings leer sind das kein Einstellung im store vorhanden sind
        if ( Global.prefPythonPath.isEmpty() && Global.prefIP.isEmpty() && Global.prefPort.isEmpty()) {
            if ( store.getString(PrefPage.PREF_LOCALHOST) == "" ) { Global.prefLocalhost = PrefPage.DEFAULT_LOCALHOST;}
            if ( store.getString(PrefPage.PREF_TELEGRAM) == "" ) { Global.prefTelegram = PrefPage.DEFAULT_TELEGRAM;}
            if ( store.getString(PrefPage.PREF_TELEGRAM_HEX) == "" ) { Global.prefTelegramHex = PrefPage.DEFAULT_TELEGRAM_HEX;}
        }
		if ( Global.prefPythonPath == "" ){ Global.prefPythonPath = PrefPage.DEFAULT_PYTHON_PATH;}
		if ( Global.prefIP == "" ) { Global.prefIP = PrefPage.DEFAULT_IP;}
		if ( Global.prefPort == "" ) { Global.prefPort = PrefPage.DEFAULT_PORT;}
		if ( Global.prefBinFiles == "" ) { Global.prefBinFiles = PrefPage.DEFAULT_BIN_FILES;}
		if ( Global.prefTransferFiles == "" ) { Global.prefTransferFiles = PrefPage.DEFAULT_TRANSFER_FILES;}
	
        // Gewählte Netzwerkadresse einstellen
		if ( Global.prefLocalhost ) {
			Global.IP = "localhost";
		} else {
			Global.IP = Global.prefIP;
		}
        Global.Selectionlistener = new ToolbarSelectionListener();
    	Preferences.buildTransferFiles();
    	Preferences.buildBinFiles();

	}
	
}
