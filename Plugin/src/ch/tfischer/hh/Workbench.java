package ch.tfischer.hh;

import java.io.File;
import java.io.IOException;


import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import ch.tfischer.hh.console.Console;


public class Workbench {
	
	
	//=========================================================================
	public static void displayDirectoryContents(File dir) {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					Console.println("directory:".concat(file.getCanonicalPath()), 0);
					displayDirectoryContents(file);
				} else {
					Console.println("     file:".concat(file.getCanonicalPath()), 0);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	//=========================================================================
	public static IWorkbenchPage getWorkbenchPage() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		if ( workbench != null) {
			IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
			if ( workbenchWindow != null){
				return workbenchWindow.getActivePage();
			}
		}
		return null;
	}
	

	//=========================================================================
	public static IWorkbenchWindow getActiveWorkbenchWindow() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		if ( workbench != null) {
			return workbench.getActiveWorkbenchWindow();
		}
		return null;
	}
	

	//=========================================================================
	public static IFile getFile(){
		IWorkbenchPage page = getWorkbenchPage();

		if ( page != null ) {
			IEditorPart editorPart = page.getActiveEditor();
			if ( editorPart != null ) {
				IEditorInput editorInput = editorPart.getEditorInput();
				if ( editorInput != null ) {
					return (IFile) editorInput.getAdapter(IFile.class);
				}
			}
		}
		return null;
	}
	
	//=========================================================================
	public static void saveFile(boolean confirm){
		//String name = file.getName();
		IWorkbenchPage page = getWorkbenchPage();
		if ( page != null ) {
			IEditorPart editor = page.getActiveEditor();
			page.saveEditor(editor, confirm);
		}
	}

	public static void saveAllFiles(boolean confirm){
		//String name = file.getName();
		IWorkbenchPage page = getWorkbenchPage();
		if ( page != null ) {
			page.saveAllEditors(confirm);
		}
	}
}
