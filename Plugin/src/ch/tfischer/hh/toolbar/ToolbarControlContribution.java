package ch.tfischer.hh.toolbar;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;



import ch.tfischer.hh.preferences.Preferences;


public class ToolbarControlContribution extends
		WorkbenchWindowControlContribution {

	public static Label label;
		
	public ToolbarControlContribution() {
		// TODO Auto-generated constructor stub
	}

	public ToolbarControlContribution(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}


	@Override
	protected Control createControl(Composite parent)
	{
		String ip;

	    Composite container = new Composite(parent, SWT.BORDER);
	    GridLayout glContainer = new GridLayout();
	    glContainer.marginTop = 0;
	    glContainer.marginBottom = -1;
	    glContainer.marginHeight = 0;
	    glContainer.marginWidth = 0;
	    glContainer.marginLeft = 0;
	    glContainer.marginRight = 0;
	    container.setLayout(glContainer);
	    	    
	    label = new Label(container, SWT.CENTER);
	    
		GridData gdLabel = new GridData();
	    GC gc = new GC(label);
		FontMetrics fm = gc.getFontMetrics();
	    gc.dispose();
	    gdLabel.widthHint = 15 * fm.getAverageCharWidth();;
	    gdLabel.heightHint = -1;
	    gdLabel.horizontalAlignment = SWT.CENTER;
	    gdLabel.verticalAlignment = SWT.CENTER;
	    //gdLabel.grabExcessHorizontalSpace = true; 
	    gdLabel.grabExcessVerticalSpace = true; 
	    label.setLayoutData(gdLabel);

		if ( Preferences.getLocalhost() ) {
			ip = "localhost";
		} else {
			ip = Preferences.getIP();
		}
		label.setText(ip);

		return container;
	}	
	
}
