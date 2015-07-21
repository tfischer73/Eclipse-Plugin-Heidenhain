package ch.tfischer.hh.console;
/**
 * ============================================================================
 * Damit aus einem laufenden Job in  die Konsole geschrieben werden kann,
 * muss das ganze Synchronisiert werden.
 * Diese Klasse stellt die entsprechenden Funktionen bereit
 * ============================================================================
 **/

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

import ch.tfischer.hh.client.Dialog;
import ch.tfischer.hh.client.LSV_Error;
import ch.tfischer.hh.client.PasswordDialog;
import ch.tfischer.hh.data.Global;

public class SyncWithUi {

	private static	String retString; 
	private static	Boolean retBool; 

	//=========================================================================
	public static String Passwort(String passwort) {
		retString = null;
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				PasswordDialog dialog = new PasswordDialog(null);
			    dialog.setPassword(passwort);
			    // get the new values from the dialog
				if (dialog.open() == Window.OK) {
					retString = dialog.getPassword();
			    }
		    	dialog.close();
			}				
		});	 
		return retString;
	}
	
	
	//=========================================================================
	public static boolean CreateFolderDialog(String path) {
		retBool = false;
		for ( String folder : Global.folders ) {
			if (path.equals(folder) ) { 
				return false;
			}
		}
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				Global.folders.add(path);
				retBool = Dialog.CreateFolderDialog(path);
			}				
		});	 
		return retBool;
	}
	
	
	//=========================================================================
	public static void ConsolePrintln(String data, int color) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				Console.println(data, color);
		    }
		});	 
	}
	

	//=========================================================================
	public static void ConsolePrintlnOkError(boolean ok, char error) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				Console.printlnOkError(ok);
				if ( error  > 0 ) {
					Console.println("           ".concat(LSV_Error.getError(error)), Console.RED);
				}
		    }
		});	 
	}

	
	//=========================================================================
	public static void ConsolePrint(String data, int color, int length) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				String datan = data;
				while ( length > datan.length()) {
					datan = datan.concat(" ");
				}
				Console.print(datan, color);
		    }
		});	 
	}

	
	//=========================================================================
	public static void ConsoleClear() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				Console.find();
				Console.clear();
		    }
		});	 
	}
	
}
