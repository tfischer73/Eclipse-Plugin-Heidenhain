package ch.tfischer.hh.toolbar;


import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import ch.tfischer.hh.client.Logfile;
import ch.tfischer.hh.client.UpDownLoad;
import ch.tfischer.hh.dynamic.PopupDownlod;


public class ToolbarPropertyTester extends PropertyTester {

	public static final String PROPERTY_NAMESPACE = "ch.tfischer.hh";
	public static final String PROPERTY_CAN_DOWNLOAD = "download";
	public static final String PROPERTY_CAN_DOWNLOAD_POPUP = "popupDownload";
	public static final String PROPERTY_EXPLORER_POPUP = "popupExplorer";
	
	
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (PROPERTY_CAN_DOWNLOAD.equals(property)) {
			return enableToolbar();
		}else if (PROPERTY_CAN_DOWNLOAD_POPUP.equals(property)) {
			return PopupDownlod.showPopupDownload;
		}else if (PROPERTY_EXPLORER_POPUP.equals(property)) {
			if (PopupDownlod.showPopupExplorer && System.getProperty("os.name").toLowerCase().startsWith("win")) {
				return true;
			}
		}

		return false;
	}

	protected boolean enableToolbar() {
		IWorkbench wb = PlatformUI.getWorkbench();
		if(wb != null){
			IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
			if(win != null){
				IWorkbenchPage page = win.getActivePage();
				if(page != null){
					IPerspectiveDescriptor perspective = page.getPerspective();
					if(perspective != null){
						if ((UpDownLoad.lsvJob == null || UpDownLoad.lsvJob.getState() == Job.NONE) &&
								(Logfile.lsvJob == null || Logfile.lsvJob.getState() == Job.NONE) && 
								(perspective.getLabel().equals("PyDev"))){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
