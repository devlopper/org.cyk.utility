package org.cyk.utility.archetype;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Properties;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.CommonUtils;

@Getter @Setter
public class GeneratorEclipse implements Serializable {
	
	private static final long serialVersionUID = 2709284066600917745L;
	
	public static final String CYK_WORKSPACE_PATH = "cyk.workspace.path";
	
	public static final String COMMANDS ="%1"+"\r\n"
			+ " cd %2"+"\r\n"
			+ " mvn archetype:generate -DarchetypeGroupId=org.cyk.archetype -DarchetypeArtifactId=system -DarchetypeVersion=1.0.0 -DarchetypeCatalog=local -DsystemId=%3";
	
	public static final String ARCHETYPE_GENERATE_COMMAND_FORMAT = 
			"start %s"+/*System.getProperty("user.dir")+"\\command\\maven.cmd"+*/ " %s %s %s";
	
	public enum Type{SYSTEM}
	
	private Properties properties = new Properties();
	private File workspaceDirectory;
	private String identifier,systemPomFolderName="_pom",/*pomFileName="pom.xml",*/driveId,systemApplicationFolderName="application";
	private Type type = Type.SYSTEM;
	private String userDir = System.getProperty("user.dir");
	private File commandFile;
	
	public GeneratorEclipse() throws FileNotFoundException, IOException {
		properties.load(new FileInputStream(userDir+"/config.properties"));
		workspaceDirectory = new File(properties.getProperty(CYK_WORKSPACE_PATH));
		commandFile = File.createTempFile(System.currentTimeMillis()+"", RandomStringUtils.randomAlphabetic(4)+".cmd", new File(userDir));
		commandFile.deleteOnExit();
		FileUtils.writeStringToFile(commandFile, COMMANDS);
	}
	
	public void execute() throws Exception{
		System.out.println("Generator");
		System.out.println("Type : "+type);
		System.out.print(type+" ID : ");
		identifier = new BufferedReader(new InputStreamReader(System.in)).readLine();
		//identifier="hello";
		System.out.println();
		
		File systemDirectory = new File(workspaceDirectory, type.name().toLowerCase());
	
		System.out.println("System directory : "+systemDirectory);
		driveId = StringUtils.substringBefore(systemDirectory.toString(), ":")+":";	
		//System.out.println(executeCommand(String.format(ARCHETYPE_GENERATE_COMMAND_FORMAT,commandFile.getPath(),driveId,systemDirectory,identifier)));
		System.out.println(CommonUtils.getInstance().executeCommand(String.format(ARCHETYPE_GENERATE_COMMAND_FORMAT,commandFile.getPath(),driveId,systemDirectory,identifier)));
		
		new File(systemDirectory, "pom").renameTo(new File(systemDirectory, identifier));
		systemDirectory = new File(systemDirectory, identifier);
		
		File pomFolder = new File(systemDirectory,systemPomFolderName);
		pomFolder.mkdir();		
		
		FileUtils.moveFile(new File(systemDirectory,"pom.xml"), new File(pomFolder,"pom.xml"));
		
		for(File file : systemDirectory.listFiles())
			if(file.isDirectory() && !file.getName().equals(systemPomFolderName) && !file.getName().equals(systemApplicationFolderName)){
				String name = new File(systemDirectory,systemDirectory.getName()+"-"+file.getName()).getAbsolutePath();
				file.renameTo(new File(name));
			}
		
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
