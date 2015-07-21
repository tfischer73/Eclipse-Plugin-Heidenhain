package ch.tfischer.hh.handlers;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

import ch.tfischer.hh.Workbench;
import ch.tfischer.hh.client.Dialog;
import ch.tfischer.hh.client.UpDownLoad;
import ch.tfischer.hh.console.Console;
import ch.tfischer.hh.data.FileData;
import ch.tfischer.hh.data.Global;


/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class DownloadAsHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public DownloadAsHandler() {
	}
	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */

	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Get the currently selected file from the editor
		if ( UpDownLoad.lsvJob == null || UpDownLoad.lsvJob.getState() == Job.NONE ){
			
			// Console suchen und Inhalt löschen
			Console.find();
			Console.clear();
	
			// Aktive Datei des Editors
			IFile file = Workbench.getFile();

			if(file != null){
				
				// Daten der Datei ermitteln
		        FileData filedata = new FileData();
		        filedata.pcFile = file.getRawLocation().toOSString();
		        filedata.ncFile = Global.prefPythonPath.concat(file.getProjectRelativePath().toOSString());
		        filedata.FileName = file.getName();
	
				Workbench.saveFile(false);
	
		        Global.FileList.clear();
		        Global.FileList.add(filedata);
	
				if ( Global.FileList.isEmpty() == false ){
					Console.view();		   
	
					filedata.ncFile = Dialog.DownloadAsDialog(filedata.ncFile);
					if ( filedata.ncFile == null || filedata.ncFile.isEmpty() ) {
						return null;
					}
					UpDownLoad.Download( Global.IP );

			    } else {
			    	IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
				    MessageDialog.openError(
						window.getShell(),
						"Error",
						"Dateipfad konnte nicht ermittelt werden");			
				}
			}
		}
		return null;
	}
}

