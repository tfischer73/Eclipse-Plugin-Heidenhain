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
		if ( Preferences.getLocalhost() ) {
			ip = "localhost";
		} else {
			ip = Preferences.getIPs()[Preferences.getUseIp()];
		}
		
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
	    gdLabel.widthHint = (int) (21 * fm.getAverageCharacterWidth());
	    gdLabel.heightHint = -1;
	    gdLabel.horizontalAlignment = SWT.CENTER;
	    gdLabel.verticalAlignment = SWT.CENTER;
	    //gdLabel.grabExcessHorizontalSpace = true; 
	    gdLabel.grabExcessVerticalSpace = true; 

	    label.setLayoutData(gdLabel);
		label.setText(ip);
		
		
		/* Test
		label.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				Console.println("mouseUp",0);
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				Console.println("mouseDown"+e,0);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        */
		
		return container;
	}	
	
	public static void setToolbarIp(String ip) {
		label.setText(ip);
	}

}
