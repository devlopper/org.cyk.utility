package org.cyk.utility.__kernel__.string.application;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.RegExUtils;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.klass.NamingModel;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringGenerator;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.StringTemplateGetter;
import org.cyk.utility.__kernel__.string.StringTemplateIdentifierGetter;
import org.cyk.utility.__kernel__.value.ValueHelper;
//import org.jboss.weld.environment.se.Weld;

public class ProjectSystemEntityFilesGenerator {

	public static void main(String[] args) {
		//Weld weld = new Weld();
	    //weld.initialize();
		
		//String defaultIde = "Eclipse";
		//String defaultProjectsWorkspaceFolder = "E:/Workspaces/"+defaultIde+"/201903";
		String defaultProjectsWorkspaceFolder = "E:/Repositories/source/git";
		String defaultProjectsSystemFolder = "ci/gouv/dgbf/system";
		//String defaultProjectsSystemFolder = "org/cyk/system";
		
		System.out.println("File generator V01.");
		Scanner scanner = new Scanner(System.in);
	   
	    System.out.print("Projects workspace folder (Default to "+defaultProjectsWorkspaceFolder+" if blank): ");
	    String projectsWorkspaceFolder = ValueHelper.defaultToIfBlank(scanner.nextLine(),defaultProjectsWorkspaceFolder);
	    
	    System.out.print("Project systems folder (Default to "+defaultProjectsSystemFolder+" if blank): ");
	    String systemsFolder = ValueHelper.defaultToIfBlank(scanner.nextLine(),defaultProjectsSystemFolder);
	    
	    System.out.print("System identifier : ");
	    String systemIdentifier = scanner.next();
	    
	    System.out.print("Entities : ");
	    String entities = scanner.next();
	    
	    scanner.close();
	    
	    entities = RegExUtils.removeAll(entities, " ");
	    if(StringHelper.isBlank(entities)) {
	    	return;
	    }
	    
	    Map<String,String> files = new HashMap<>();
	    processEntities(projectsWorkspaceFolder + "/" + systemsFolder, systemIdentifier,RegExUtils.replaceAll(systemsFolder, "/", "."),files, entities.split(","));
		//processEntities("E:\\Workspaces\\Eclipse\\201903\\org\\cyk\\system","contact","org.cyk.system", files, "PhoneNumber");
		for(Map.Entry<String, String> entry : files.entrySet()) {
			try {
				Path path = Paths.get(entry.getKey());
				if(path == null)
					continue;
				File file = path.toFile();
				if(file == null)
					continue;
				if(file.exists()) {
					System.out.println("File has not been overriden : "+file);
					continue;
				}
				Files.writeString(path, entry.getValue());
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		//weld.shutdown();
	}
	
	private static void processEntities(String systemsFolder,String systemIdentifier,String packageName,Map<String,String> files,Collection<String> entities) {
		if(CollectionHelper.isEmpty(entities))
			return;
		for(String entity : entities) {
			if(StringHelper.isBlank(entity))
				continue;
			Map<String,Object> arguments = new HashMap<>();
			arguments.put("system_package", packageName);
			arguments.put("system_identifier", systemIdentifier);
			arguments.put("entity_class_name", entity);
			String tableName = StringHelper.applyCase(entity, Case.LOWER);
			arguments.put("entity_table_name", tableName);
			arguments.put("path", tableName);
			addFile(systemsFolder, systemIdentifier, packageName, new NamingModel().server().persistence().entities(), entity, arguments, files);
			addFile(systemsFolder, systemIdentifier, packageName, new NamingModel().server().persistence().api().suffix(), entity, arguments, files);
			addFile(systemsFolder, systemIdentifier, packageName, new NamingModel().server().persistence().impl().suffix(), entity, arguments, files);
			addFile(systemsFolder, systemIdentifier, packageName, new NamingModel().server().business().api().suffix(), entity, arguments, files);
			addFile(systemsFolder, systemIdentifier, packageName, new NamingModel().server().business().impl().suffix(), entity, arguments, files);
			addFile(systemsFolder, systemIdentifier, packageName, new NamingModel().server().representation().entities().suffix(), entity, arguments, files);
			addFile(systemsFolder, systemIdentifier, packageName, new NamingModel().server().representation().entities().suffix().addSuffixMapper(), entity, arguments, files);
			addFile(systemsFolder, systemIdentifier, packageName, new NamingModel().server().representation().api().suffix(), entity, arguments, files);
			addFile(systemsFolder, systemIdentifier, packageName, new NamingModel().server().representation().impl().suffix(), entity, arguments, files);
			addFile(systemsFolder, systemIdentifier, packageName, new NamingModel().client().controller().entities(), entity, arguments, files);
			addFile(systemsFolder, systemIdentifier, packageName, new NamingModel().client().controller().entities().addSuffixMapper(), entity, arguments, files);
			addFile(systemsFolder, systemIdentifier, packageName, new NamingModel().client().controller().api().suffix(), entity, arguments, files);
			addFile(systemsFolder, systemIdentifier, packageName, new NamingModel().client().controller().impl().suffix(), entity, arguments, files);
		}
	}
	
	private static void processEntities(String systemsFolder,String systemIdentifier,String packageName,Map<String,String> files,String...entities) {
		if(ArrayHelper.isEmpty(entities))
			return;
		processEntities(systemsFolder,systemIdentifier,packageName, files, CollectionHelper.listOf(Boolean.TRUE,entities));
	}
	
	private static void addFile(String systemsFolder,String systemIdentifier,String packageName,NamingModel namingModel,String entity,Map<String,Object> arguments,Map<String,String> files) {
		String templateIdentifier = StringTemplateIdentifierGetter.getInstance().get(namingModel);
		Object template = StringTemplateGetter.getInstance().get(templateIdentifier);
		files.put(buildFileName(systemsFolder, systemIdentifier, packageName, namingModel, entity), StringGenerator.getInstance().generate(template, arguments));
	}

	private static String buildFileName(String systemsFolder,String systemIdentifier,String packageName,NamingModel namingModel,String entity) {
		String subLayer = namingModel.getSubLayer();
		if(namingModel.isNodeClient() && namingModel.isLayerController() && namingModel.isSubLayerImpl()) {
			subLayer = NamingModel.SUB_LAYER_API;
		}
		return systemsFolder+"/"+systemIdentifier+"/"+namingModel.getNode()+"/"+systemIdentifier+"-"+namingModel.getNode()+"-"+namingModel.getLayer()+"-"+subLayer+"/src/main/java/"
				+RegExUtils.replaceAll(packageName+"."+systemIdentifier+"."+namingModel.getNode()+"."+namingModel.getLayer()+"."+subLayer, "\\.", "/")+"/"
				+entity+ValueHelper.defaultToIfBlank(namingModel.getSuffix(),ConstantEmpty.STRING)+".java";
	}
}
