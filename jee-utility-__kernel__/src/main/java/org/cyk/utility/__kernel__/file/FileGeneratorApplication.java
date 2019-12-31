package org.cyk.utility.__kernel__.file;

import java.util.Scanner;

import org.cyk.utility.__kernel__.value.ValueHelper;

public class FileGeneratorApplication {

	public static void main(String[] args) {
		String defaultIde = "Eclipse";
		String defaultProjectsWorkspaceFolder = "E:/Workspaces/"+defaultIde+"/201903";
		String defaultProjectsSystemFolder = "org/cyk/system";
		
		System.out.println("File generator V01.");
		Scanner scanner = new Scanner(System.in);
	    /*
		System.out.print("Integrated Development Environment (Default to "+defaultIde+" if blank): ");
	    String ide = ValueHelper.defaultToIfBlank(scanner.nextLine(),defaultIde);
	    */
	    System.out.print("Projects workspace folder (Default to "+defaultProjectsWorkspaceFolder+" if blank): ");
	    String projectsWorkspaceFolder = ValueHelper.defaultToIfBlank(scanner.nextLine(),defaultProjectsWorkspaceFolder);
	    System.out.print("Project systems folder (Default to "+defaultProjectsSystemFolder+" if blank): ");
	    String systemsFolder = ValueHelper.defaultToIfBlank(scanner.nextLine(),defaultProjectsSystemFolder);
	    System.out.print("System identifier : ");
	    String systemIdentifier = scanner.next();
	    scanner.close();
	    systemsFolder = projectsWorkspaceFolder + "/" + systemsFolder +"/"+ systemIdentifier;
	    System.out.println("Generating following files : Persistence,Business,Representation and Controller into "+systemsFolder);
	    
	}

}
