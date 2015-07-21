package ch.tfischer.hh.toolbar;

import static ch.tfischer.hh.toolbar.ToolbarPropertyTester.PROPERTY_CAN_DOWNLOAD;
import static ch.tfischer.hh.toolbar.ToolbarPropertyTester.PROPERTY_CAN_DOWNLOAD_POPUP;
import static ch.tfischer.hh.toolbar.ToolbarPropertyTester.PROPERTY_NAMESPACE;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.services.IEvaluationService;


public class ToolbarSelectionListener  implements ISelectionChangedListener{

 
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		requestRefresh();
	}

	public static void requestRefresh() {


		IWorkbench wb = PlatformUI.getWorkbench();
		final IEvaluationService evaluationService = (IEvaluationService) wb.getService(IEvaluationService.class);
		
		if (evaluationService != null) {
			evaluationService.requestEvaluation(PROPERTY_NAMESPACE.concat(".").concat(PROPERTY_CAN_DOWNLOAD));
			evaluationService.requestEvaluation(PROPERTY_NAMESPACE.concat(".").concat(PROPERTY_CAN_DOWNLOAD_POPUP));
		}
	}


}
