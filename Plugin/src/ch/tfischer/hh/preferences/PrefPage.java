package ch.tfischer.hh.preferences;
/**
 * ============================================================================
 * Erstellt die Preferences-Page 
 * und wertet die Buttons Default, Apply und OK aus 
 * ============================================================================
 **/


import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;






import ch.tfischer.hh.Activator;
import ch.tfischer.hh.data.Global;
import ch.tfischer.hh.toolbar.ToolbarControlContribution;


public class PrefPage extends PreferencePage implements IWorkbenchPreferencePage {

	
	public static final String PREF_KEY = "qaywsxedcrfvtgbzhnujmikolp";
	public static final String PREF_PASSWORD     		= "Password";
	public static final String PREF_PYTHON_PATH  		= "PythonPath";
	public static final String PREF_TRANSFER_FILES		= "transferFiles";
	public static final String PREF_BIN_FILES    		= "BinFiles";
	public static final String PREF_IP           		= "IP";
	public static final String PREF_PORT         		= "Port";
	public static final String PREF_LOCALHOST    		= "Localhost";
	public static final String PREF_NOT_CONFIRM  		= "NotConfirm";
	public static final String PREF_TELEGRAM     		= "Telegram";
	public static final String PREF_TELEGRAM_HEX 		= "TelegramHexData";
	
	public static final String DEFAULT_PASSWORD      	= "";
	public static final String DEFAULT_PYTHON_PATH   	= "PLC:\\Python\\";
	public static final String DEFAULT_TRANSFER_FILES	= ".bmp .bmx .cfg .chm .gif .ico .jpg .jpeg .png .py .spj .xpm .xrs";
	public static final String DEFAULT_BIN_FILES     	= ".bmp .bmx .chm .gif .ico .jpg .jpeg .png .xpm";
	public static final String DEFAULT_IP            	= "192.168.100.2";
	public static final String DEFAULT_PORT          	= "19000";
	public static final boolean DEFAULT_LOCALHOST    	= true;
	public static final boolean DEFAULT_NOT_CONFIRM  	= false;
	public static final boolean DEFAULT_TELEGRAM     	= false;
	public static final boolean DEFAULT_TELEGRAM_HEX 	= false;

	private Text pythonPath;
	private Text transferFiles;
	private Text binFiles;
    private Text password;
    private Text ip;
    private Text port;
    private Button useIp;
    private Button useLocalhost;
	private Button notConfirm;
    private Button telegram;
    private Button telegramHex;
    private Label warningLabel;

    public PrefPage() {
    }

    
    //=========================================================================
    // @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
    @Override
    public void init(IWorkbench workbench) {
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
    }


    //=========================================================================
    // @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
    @Override
    protected Control createContents(Composite parent) {
        Composite content = new Composite(parent, SWT.NONE);
        GridLayout gridLayout = new GridLayout(2,true);
        //gridLayout.numColumns = 2;
        gridLayout.marginTop = 5;
        gridLayout.marginBottom = 5;
        gridLayout.marginRight = 10;
        gridLayout.marginLeft = 5;
        gridLayout.verticalSpacing =10;
        content.setLayout(gridLayout);

        Group groupPython = new Group(content, SWT.NONE);
		groupPython.setLayout(new GridLayout(1, true));
		groupPython.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		groupPython.setText("Python-Verzeichnis auf der Steuerung");

        pythonPath = new Text(groupPython, SWT.BORDER);
        pythonPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Group groupTransferFiles = new Group(content, SWT.NONE);
		groupTransferFiles.setLayout(new GridLayout(1, true));
		groupTransferFiles.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		groupTransferFiles.setText("Transfer Dateien");

		transferFiles = new Text(groupTransferFiles, SWT.BORDER);
        transferFiles.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Group groupFiles = new Group(content, SWT.NONE);
		groupFiles.setLayout(new GridLayout(1, true));
		groupFiles.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		groupFiles.setText("Übertragung im Binärmodus");

        binFiles = new Text(groupFiles, SWT.BORDER);
        binFiles.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Group groupPassword = new Group(content, SWT.NONE);
		groupPassword.setLayout(new GridLayout(1, true));
		groupPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		groupPassword.setText("Passwort");

        password = new Text(groupPassword, SWT.BORDER | SWT.PASSWORD);
        password.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Group network = new Group(content, SWT.NONE);
		network.setLayout(new GridLayout(3, true));
		network.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		network.setText("Verbindung");

        Label label = new Label(network, SWT.NONE);
        label.setText("IP-Adresse");
        label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        ip = new Text(network, SWT.BORDER | SWT.BORDER);
        ip.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

        label = new Label(network, SWT.NONE);
        label.setText("Portnummer");
        label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        port = new Text(network, SWT.BORDER | SWT.BORDER);
        port.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        
		
        useLocalhost = new Button(network, SWT.RADIO);
        useLocalhost.setText("Localhost");
        useLocalhost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        useIp = new Button(network, SWT.RADIO);
        useIp.setText("IP-Adresse");
        useIp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        notConfirm = new Button(content, SWT.CHECK);
        notConfirm.setText("Up/DownLoad ohne Nachfrage");
        notConfirm.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

        telegram = new Button(content, SWT.CHECK);
        telegram.setText("LSV Telegramme in Konsole ausgeben");
        telegram.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
            	telegramHex.setVisible(telegram.getSelection());
                warningLabel.setVisible(telegram.getSelection());
            }
        });

        telegramHex = new Button(content, SWT.CHECK);
        telegramHex.setText("als HEX-Daten anzeigen");

        warningLabel = new Label(content, SWT.NONE);
        warningLabel.setText("ACHTUNG! LSV Telegramme in der Konsole ausgeben nur zum Debugen aktivieren!");
        warningLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.END, false, true, 2, 1));

        password.setText(Global.prefPasswort);
        binFiles.setText(Global.prefBinFiles);
        transferFiles.setText(Global.prefTransferFiles);
        pythonPath.setText(Global.prefPythonPath);
        ip.setText(Global.prefIP);
        port.setText(Global.prefPort);
        useLocalhost.setSelection(Global.prefLocalhost);
        useIp.setSelection(!Global.prefLocalhost);
        notConfirm.setSelection(Global.prefNotConfirm);
        telegram.setSelection(Global.prefTelegram);
        telegramHex.setSelection(Global.prefTelegramHex);
        telegramHex.setVisible(telegram.getSelection());
        warningLabel.setVisible(telegram.getSelection());
        
        return content;
    }

    
    //=========================================================================
    // @see org.eclipse.jface.preference.PreferencePage#performOk()
    @Override
    public boolean performOk() {
    	if ( port.getText().isEmpty() ){
    		port.setBackground(new Color(null,255,100,100));
    		return false;
    	}
    	if ( ip.getText().isEmpty() ){
    		ip.setBackground(new Color(null,255,100,100));
    		return false;
    	}
        performApply();
        return super.performOk();
    }

    
    //=========================================================================
    // @see org.eclipse.jface.preference.PreferencePage#performApply()
    @Override
    protected void performApply() {
    	// Passwort sicher speichern 
    	Preferences.setPassword(password.getText());

    	String python = pythonPath.getText().replaceAll("/", "\\");
    	while ( python.endsWith("\\" )){
    		python = python.substring(0, python.length()-1);
    	}
    	python = python.concat("\\");
        // Speichern
    	IPreferenceStore preferenceStore = getPreferenceStore();
        preferenceStore.setValue(PREF_PASSWORD, Preferences.xor(password.getText(),PREF_KEY));
        preferenceStore.setValue(PREF_PYTHON_PATH, python);
        preferenceStore.setValue(PREF_TRANSFER_FILES, transferFiles.getText());
        preferenceStore.setValue(PREF_BIN_FILES, binFiles.getText());
        preferenceStore.setValue(PREF_IP, ip.getText());
        preferenceStore.setValue(PREF_PORT, port.getText());
        preferenceStore.setValue(PREF_LOCALHOST, useLocalhost.getSelection());
        preferenceStore.setValue(PREF_NOT_CONFIRM, notConfirm.getSelection());
        preferenceStore.setValue(PREF_TELEGRAM, telegram.getSelection());
        preferenceStore.setValue(PREF_TELEGRAM_HEX, telegramHex.getSelection());
        
        Global.prefPasswort = password.getText();
        Global.prefPythonPath = python;
        Global.prefTransferFiles = transferFiles.getText();
        Global.prefBinFiles = binFiles.getText();
        Global.prefIP = ip.getText();
        Global.prefPort = port.getText();
        Global.prefLocalhost = useLocalhost.getSelection();
        Global.prefNotConfirm = notConfirm.getSelection();
        Global.prefTelegram = telegram.getSelection();
        Global.prefTelegramHex = telegramHex.getSelection();
        
        Global.prefLocalhost = useLocalhost.getSelection();
		if ( Global.prefLocalhost ) {
			Global.IP = "localhost";
		} else {
			Global.IP = Global.prefIP;
		}
		ToolbarControlContribution.label.setText(Global.IP);
		
		ip.setBackground(null);
		port.setBackground(null);
    	if ( port.getText().isEmpty() ){
    		port.setBackground(new Color(null,255,100,100));
    	}
    	if ( ip.getText().isEmpty() ){
    		ip.setBackground(new Color(null,255,100,100));
    	}
    	
    	Preferences.buildTransferFiles();
    	Preferences.buildBinFiles();
    }


    
    //=========================================================================
    // @see org.eclipse.jface.preference.PreferencePage#performDefaults()
    @Override
    protected void performDefaults() {
    	
        String pw = password.getText();
        pw = Preferences.xor("807667",PREF_KEY);
        
    	IPreferenceStore preferenceStore = getPreferenceStore();
        preferenceStore.setValue(PREF_PASSWORD, pw);
        preferenceStore.setValue(PREF_PYTHON_PATH, DEFAULT_PYTHON_PATH);
        preferenceStore.setValue(PREF_TRANSFER_FILES, DEFAULT_TRANSFER_FILES);
        preferenceStore.setValue(PREF_BIN_FILES, DEFAULT_BIN_FILES);
        preferenceStore.setValue(PREF_IP, DEFAULT_IP);
        preferenceStore.setValue(PREF_PORT, DEFAULT_PORT);
        preferenceStore.setValue(PREF_LOCALHOST, DEFAULT_LOCALHOST);
        preferenceStore.setValue(PREF_NOT_CONFIRM, DEFAULT_NOT_CONFIRM);
        preferenceStore.setValue(PREF_TELEGRAM, DEFAULT_TELEGRAM);
        preferenceStore.setValue(PREF_TELEGRAM_HEX, DEFAULT_TELEGRAM_HEX);

    	password.setText("");
        pythonPath.setText(DEFAULT_PYTHON_PATH);
        transferFiles.setText(DEFAULT_TRANSFER_FILES);
        binFiles.setText(DEFAULT_BIN_FILES);
        ip.setText(DEFAULT_IP);
        port.setText(DEFAULT_PORT);
        useLocalhost.setSelection(DEFAULT_LOCALHOST);
        useIp.setSelection(!DEFAULT_LOCALHOST);
        notConfirm.setSelection(DEFAULT_NOT_CONFIRM);
        telegram.setSelection(DEFAULT_TELEGRAM);
        telegramHex.setSelection(DEFAULT_TELEGRAM_HEX);

        warningLabel.setVisible(telegram.getSelection());
        
    }

}