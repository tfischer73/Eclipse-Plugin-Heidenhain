package ch.tfischer.hh.handlers;


import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;

import ch.tfischer.hh.Workbench;
import ch.tfischer.hh.data.Global;


/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class PopupExplorerHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public PopupExplorerHandler() {

	}

	@SuppressWarnings("static-access")
	public Object execute(ExecutionEvent event) throws ExecutionException {	

		
		IWorkbenchPage page = Workbench.getWorkbenchPage();
		if (page == null){return null;}
		IPerspectiveDescriptor perspective = page.getPerspective();
		if (perspective != null & perspective.getLabel().equals("PyDev")){
			Global.FileList.clear();
			ISelection selection = page.getSelection();
			//Console.println(selection.toString());
			if (selection instanceof ITreeSelection){

				ITreeSelection treeSelection = (ITreeSelection) selection;
	            TreePath[] paths = treeSelection.getPaths();
	            if ( paths.length == 1 ) {
	        	    if (paths[0] != null) {
	        	    	Object object = paths[0].getLastSegment();
	        	        if (object instanceof IAdaptable) {
	        	            IAdaptable adaptable = (IAdaptable) object;
	        	            IResource resource = (IResource) adaptable.getAdapter(IResource.class);
	        	            if ( resource != null ) {
	        	            	if ( resource.getType() == resource.FILE ) {
	        	    				OpenExplorerFile( resource.getLocation().toOSString() );
	        	            	} else if ( resource.getType() == resource.PROJECT || resource.getType() == resource.FOLDER ) {
	        	    				OpenExplorerPath( resource.getLocation().toOSString() );
		        	            }
	        	            }
	        	        }
	        	    }
	        	}
            }
		}
		return null;
	}

	
	public void OpenExplorerFile( String location ) {
		try {
			new ProcessBuilder( "explorer.exe", "/select,", "\"" + location + "\"" ).start();
		} catch (IOException e) {

		}		
	}

	public void OpenExplorerPath( String location ) {
		try {
			new ProcessBuilder( "explorer.exe", "/e,", "\"" + location + "\"" ).start();
		} catch (IOException e) {

		}		
	}
}

