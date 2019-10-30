package ch.tfischer.hh.preferences;
/**
 * ============================================================================
 * Erstellt die Preferences-Page 
 * und wertet die Buttons Default, Apply und OK aus 
 * ============================================================================
 **/


import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import ch.tfischer.hh.Activator;
import ch.tfischer.hh.data.Global;
import ch.tfischer.hh.toolbar.ToolbarControlContribution;


public class PrefPage extends PreferencePage implements IWorkbenchPreferencePage {

	
	public static final String  PREF_KEY = "qaywsxedcrfvtgbzhnujmikolp";
	public static final String  PREF_PASSWORD     		= "Password";
	public static final String  PREF_PYTHON_PATH  		= "PythonPath";
	public static final String  PREF_LOGFILE_PATH  		= "LogfilePath";
	public static final String  PREF_TRANSFER_FILES		= "transferFiles";
	public static final String  PREF_BIN_FILES    		= "BinFiles";
	public static final String  PREF_ALLWAYS_BINARY     = "AllwaysBinary";
	public static final String  PREF_USE_IP      		= "UseIP";
	public static final String  PREF_IPS                = "IPs";
	public static final String  PREF_PORT         		= "Port";
	public static final String  PREF_LOCALHOST    		= "Localhost";
	public static final String  PREF_NOT_CONFIRM  		= "NotConfirm";
	public static final String  PREF_TELEGRAM     		= "Telegram";
	public static final String  PREF_TELEGRAM_HEX 		= "TelegramHexData";
	
	public static final String  DEFAULT_PASSWORD      	= "";
	public static final String  DEFAULT_PYTHON_PATH   	= "PLC:\\Python\\";
	public static final String  DEFAULT_LOGFILE_PATH   	= "LOG:\\oem\\python\\pythonprint";
	public static final String  DEFAULT_TRANSFER_FILES	= ".bmp .bmx .cfg .chm .gif .ico .jpg .jpeg .png .py .spj .xpm .xrs";
	public static final String  DEFAULT_BIN_FILES     	= ".bmp .bmx .chm .gif .ico .jpg .jpeg .png .xpm";
	public static final boolean DEFAULT_ALLWAYS_BINARY 	= true;
	public static final int     DEFAULT_USE_IP    	    = 0;     	
	public static final String  DEFAULT_IPS[]           = {"192.168.100.2","192.168.56.101"};
	public static final String  DEFAULT_PORT          	= "19000";
	public static final boolean DEFAULT_LOCALHOST  		= false;
	public static final boolean DEFAULT_NOT_CONFIRM  	= false;
	public static final boolean DEFAULT_TELEGRAM     	= false;
	public static final boolean DEFAULT_TELEGRAM_HEX 	= false;

	private Text pythonPath;
	private Text logfilePath;
	private Text transferFiles;
	private Text binFiles;
    private Text password;
    private Text port;
    private Combo cmbIp;
    private Button useIp;
    private Button useLocalhost;
	private Button notConfirm;
    private Button telegram;
    private Button telegramHex;
    private Button allwaysBinary;
    private Button selectedAsBinary;
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

        Bundle bundle = Platform.getBundle("ch.tfischer.hh");
        Version version = bundle.getVersion();
                
	    TabFolder folder = new TabFolder(content, SWT.FILL);
	    folder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 5));
	    
        //=====================================================================
	    //Tab 1 - Netzwerk
        //---------------------------------------------------------------------
	    Composite compositNetwork = new Composite( folder, SWT.NONE );
	    compositNetwork.setLayout( new GridLayout( 4, false ));

	    TabItem tabNetwork = new TabItem(folder, SWT.NONE);
	    tabNetwork.setText("Netzwerk");
	    tabNetwork.setControl(compositNetwork);
	           
        //---------------------------------------------------------------------
        Label separator = new Label(compositNetwork, SWT.HORIZONTAL | SWT.NONE);
        separator.setLayoutData(new GridData(GridData.FILL, SWT.CENTER, false, false, 4, 1));

        //---------------------------------------------------------------------
        useIp = new Button(compositNetwork, SWT.RADIO);
        useIp.setText("");
        useIp.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));

        cmbIp = new Combo(compositNetwork, SWT.DROP_DOWN);
        cmbIp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        cmbIp.setItems(Global.prefIPs);
        cmbIp.select(Global.prefUseIp);

        Button btnDelIp = new Button(compositNetwork,SWT.PUSH | SWT.FLAT);
        btnDelIp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        try {
            btnDelIp.setImage( new Image(compositNetwork.getDisplay(), 
      	                       getClass().getClassLoader().getResourceAsStream("icons/delete.png")));
        } 
        catch(Exception e) {
        	btnDelIp.setText("Delete");
        	
        }
        btnDelIp.setToolTipText("IP-Adresse aus der Auswahlliste löschen");
        btnDelIp.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				if (cmbIp.getText().isEmpty() || cmbIp.indexOf(cmbIp.getText()) < 0 ) {return;}
				if (cmbIp.getItemCount() > 0) {
					int i = cmbIp.getSelectionIndex();
					cmbIp.remove(cmbIp.getText());
					if (i==0) {
						cmbIp.select(i);
					} else {
						cmbIp.select(i-1);
					}
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
		});

        //---------------------------------------------------------------------
        useLocalhost = new Button(compositNetwork, SWT.RADIO);
        useLocalhost.setText("");
        useLocalhost.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));

        Label labelNetwork = new Label(compositNetwork, SWT.NONE);
        labelNetwork.setText("Localhost");
        labelNetwork.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

        //---------------------------------------------------------------------
        separator = new Label(compositNetwork, SWT.HORIZONTAL | SWT.NONE);
        separator.setLayoutData(new GridData(GridData.FILL, SWT.CENTER, false, false, 4, 1));

        //---------------------------------------------------------------------
        labelNetwork = new Label(compositNetwork, SWT.NONE);
        labelNetwork.setText("Port");
        labelNetwork.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));

        port = new Text(compositNetwork, SWT.BORDER);
        port.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));


        //=====================================================================
        //Tab 2 - Allgemein
        //---------------------------------------------------------------------
	    Composite compositGeneral = new Composite( folder, SWT.NONE );
	    compositGeneral.setLayout( new GridLayout( 2, true ));
	    
	    TabItem tabGeneral = new TabItem(folder, SWT.NONE);
	    tabGeneral.setText("Allgemein");
		tabGeneral.setControl(compositGeneral); 
    
        //---------------------------------------------------------------------
		Group groupPython = new Group(compositGeneral, SWT.NONE);
		groupPython.setLayout(new GridLayout(1, true));
		groupPython.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		groupPython.setText("Python-Verzeichnis auf der Steuerung");

        pythonPath = new Text(groupPython, SWT.BORDER);
        pythonPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	    
        //---------------------------------------------------------------------
		Group groupLogfile = new Group(compositGeneral, SWT.NONE);
		groupLogfile.setLayout(new GridLayout(1, true));
		groupLogfile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		groupLogfile.setText("Logfile auf der Steuerung");

        logfilePath = new Text(groupLogfile, SWT.BORDER);
        logfilePath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	    
        //---------------------------------------------------------------------
        Group groupTransferFiles = new Group(compositGeneral, SWT.NONE);
		groupTransferFiles.setLayout(new GridLayout(1, true));
		groupTransferFiles.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		groupTransferFiles.setText("Transfer Dateien");

		transferFiles = new Text(groupTransferFiles, SWT.BORDER);
        transferFiles.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        //---------------------------------------------------------------------
        Group groupFiles = new Group(compositGeneral, SWT.NONE);
		groupFiles.setLayout(new GridLayout(2, false));
		groupFiles.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 2));
		groupFiles.setText("Übertragung im Binärmodus");

        selectedAsBinary = new Button(groupFiles, SWT.RADIO);
        selectedAsBinary.setText(" ");
        selectedAsBinary.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, true, 1, 1));
        
        binFiles = new Text(groupFiles, SWT.BORDER);
        binFiles.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        allwaysBinary = new Button(groupFiles, SWT.RADIO);
        allwaysBinary.setText(" ");
        allwaysBinary.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, true, 1, 1));
        
        Label binaryLabel = new Label(groupFiles, SWT.NONE);
        binaryLabel.setText(" Immer");
        binaryLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));        
	    
        //---------------------------------------------------------------------
        Group groupPassword = new Group(compositGeneral, SWT.NONE);
		groupPassword.setLayout(new GridLayout(1, true));
		groupPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		groupPassword.setText("Passwort");

        password = new Text(groupPassword, SWT.BORDER | SWT.PASSWORD);
        password.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        
        //---------------------------------------------------------------------
        notConfirm = new Button(compositGeneral, SWT.CHECK);
        notConfirm.setText("Up/DownLoad ohne Nachfrage");
        notConfirm.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

        
        //=====================================================================
	    //Tab 3 - Debug
        //---------------------------------------------------------------------
 	    Composite compositDebug = new Composite( folder, SWT.NONE );
	    compositDebug.setLayout( new GridLayout( 2, false ));
	    
	    TabItem tabDebug = new TabItem(folder, SWT.NONE);
	    tabDebug.setText("Debug");
	    tabDebug.setControl(compositDebug); 
		
        telegram = new Button(compositDebug, SWT.CHECK);
        telegram.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
        telegram.setText("LSV Telegramme in Konsole ausgeben");
        telegram.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                warningLabel.setVisible(telegram.getSelection());
            }
        });

        telegramHex = new Button(compositDebug, SWT.CHECK);
        telegramHex.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        telegramHex.setText("als HEX-Daten anzeigen");


        //---------------------------------------------------------------------
        warningLabel = new Label(content, SWT.NONE);
        warningLabel.setText("ACHTUNG! LSV Telegramme in der Konsole ausgeben, nur zum Debugen aktivieren!");
        warningLabel.setLayoutData(new GridData(SWT.FILL, SWT.END, true, true, 2, 1));

        separator = new Label(content, SWT.HORIZONTAL | SWT.SEPARATOR);
        separator.setLayoutData(new GridData(GridData.FILL, SWT.CENTER, true, false, 2, 1));
        
        Label pluginVersion = new Label(content, SWT.NONE);
        pluginVersion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        pluginVersion.setText("Version: " + version.getMajor() + "." + version.getMinor() + "." + version.getMicro());

        
        password.setText(Global.prefPasswort);
        binFiles.setText(Global.prefBinFiles);
        allwaysBinary.setSelection(Global.prefAllwaysBinary);
        selectedAsBinary.setSelection(!Global.prefAllwaysBinary);
        transferFiles.setText(Global.prefTransferFiles);
        pythonPath.setText(Global.prefPythonPath);
        logfilePath.setText(Global.prefLogfilePath);
        port.setText(Global.prefPort);
	    useLocalhost.setSelection(false);
	    if (Global.prefLocalhost) {
	    	useIp.setSelection(false);
	    	useLocalhost.setSelection(true);
	    } else {
	    	useIp.setSelection(true);
	    	useLocalhost.setSelection(false);
	    }
        notConfirm.setSelection(Global.prefNotConfirm);
        telegram.setSelection(Global.prefTelegram);
        telegramHex.setSelection(Global.prefTelegramHex);
        warningLabel.setVisible(telegram.getSelection());
        
        return content;
    }

    
    //=========================================================================
    // @see org.eclipse.jface.preference.PreferencePage#performOk()
    @Override
    public boolean performOk() {
    	if (dataOK() == false ){
    		return false;
    	}
    	cmbIpUpdate();

        performApply();
        return super.performOk();
    }

    
    //=========================================================================
    // @see org.eclipse.jface.preference.PreferencePage#performApply()
    @Override
    protected void performApply() {
    	// Passwort sicher speichern 
    	Preferences.setPassword(password.getText());
    	
    	// Pfad anpassen
    	String python = pythonPath.getText().replaceAll("/", "\\");
    	while ( python.endsWith("\\" )){
    		python = python.substring(0, python.length()-1);
    	}
    	python = python.concat("\\");

    	String logfile = logfilePath.getText().replaceAll("/", "\\");

    	// Netzwerk
    	cmbIpUpdate(); 	
    	Global.prefIPs = cmbIp.getItems();
        Global.prefUseIp = cmbIp.indexOf(cmbIp.getText());
        Global.prefLocalhost = useLocalhost.getSelection();
        Global.prefPort = port.getText();
        
        // Allgemein
        Global.prefPythonPath = python;
        Global.prefLogfilePath = logfile;
        Global.prefTransferFiles = transferFiles.getText();
        Global.prefBinFiles = binFiles.getText();
        Global.prefAllwaysBinary = allwaysBinary.getSelection();
    	Global.prefPasswort = password.getText();

        // Debug
        Global.prefTelegram = telegram.getSelection();
        Global.prefTelegramHex = telegramHex.getSelection();

        // 
        Global.prefNotConfirm = notConfirm.getSelection();
        
        // IP & Info updaten
      	Global.IP = getIp();
      	ToolbarControlContribution.setToolbarIp(Global.IP);

		
        // Speichern
    	IPreferenceStore preferenceStore = getPreferenceStore();
        preferenceStore.setValue(PREF_IPS, String.join(""+(char) 0, Global.prefIPs));
        preferenceStore.setValue(PREF_USE_IP, Global.prefUseIp);
        preferenceStore.setValue(PREF_PORT, Global.prefPort);
    	preferenceStore.setValue(PREF_LOCALHOST, Global.prefLocalhost);
        preferenceStore.setValue(PREF_PYTHON_PATH, Global.prefPythonPath);
        preferenceStore.setValue(PREF_LOGFILE_PATH, Global.prefLogfilePath);
        preferenceStore.setValue(PREF_TRANSFER_FILES, Global.prefTransferFiles);
        preferenceStore.setValue(PREF_BIN_FILES, Global.prefBinFiles);
        preferenceStore.setValue(PREF_ALLWAYS_BINARY, Global.prefAllwaysBinary);
        preferenceStore.setValue(PREF_PASSWORD, Preferences.xor(Global.prefPasswort,PREF_KEY));
        preferenceStore.setValue(PREF_TELEGRAM, Global.prefTelegram);
        preferenceStore.setValue(PREF_TELEGRAM_HEX, Global.prefTelegramHex); 
        preferenceStore.setValue(PREF_NOT_CONFIRM, Global.prefNotConfirm);
        
        dataOK();
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
        preferenceStore.setValue(PREF_LOGFILE_PATH, DEFAULT_LOGFILE_PATH);
        preferenceStore.setValue(PREF_TRANSFER_FILES, DEFAULT_TRANSFER_FILES);
        preferenceStore.setValue(PREF_BIN_FILES, DEFAULT_BIN_FILES);
        preferenceStore.setValue(PREF_ALLWAYS_BINARY, DEFAULT_ALLWAYS_BINARY);
        preferenceStore.setValue(PREF_PORT, DEFAULT_PORT);
        preferenceStore.setValue(PREF_LOCALHOST, DEFAULT_LOCALHOST);
      	preferenceStore.setValue(PREF_USE_IP, DEFAULT_USE_IP);
        preferenceStore.setValue(PREF_NOT_CONFIRM, DEFAULT_NOT_CONFIRM);
        preferenceStore.setValue(PREF_TELEGRAM, DEFAULT_TELEGRAM);
        preferenceStore.setValue(PREF_TELEGRAM_HEX, DEFAULT_TELEGRAM_HEX);
        preferenceStore.setValue(PREF_IPS, String.join(""+(char) 0, DEFAULT_IPS));

    	password.setText("");
        pythonPath.setText(DEFAULT_PYTHON_PATH);
        logfilePath.setText(DEFAULT_LOGFILE_PATH);
        transferFiles.setText(DEFAULT_TRANSFER_FILES);
        binFiles.setText(DEFAULT_BIN_FILES);
        allwaysBinary.setSelection(DEFAULT_ALLWAYS_BINARY);
        selectedAsBinary.setSelection(!DEFAULT_ALLWAYS_BINARY);
        cmbIp.setItems(DEFAULT_IPS);
        cmbIp.select(DEFAULT_USE_IP);
        port.setText(DEFAULT_PORT);
	    if (Global.prefLocalhost) {
	        useIp.setSelection(false);
	        useLocalhost.setSelection(true);
	    } else {
	        useIp.setSelection(true);
	        useLocalhost.setSelection(false);
        }
        notConfirm.setSelection(DEFAULT_NOT_CONFIRM);
        telegram.setSelection(DEFAULT_TELEGRAM);
        telegramHex.setSelection(DEFAULT_TELEGRAM_HEX);

        warningLabel.setVisible(telegram.getSelection());

        dataOK();   
    }

    //=========================================================================
    // Prüfen ob eine neue IP eingetragen wurde
    private void cmbIpUpdate() {
    	if (cmbIp.getText().isEmpty()) {
    		return;
    	}
		for (String item : cmbIp.getItems()){
			if (item.equals(cmbIp.getText())){
	    		return;
			}
		}
		cmbIp.add(cmbIp.getText());
		cmbIp.select(cmbIp.getItemCount());
    }
    
    
    //=========================================================================
    // Prüfe Daten 
    private boolean dataOK() {
    	boolean ret = true;
		cmbIp.setBackground(null);
		port.setBackground(null);
		if ( port.getText().isEmpty() ){
			port.setBackground(new Color(null,255,100,100));
			ret = false;
		}
		if ( cmbIp.getText().isEmpty() && useLocalhost.getSelection() == false) {
			cmbIp.setBackground(new Color(null,255,100,100));
			ret = false;
		}
		return ret;
    }
    
    
    //=========================================================================
    // IP die verwendet werden soll ermitteln
    public static String getIp() {
        if (Global.prefLocalhost) {
        	return  "localhost";
        } else {
        	if (Global.prefIPs.length > 0) {
        		return  Global.prefIPs[Global.prefUseIp];
        	} else {
        		return "N/A";
        	}
		}
    }
}