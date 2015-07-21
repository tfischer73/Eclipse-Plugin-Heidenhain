package ch.tfischer.hh.client;
/**
 * ============================================================================
 * Erstellt den Abfrage-Dialog und den Eingabe-Dialog
 * ============================================================================
 **/


import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;




import ch.tfischer.hh.data.Global;


public class Dialog {

	
	public static boolean ConfirmDialog(){
		String message = "";
		if (Global.FileList.size() == 1){
			message = "Datei auf Steuerung übertragen? \n\nIP: ".concat(Global.IP);
			message = message.concat("\n\nDatei : ").concat(Global.FileList.get(0).pcFile.replace("/","\\")); 
			message = message.concat("\nnach  : ").concat(Global.FileList.get(0).ncFile).replace("/","\\"); 
		} else {
			message = "Ausgewählte Dateien/Ordner auf Steuerung übertragen? \n";
			message = message.concat("\nIP: ").concat(Global.IP);	
		}
		
		boolean result = MessageDialog.openConfirm(null, "UpDownLoad", message);
		if (result) {
    		return true;		
    	} else {
    		return false;
    	}
	}
	

	public static boolean CreateFolderDialog(String path){
		String message = "";
		message = "Soll das Verzeichnis erstellt werden? \n\n";
		message = message.concat(path);	
		
		boolean result = MessageDialog.openQuestion(null, "Verzeichnis erstellen?", message);
		if (result) {
    		return true;		
    	} else {
    		return false;
    	}
	}
	

	public static String DownloadAsDialog(String path){
		InputDialog inputDlg = new InputDialog(null, "Download nach?", "Neuen Dateipfad und Name eingeben", path, null);
		inputDlg.open();
		String newPath = inputDlg.getValue();
		inputDlg.close();
		return newPath;		
	}
}