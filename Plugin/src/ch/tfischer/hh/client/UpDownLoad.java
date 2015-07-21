package ch.tfischer.hh.client;
/**
 * ============================================================================
 * Stellt die Funktion Download bereit
 * 	 - Der Download Prozess wird in einem Job gestartet so das er im 
 *     Hintergrund laufen kann und den Editor nicht behindert.
 *     
 *     Grund dafür war, das bei einem Verbindungsversuch Windows das Programm 
 *     als nicht mehr Funktionsfähig ansah da es bis zu einer Minute nicht 
 *     mehr reagiert.
 * ============================================================================
 **/


import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import ch.tfischer.hh.client.LSV_Client;
import ch.tfischer.hh.console.Console;
import ch.tfischer.hh.console.SyncWithUi;
import ch.tfischer.hh.data.FileData;
import ch.tfischer.hh.data.Global;
import ch.tfischer.hh.toolbar.ToolbarSelectionListener;


public class UpDownLoad {

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
								    	String ncFile = Global.FileList.get(0).ncFile;
								   	 	ret =  LSV_Client.LoginDrive(ncFile.substring(0,4));
								    	ncFile = null;
								   	    if ( ret ==  LSV_Client.TIMEOUT) { return Status.CANCEL_STATUS; }
								   	    if ( ret ==  LSV_Client.OK) {
							        		errorCounter = 0;
									    	
									    	// Dateien übertragen
											//---------------------------------------------------------------------
											SyncWithUi.ConsolePrintln(now.format(new Date()).concat(" - Download: "),Console.BLACK); 
									    	for ( FileData fordata : Global.FileList ) {
									    		if ( Global.prefTelegram ) {
									    			SyncWithUi.ConsolePrintln(now.format(new Date()).concat(" - ").concat(fordata.ncFile),Console.BLACK);
									    		} else {
									    			SyncWithUi.ConsolePrint(now.format(new Date()).concat(" - ").concat(fordata.ncFile),Console.BLACK, 96);
									    		}
										   	    // Erster versuch die Datei zusenden
									    		ret =  LSV_Client.SendFile( fordata.ncFile, fordata.pcFile);
										   	    if ( ret ==  LSV_Client.TIMEOUT) { return Status.CANCEL_STATUS; }
										   	    if ( ret == LSV_Client.OK  ) {
								    				SyncWithUi.ConsolePrintlnOkError( true, (char)0 );
										   	    } else {
										   	    	// Fehlgeschlagen Datei existiert oder Ordner nicht vorhanden

										   	    	ret =  LSV_Client.DeleteFile(fordata.ncFile);
											   	    if ( ret ==  LSV_Client.TIMEOUT) { return Status.CANCEL_STATUS; }
											   	    if ( ret ==  LSV_Client.ERROR) {
									    				char errorDel = LSV_Client.LSV_data.charAt(LSV_Client.LSV_data.length()-1);
											   	    	
											   	    	// Verzeichnis wechseln
											    		ret =  LSV_Client.ChDir(fordata.ncFile.substring(0, fordata.ncFile.lastIndexOf("\\")));
												   	    if ( ret ==  LSV_Client.TIMEOUT) { return Status.CANCEL_STATUS; }
												   	    if ( ret ==  LSV_Client.ERROR) { 
													   	    String path = fordata.ncFile.substring(0, fordata.ncFile.lastIndexOf("\\") +1 );
													   	    
												   	    	char errorChDir = LSV_Client.LSV_data.charAt(LSV_Client.LSV_data.length()-1);
												   	    	// Ordner Information auslesen
												    		ret =  LSV_Client.FileInfo( path );
													   	    if ( ret ==  LSV_Client.TIMEOUT) { return Status.CANCEL_STATUS; }
													   	    if ( ret ==  LSV_Client.ERROR) {
													   	    	// Ordener nicht Vorhanden
													   	    	// Funktion liefert "Datei existiert nicht" zurück!
													   	    	// ersetzen durch Fehler "Verzeichnis nicht vorhanden"
													   	    	errorChDir = 55;
														   	    if ( SyncWithUi.CreateFolderDialog(path) ) {
														   	    	//ret =  LSV_Client.MkDir( path );
														   	    	ret = makeDir(path);
														   	    	if ( ret ==  LSV_Client.TIMEOUT) { return Status.CANCEL_STATUS; }
														   	    	if ( ret ==  LSV_Client.ERROR) {
															   	    	char zeichen = LSV_Client.LSV_data.charAt(LSV_Client.LSV_data.length()-1);
															   			SyncWithUi.ConsolePrintlnOkError( false, zeichen );
														        		errorCounter ++;
														   	    	} else {
															   	      	ret =  LSV_Client.SendFile( fordata.ncFile, fordata.pcFile);
															   		    if ( ret ==  LSV_Client.TIMEOUT) { return Status.CANCEL_STATUS; }
															   		    if ( ret ==  LSV_Client.ERROR) {
																   			char zeichen = LSV_Client.LSV_data.charAt(LSV_Client.LSV_data.length()-1);
																   			SyncWithUi.ConsolePrintlnOkError( false, zeichen );
																   			errorCounter ++;
															   		    } else {
															   		    	SyncWithUi.ConsolePrintlnOkError( true, (char)0 );
															   		    }
														   	    	}
														   	    } else {
														   	    	SyncWithUi.ConsolePrintlnOkError( false, errorChDir );
													        		errorCounter ++;
														   	    }
													   	    }
												   	    } else { 
												   	    	// Ordner konnten gewechselt werden
												   	    	
												   	    	SyncWithUi.ConsolePrintlnOkError( false, errorDel );
											        		errorCounter ++;
												   	    }
											   	    } else {
												   	    // Datei gelöscht, nochmals versuchen Datei zusenden
											    		
											   	    	ret =  LSV_Client.SendFile( fordata.ncFile, fordata.pcFile);
												   	    if ( ret ==  LSV_Client.TIMEOUT) { return Status.CANCEL_STATUS; }
												   	    if ( ret ==  LSV_Client.ERROR) {
										    				char zeichen = LSV_Client.LSV_data.charAt(LSV_Client.LSV_data.length()-1);
										    				SyncWithUi.ConsolePrintlnOkError( false, zeichen );
											        		errorCounter ++;
												   	    } else {
										    				SyncWithUi.ConsolePrintlnOkError( true, (char)0 );
												   	    }
											   	    }
										   	    }
									    	} // End For
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
				String msg = "Download mit Fehler abgebrochen!";
				int color = Console.RED;
				if ( errorCounter == 0 ) {
					msg = "Download ohne Fehler beendet";
					color = Console.GREEN;
				} else if ( errorCounter == 1 ) {
					msg = "Download mit einem Fehler beendet";
				} else if ( errorCounter > 1 ) {
					msg = "Download mit ".concat(String.valueOf(errorCounter)).concat(" Fehlern beendet.");
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

	
	//=========================================================================
	private static byte makeDir(String path){
   	    int start=path.indexOf("\\") + 1;
   	    int counter = 100;
   	    byte ret;
   	    String pathNew;
   	    while ( start > -1 && counter-- > 0) {
   	    	start = path.indexOf("\\", start) + 1;
   			pathNew = path.substring(0, start);
   			//SyncWithUi.ConsolePrintln(pathNew , Console.GREEN);
   			ret = LSV_Client.FileInfo(pathNew);
   			if ( ret == LSV_Client.ERROR) {
   	   			ret = LSV_Client.MkDir( pathNew );
   	   			if ( ret != LSV_Client.OK){
   	   				return ret;
   	   			}
   			} else if ( ret == LSV_Client.TIMEOUT ){
   				return ret;
   			}
   	    }
		return LSV_Client.OK;
	}
	
}
