package ch.tfischer.hh.console;
/**
 * ============================================================================
 * Erstellt ein Konsole und stellt die Funktionen zum beschreiben und
 * löschen bereit
 * ============================================================================
 **/


import java.io.IOException;

import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import ch.tfischer.hh.Workbench;


public class Console {

	// Konstanten
	//-------------------------------------------------------------------------
	public final static int BLACK = 0;
	public final static int GREEN = 1;
	public final static int RED   = 2;
	
	// Variablen
	//-------------------------------------------------------------------------
	private static String Name = "";
	private static MessageConsole myConsole;
	private static Color green 	= new Color(null,  0,150,  0);
	private static Color red 	= new Color(null,255,  0,  0);
	private static Color black  = new Color(null,  0,  0,  0);

	
	//=========================================================================
	public static void find() {
	    ConsolePlugin plugin = ConsolePlugin.getDefault();
	    IConsoleManager conMan = plugin.getConsoleManager();
	    IConsole[] existing = conMan.getConsoles();
	    for (int i = 0; i < existing.length; i++)
	       if (getName().equals(existing[i].getName()))
	          return;
	    //no ch.tfischer.hh.console found, so create a new one
	    myConsole = new MessageConsole(getName(), null);
	    conMan.addConsoles(new IConsole[]{myConsole});
	    //myConsole.setTabWidth(8);
	}

	
	//=========================================================================
	public static void println (String text ,int color ){
		// Console suchen und Text ausgeben
		MessageConsoleStream out = myConsole.newMessageStream();
		switch (color){
			case BLACK: out.setColor(black); break;
			case GREEN: out.setColor(green); break;
			case RED:   out.setColor(red); break;
		}
		out.println(text);
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//=========================================================================
	public static void printlnOkError ( boolean ok ){
		// Console suchen und Text ausgeben
		MessageConsoleStream out = myConsole.newMessageStream();
		MessageConsoleStream outCol = myConsole.newMessageStream();
		Color col;
		String text;
		if ( ok ){
			col = green;
			text = "OK";
		} else {
			col = red;
			text = "ERROR";
		}
		out.print("	[");
		outCol.setColor(col);
		outCol.print(text);
		out.println("]");
		try {
			out.close();
			outCol.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	//=========================================================================
	public static void print (String text, int color){
		// Console suchen und Text ausgeben
		MessageConsoleStream out = myConsole.newMessageStream();
		switch (color){
			case BLACK: out.setColor(black); break;
			case GREEN: out.setColor(green); break;
			case RED:   out.setColor(red); break;
		}
		out.print(text);
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	//=========================================================================
	public static void clear() {
		myConsole.clearConsole();		
	}
	
	
	//=========================================================================
	public static void view() {
		IWorkbenchWindow ActiveWorkbenchWindow = Workbench.getActiveWorkbenchWindow();
		IWorkbenchPage page = ActiveWorkbenchWindow.getActivePage();// obtain the active PrefPage
		IWorkbenchPart part = ActiveWorkbenchWindow.getPartService().getActivePart();
		String id = IConsoleConstants.ID_CONSOLE_VIEW;
		try{
			IConsoleView view = (IConsoleView) page.showView(id);
	    	view.display(myConsole);
		}catch (Exception e){
		   
	   	}
    	page.activate(part);
	}


	//=========================================================================
	public static String getName() {
		return Name;
	}


	//=========================================================================
	public static void setName(String name) {
		Name = name;
	}
	
}
