package org.cyk.utility.archetype;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.helper.RandomHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GeneratorEclipse implements Serializable {
	
	private static final long serialVersionUID = 2709284066600917745L;
	
	public static final String CYK_WORKSPACE_PATH = "cyk.workspace.path";
	
	//public static final String PACKAGE = "org.cyk.system";
	public static final String PACKAGE = "ci.gouv.dgbf.system";
	
	//archetype-jee-server-microservices-jboss-swarm
	public static final String COMMANDS_SERVER ="%1"+"\r\n"
			+ " cd %2"+"\r\n"
			+ " mvn archetype:generate"
			+ " -DarchetypeGroupId=org.cyk.archetype"
			+ " -DarchetypeArtifactId=archetype-jee-server"
			+ " -DarchetypeVersion=0.1.0"
			+ " -DarchetypeCatalog=local"
			+ " -DsystemIdentifier=%3 "
			+ " -Dpackage="+PACKAGE+" "
			+ " -DinteractiveMode=false";
	
	public static final String COMMANDS_CLIENT ="%1"+"\r\n"
			+ " cd %2"+"\r\n"
			+ " mvn archetype:generate"
			+ " -DarchetypeGroupId=org.cyk.archetype"
			+ " -DarchetypeArtifactId=archetype-jee-client"
			+ " -DarchetypeVersion=0.1.0"
			+ " -DarchetypeCatalog=local"
			+ " -DsystemIdentifier=%3 "
			+ " -Dpackage="+PACKAGE+" "
			+ " -DinteractiveMode=false";
	
	public static final String ARCHETYPE_GENERATE_COMMAND_FORMAT = 
			"start \"\" \"%s\""+/*System.getProperty("user.dir")+"\\command\\maven.cmd"+*/ " %s %s %s";
	
	public enum Type{SYSTEM}
	
	private Properties properties = new Properties();
	private File workspaceDirectory;
	private String identifier,systemPomFolderName,/*pomFileName="pom.xml",*/driveId,systemApplicationFolderName="application",packageName;
	private Type type = Type.SYSTEM;
	private String userDir = System.getProperty("user.dir");
	private File commandFile;
	
	public GeneratorEclipse() throws FileNotFoundException, IOException {
		//properties.put("cyk.workspace.path", "E:\\Workspaces\\Eclipse\\201903\\org\\cyk");
		properties.put("cyk.workspace.path", "E:\\Workspaces\\Eclipse\\201903\\ci\\gouv\\dgbf");
		//properties.put("cyk.workspace.path", "E:\\Workspaces\\Eclipse\\photon\\ci\\gouv\\dgbf");
		//properties.load(new FileInputStream(userDir+"/config.properties"));
		workspaceDirectory = new File(properties.getProperty(CYK_WORKSPACE_PATH));
		
	}
	
	private void executeSystem(File systemDirectory,String side,String commands) throws Exception{
		systemPomFolderName="_pom";
		commandFile = File.createTempFile(System.currentTimeMillis()+"", RandomHelper.getInstance().getString(4)+".cmd", new File(userDir));
		commandFile.deleteOnExit();
		FileUtils.writeStringToFile(commandFile, commands);
		
		System.out.println("Creating jee "+side);
		
		driveId = StringUtils.substringBefore(systemDirectory.toString(), ":")+":";	
		String command = String.format(ARCHETYPE_GENERATE_COMMAND_FORMAT,commandFile.getPath(),driveId,systemDirectory,identifier,side,packageName);
		System.out.println("Command : "+command);
		System.out.println(CommonUtils.getInstance().executeCommand(command));
		
		File targetDirectory = new File(systemDirectory, identifier);
		if(targetDirectory.exists()){
			for(File file : new File(systemDirectory, "pom").listFiles())
				FileUtils.moveToDirectory(file, targetDirectory,true);
			new File(systemDirectory, "pom").deleteOnExit();
		}else{
			new File(systemDirectory, "pom").renameTo(new File(systemDirectory, identifier));
			//systemDirectory = new File(systemDirectory, identifier);	
		}
		systemDirectory = targetDirectory;//new File(systemDirectory, identifier);
		
		systemPomFolderName = identifier+"-"+side+"-"+systemPomFolderName;
		File pomFolder = new File(systemDirectory,systemPomFolderName);
		pomFolder.mkdir();		
		System.out.println("Parent pom folder : "+pomFolder);
		FileUtils.moveFile(new File(systemDirectory,"pom.xml"), new File(pomFolder,"pom.xml"));
		
	}
	
	public void execute() throws Exception{
		System.out.println("Generator");
		System.out.println("Type : "+type);
		System.out.print(type+" ID : ");
		identifier = new BufferedReader(new InputStreamReader(System.in)).readLine();
		System.out.print(type+" package : ");
		packageName = new BufferedReader(new InputStreamReader(System.in)).readLine();
		if(StringUtils.isBlank(packageName))
			packageName = "org.cyk.system."+identifier;
		//identifier="hello";
		System.out.println();
		
		File systemDirectory = new File(workspaceDirectory, type.name().toLowerCase());
		System.out.println("System directory : "+systemDirectory);
		
		executeSystem(systemDirectory,"server", COMMANDS_SERVER);
		executeSystem(systemDirectory,"client",COMMANDS_CLIENT);
		
		System.out.println("###   Done   ###");
	}
	
	protected String script(){
		return String.format(ARCHETYPE_GENERATE_COMMAND_FORMAT, "org.cyk.archetype","system","1.0.0",type.name(),identifier);
	}
	
	@Deprecated
	protected String executeCommand(String command) throws IOException, InterruptedException{
		Process process = Runtime.getRuntime().exec("cmd /c "+command);
		/*process.getOutputStream().write(new byte[]{'\r','\n'});
		process.getOutputStream().write(new byte[]{'\r','\n'});
		
		BufferedWriter printOut = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
		printOut.write("\r\n");printOut.write("\r\n");printOut.write("\r\n");
		*/
		process.waitFor();
		BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = input.readLine()) != null) {
        	sb.append(line+"\r\n");
        }
        return sb.toString();
	}
	
	public static void main(String[] args) {
		GeneratorEclipse generator;
		try {
			 generator = new GeneratorEclipse();
			 generator.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
