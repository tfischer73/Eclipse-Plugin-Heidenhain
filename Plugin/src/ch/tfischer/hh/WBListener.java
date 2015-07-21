package ch.tfischer.hh;
/**
 * ============================================================================
 * Stellt sicher das keine null-Pointer exception beim beenden von Eclipse 
 * auftritt
 * ============================================================================
 **/

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;

import ch.tfischer.hh.client.UpDownLoad;

public class WBListener implements IWorkbenchListener {

    @Override
    public void postShutdown(IWorkbench w) {
    }

    @Override
    public boolean preShutdown(IWorkbench w, boolean b) {
		if ( UpDownLoad.lsvJob != null ){
			UpDownLoad.lsvJob.cancel();
			if ( UpDownLoad.lsvJob.getState() != Job.NONE ){
				MessageDialog msg = new MessageDialog( null, "ShutDown", null,
						"Eclipse wird beendet! \n\nDie Verbindung zur Steuerung wird getrennt!", 
						MessageDialog.INFORMATION, new String[] { "OK" }, 0 );
				msg.setBlockOnOpen(false);
				msg.open();
				int timeout = 6000;
				Object o = new Object();
				while (UpDownLoad.lsvJob.getState() != Job.NONE && timeout > 0 ){
					timeout--;
					try {
				        synchronized (o)
				        {
				            o.wait(10);
				        }					
					} catch (InterruptedException e) {
					}
				}
				msg.close();
			}
		}
        return true;
    }
}