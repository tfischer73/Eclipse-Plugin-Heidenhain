package ch.tfischer.hh.handlers;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.jobs.Job;

import ch.tfischer.hh.client.Logfile;
import ch.tfischer.hh.console.Console;
import ch.tfischer.hh.data.Global;


/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class LogfileHandler extends AbstractHandler {

	/**
	 * The constructor.
	 */
	public LogfileHandler() {

	}

	public Object execute(ExecutionEvent event) throws ExecutionException {	

		if ( Logfile.lsvJob == null || Logfile.lsvJob.getState() == Job.NONE ){ 
			// Console suchen und Inhalt löschen
			Console.find();
			Console.clear();
			Logfile.Download(Global.IP);

		}
		
		return null;
	}

}

