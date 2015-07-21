package ch.tfischer.hh.client;
/**
 * ============================================================================
 * Erstellt die Passworteingabe 
 * ============================================================================
 **/

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

import ch.tfischer.hh.preferences.Preferences;

public class PasswordDialog extends Dialog {
  private Text txtPassword;
  private Button checkbox;
  private String password = "";

  public PasswordDialog(Shell parentShell) {
    super(parentShell);
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    Composite container = (Composite) super.createDialogArea(parent);
    GridLayout layout = new GridLayout(1, false);
    layout.marginRight = 10;
    layout.marginLeft  = 10;
    layout.marginTop = 10;
    layout.marginBottom = 10;
    layout.verticalSpacing = 10;
    container.setLayout(layout);
    
    Label lblUser = new Label(container, SWT.NONE);
    lblUser.setText("Passwort eingeben!");
    lblUser.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 0, 1));

    txtPassword = new Text(container, SWT.BORDER);
    txtPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 0, 1));
    txtPassword.setEchoChar('*');
    txtPassword.setText(password);

	checkbox = new Button(container,SWT.CHECK);
    checkbox.setText("Passwort speichern");
    checkbox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 0, 1));

    return container;
  }

  // override method to use "Login" as label for the OK button
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    createButton(parent, IDialogConstants.OK_ID, "Login", true);
    createButton(parent, IDialogConstants.CANCEL_ID,
        IDialogConstants.CANCEL_LABEL, false);
  }

  @Override
  protected Point getInitialSize() {
    return new Point(300, 200);
  }

  @Override
  protected void okPressed() {
    // Copy data from SWT widgets into fields on button press.
    // Reading data from the widgets later will cause an SWT
    // widget diposed exception.
    password = txtPassword.getText();
    if ( password.isEmpty() ) { return; }
    if ( checkbox.getSelection() ){
    	Preferences.setPassword(password);
    }
    super.okPressed();
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

} 