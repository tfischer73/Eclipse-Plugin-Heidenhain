package ch.tfischer.hh.handlers;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

import ch.tfischer.hh.Workbench;
import ch.tfischer.hh.client.Dialog;
import ch.tfischer.hh.client.UpDownLoad;
import ch.tfischer.hh.console.Console;
import ch.tfischer.hh.data.Global;


/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class PopupDownloadHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public PopupDownloadHandler() {

	}

	public Object execute(ExecutionEvent event) throws ExecutionException {	

		if ( UpDownLoad.lsvJob == null || UpDownLoad.lsvJob.getState() == Job.NONE ){

			// Console suchen und Inhalt löschen
			Console.find();
			Console.clear();
	    	
			if (Global.FileList.isEmpty() == false){
				Console.view();		   
				
				Workbench.saveAllFiles(false);
				
                if( Global.prefNotConfirm || Dialog.ConfirmDialog() ){
                	UpDownLoad.Download(Global.IP);
                }
		    } else {
		    	Shell shell = HandlerUtil.getActiveWorkbenchWindowChecked(event).getShell();
			    MessageDialog.openInformation(
					shell,
					"Datei nicht bekannt",
					"Fehler Dateipfad konnte nicht ermittelt werden");			
		    }
		}
		return null;
	}

}

