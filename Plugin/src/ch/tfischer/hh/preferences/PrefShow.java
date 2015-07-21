package ch.tfischer.hh.preferences;
/**
 * ============================================================================
 * Öffnet die Einstellungen 
 * ============================================================================
 **/

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.handlers.HandlerUtil;



public class PrefShow extends AbstractHandler {
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveWorkbenchWindowChecked(event).getShell();
		String id = "ch.tfischer.hh.preferencePage";
		PreferencesUtil.createPreferenceDialogOn(shell, id, new String[] {id}, null).open();
		    
		return null;
	}
} 