//=============================================================================
//
// Prüft die im Projekt-Explorer ausgewählten Dateien und erstellt ein Array 
// mit den Dateien die übertragen werden können
//
// Legt anhand der Daten fest ob der popup eintrag UpDownLoad Enabled ist  
//
//=============================================================================
package ch.tfischer.hh.dynamic;


import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;



import ch.tfischer.hh.Workbench;
import ch.tfischer.hh.client.UpDownLoad;
import ch.tfischer.hh.data.Global;


public class PopupDownlod extends ContributionItem {

	public static boolean showPopupDownload = false;
	public static boolean showPopupExplorer = false;


	@SuppressWarnings("static-access")
	@Override
	public void fill(Menu menu, int index) {
    	showPopupExplorer = false;		            	
		showPopupDownload = false;

    	IWorkbenchPage page = Workbench.getWorkbenchPage();
		if (page == null){return;}
		IPerspectiveDescriptor perspective = page.getPerspective();
		if (perspective != null & perspective.getLabel().equals("PyDev")){
			Global.FileList.clear();
			ISelection selection = page.getSelection();
			//Console.println(selection.toString());
			if (selection instanceof ITreeSelection){

				ITreeSelection treeSelection = (ITreeSelection) selection;
	            TreePath[] paths = treeSelection.getPaths();

	            if ( paths.length == 1 ) {
            		showPopupExplorer = true;
	            }
	            
	    		if (UpDownLoad.lsvJob == null || UpDownLoad.lsvJob.getState() == Job.NONE){
		           	IResource resource;
		            //FileData fileData = new FileData();

		        	for (TreePath path : paths) {
		        	    if (path != null) {
		        	    	Object object = path.getLastSegment();
		        	        if (object instanceof IAdaptable) {
		        	            IAdaptable adaptable = (IAdaptable) object;
		        	            resource = (IResource) adaptable.getAdapter(IResource.class);
		        	            if ( resource != null ) {
		        	            	if ( resource.getType() == resource.FILE ) {
		        	            		ProjectFiles.extractFileData(resource);		        	            		
		        	            	} else if ( resource.getType() == resource.FOLDER ) {
		        	            		ProjectFiles.getFiles( resource.getProject().getName(), resource.getProjectRelativePath() );
		        	            	} else {
		        	            		ProjectFiles.getFiles( resource.getProject().getName(), null );
			        	            }
		        	            	if (Global.FileList.size() > 0) {
		        	            		showPopupDownload = true;
		        	            	}
		        	            }
		        	        }
		        	    }
		        	}
				}
			}
		}
	}

}
