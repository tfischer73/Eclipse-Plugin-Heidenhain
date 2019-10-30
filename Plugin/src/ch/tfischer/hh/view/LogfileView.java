package ch.tfischer.hh.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;


public class LogfileView extends ViewPart {
		private static Label label;
		private static Text text;
		public LogfileView() {
			super();
		}

		
		public void setFocus() {
			label.setFocus();
		}

		
		public void createPartControl(Composite parent) {
			text = new Text(parent,SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL); // | SWT.READ_ONLY
			text.setSize(parent.getSize());
			text.setText("");
			//text.setLocation(-100, 0);
			text.setSelection(text.getText().length());
			Color color = text.getBackground();
			text.setEditable(false);
			text.setBackground(color);
		}
		
		
		public static void setText(String data) {
			text.setText(data);
			text.setSelection(text.getText().length());
		}
		
		
		public static void setTextSync(String data) {
			Display.getDefault().syncExec(new Runnable() {
					public void run() {
						text.setText(data.replace((char)0, (char) 10));
						text.setSelection(text.getText().length());
					}
				});	 
		}
		
		public static void show() {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("ch.tfischer.hh.view.logfile");
					} catch (PartInitException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		
}