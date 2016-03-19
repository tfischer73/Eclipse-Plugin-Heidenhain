package ch.tfischer.hh.dynamic;

import java.util.ArrayList;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;




import ch.tfischer.hh.data.FileData;
import ch.tfischer.hh.data.Global;


public class ProjectFiles {

	private static IProject project;
		
	
	//=========================================================================
	@SuppressWarnings("static-access")
	public static void getFiles(String projectName, IPath path) {
		IWorkspaceRoot WorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		if ( WorkspaceRoot != null ) {
		project = WorkspaceRoot.getProject(projectName);
		ArrayList<IResource>resources = new ArrayList<IResource>();
			if ( path == null ) {
				try {
					IResource[] members = project.members( false );
					for (IResource resource : members) {
						if (resource.getType() == resource.FOLDER ){
							resources.add(resource);
						} else if ( resource.getType() == resource.FILE ){
							extractFileData( resource );
						}
					}
					for (IResource resource : resources) {
						getFilesFromFolder( resource.getProjectRelativePath() );
					}
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			} else {
				getFilesFromFolder( path );
			}
		}
	}
	
	
	//=========================================================================
	@SuppressWarnings("static-access")
	public static void getFilesFromFolder( IPath path ) {
		if ( path != null ) {
			ArrayList<IResource>resources = new ArrayList<IResource>();
			IFolder folder = project.getFolder(path);
			if (folder.exists()) {
				try {
					IResource[] members = folder.members();
					for ( IResource resource : members ) {
						if (resource.getType() == resource.FOLDER ){
							resources.add(resource);
						} else {
							extractFileData(resource);
						}
					}
					for (IResource resource : resources) {
						getFilesFromFolder( resource.getProjectRelativePath() );
					}
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

	//=========================================================================
	@SuppressWarnings("static-access")
	public static void extractFileData(IResource resource) {
		if ( resource.getType() == resource.FILE ){
			if ( resource.getName().startsWith(".") ) { return; }
			if ( matchTransferFile(resource.getName()) ) {
				FileData fileData = new FileData();
        		fileData.pcFile = resource.getLocation().toString();
        		fileData.ncFile = Global.prefPythonPath .concat(resource.getProjectRelativePath().toOSString());
        		fileData.FileName = resource.getName();
        		Global.FileList.add(fileData);
			}
		}
		
	}
	
	
	//=========================================================================
	private static boolean matchTransferFile(String fileName){
		if ( fileName == null ) {
			return false;
		}
		for ( String transferFile:Global.transferFiles ){
			if ( fileName.toLowerCase().endsWith(transferFile.toLowerCase()) ) {
				return true;
			}
		}
		return false;
	}

}
