package ch.tfischer.hh.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import ch.tfischer.hh.console.Console;
import ch.tfischer.hh.console.SyncWithUi;
//import ch.tfischer.hh.data.FileData;
import ch.tfischer.hh.data.Global;
import ch.tfischer.hh.toolbar.ToolbarSelectionListener;

public class Logfile {

	public static Job lsvJob;	// Upload/UpDownLoad Job

	private static SimpleDateFormat now = new SimpleDateFormat("HH:mm:ss");
	
	//=========================================================================
	public static boolean Download(String IP) {
		lsvJob = new Job("UpDownLoad") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				byte ret = LSV_Client.OK;
				int errorCounter = -1;
				Global.folders.clear();
				
			    // Verbinden
				//---------------------------------------------------------------------
				SyncWithUi.ConsolePrintln(now.format(new Date()).concat(" - Verbinde mit ").concat(Global.IP),Console.BLACK);
				// 10sec Timeout
				if ( LSV_Client.Connect(IP,Integer.parseInt(Global.prefPort),10000) ) {
					SyncWithUi.ConsoleClear();
			   	    SyncWithUi.ConsolePrintln(now.format(new Date()).concat(" - Verbunden mit ").concat(Global.IP),Console.BLACK);

					// Login INSPECT
					//---------------------------------------------------------------------
			   	    ret = LSV_Client.Login("INSPECT","");
			   	    if ( ret ==  LSV_Client.TIMEOUT) { return Status.CANCEL_STATUS; }
			   	    if ( ret ==  LSV_Client.OK) {

				   	    // Versions info
						//---------------------------------------------------------------------
				   	 	ret = LSV_Client.getInfo();
				   	    if ( ret ==  LSV_Client.TIMEOUT) { return Status.CANCEL_STATUS; }
				   	    if ( ret ==  LSV_Client.OK) {
				   	 		
					    	// System commands
							//---------------------------------------------------------------------
					   	 	if ( LSV_Client.GetSysPar() == LSV_Client.OK) {
					   	 		
						   	    if ( LSV_SysParam.MaxBlockLen == 4096 || LSV_Client.Steuerung.startsWith("TNC6")  ) {
							    	ret = LSV_Client.SetSysCmd(LSV_Client.LSV2_SET_BUF4096) ;
							   	    if ( ret ==  LSV_Client.OK) {
							    		LSV_Client.BufferLength = 4096-3*54 - 9;
							    	}
						   	    }else if (LSV_SysParam.MaxBlockLen == 2048) {
							    	ret = LSV_Client.SetSysCmd(LSV_Client.LSV2_SET_BUF2048) ;
							   	    if ( ret ==  LSV_Client.OK) {
						   	    		LSV_Client.BufferLength = 2048-2*54 - 9;
						   	    	}
						   	    }else if ( LSV_SysParam.MaxBlockLen == 1024) {
							    	ret = LSV_Client.SetSysCmd(LSV_Client.LSV2_SET_BUF1024) ;
							   	    if ( ret ==  LSV_Client.OK) {
						   	    		LSV_Client.BufferLength = 1024 - 9;
						   	    	}
						   	    }else if ( LSV_SysParam.MaxBlockLen == 512) {
							    	ret = LSV_Client.SetSysCmd(LSV_Client.LSV2_SET_BUF512) ;
							   	    if ( ret ==  LSV_Client.OK) {
						   	    		LSV_Client.BufferLength = 512-9;
						   	    	}
						   	    } else {
						   	    	LSV_Client.BufferLength = 256-9;
						   	    }
						   	    if ( ret ==  LSV_Client.TIMEOUT) { return Status.CANCEL_STATUS; }
						   	    
						   	 	ret =  LSV_Client.SetSysCmd(LSV_Client.LSV2_SECURE_FILE_SEND);
						   	    if ( ret ==  LSV_Client.TIMEOUT) { return Status.CANCEL_STATUS; }
						   	    if ( ret ==  LSV_Client.OK) {
	
							   	    // Login für file transfer
									//---------------------------------------------------------------------
							   	 	ret =  LSV_Client.Login(LSV_Client.PW_FILETRANSFER,"");
							   	    if ( ret ==  LSV_Client.TIMEOUT) { return Status.CANCEL_STATUS; }
							   	    if ( ret ==  LSV_Client.OK) {
							    		
								   	    // Login PLC: oder SYS:
										//---------------------------------------------------------------------
								    	String logfile = Global.prefLogfilePath;
								   	 	ret =  LSV_Client.LoginDrive(logfile.substring(0,4));
								   	    if ( ret ==  LSV_Client.TIMEOUT) { return Status.CANCEL_STATUS; }
								   	    if ( ret ==  LSV_Client.OK) {
							        		errorCounter = 0;
									    	
									    	// Dateien übertragen
											//---------------------------------------------------------------------
											SyncWithUi.ConsolePrintln(now.format(new Date()).concat(" - Read logfile: "),Console.BLACK); 
								    		if ( Global.prefTelegram ) {
								    			SyncWithUi.ConsolePrintln(now.format(new Date()).concat(" - ").concat(logfile),Console.BLACK);
								    		} else {
								    			SyncWithUi.ConsolePrint(now.format(new Date()).concat(" - ").concat(logfile),Console.BLACK, 96);
								    		}
									   	    // Erster versuch die Datei zulesen
								    		ret =  LSV_Client.ReceiveFile( logfile, "" );
									   	    if ( ret ==  LSV_Client.TIMEOUT) { return Status.CANCEL_STATUS; }
									   	    if ( ret == LSV_Client.OK  ) {
							    				SyncWithUi.ConsolePrintlnOkError( true, (char)0 );
									   	    } else {
									   	    	// Fehlgeschlagen Datei existiert nicht
							    				char zeichen = LSV_Client.LSV_data.charAt(LSV_Client.LSV_data.length()-1);
							    				SyncWithUi.ConsolePrintlnOkError( false, zeichen );
								        		errorCounter ++;
								        	}
								    	} 
							        	//LSV_Client.Logout("");
							   	    }
						    	}
					        	Global.FileList.clear();
					   	 	} // 
				   	 	}
				   	    LSV_Client.Logout("");
			   	    }
			   	    LSV_Client.Disconnect();
				}
				Global.FileList.clear();
				String msg = "Dateiübertragung mit Fehler abgebrochen!";
				int color = Console.RED;
				if ( errorCounter == 0 ) {
					msg = "Dateiübertragung ohne Fehler beendet";
					color = Console.GREEN;
				} else if ( errorCounter == 1 ) {
					msg = "Dateiübertragung mit einem Fehler beendet";
				} else if ( errorCounter > 1 ) {
					msg = "Dateiübertragung mit ".concat(String.valueOf(errorCounter)).concat(" Fehlern beendet.");
				}
				SyncWithUi.ConsolePrint( "\n".concat(now.format(new Date())).concat(" - "), Console.BLACK, 0 );
				SyncWithUi.ConsolePrintln( msg , color );
					
				ToolbarSelectionListener.requestRefresh();
				return Status.OK_STATUS;
	        }
		};
		//job.setUser(false);
		//lsvJob.setRule(mutex);
		Console.find();
		Console.view();
		lsvJob.setSystem(true);
		lsvJob.schedule();
		return false;
	}
	
}
