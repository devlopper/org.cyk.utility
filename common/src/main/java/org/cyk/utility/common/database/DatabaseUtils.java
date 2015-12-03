package org.cyk.utility.common.database;

import java.io.IOException;
import java.io.Serializable;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class DatabaseUtils extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 9033325159439140564L;

	@Getter @AllArgsConstructor
	public static enum Server{
		MYSQL("mysql -u %s -p%s -e \"create database %s;\""
				,"mysql -u %s -p%s -e \"drop schema %s;\""
				,"mysqldump -u %s -p%s --add-drop-database  %s > %s.%s"
				),
		;
		private String createDatabaseCommandFormat;
		private String dropDatabaseCommandFormat;
		private String exportDatabaseCommandFormat;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public void createDatabase(CreateParameters parameters) throws IOException, InterruptedException{
		logTrace("Creating database", parameters.getDatabaseName());
		String command = String.format(parameters.getServer().getCreateDatabaseCommandFormat(),parameters.getUsername(), parameters.getPassword()
				,parameters.getDatabaseName());
		commonUtils.executeCommand(command);
	}
	
	public void dropDatabase(DropParameters parameters) throws IOException, InterruptedException{
		logTrace("Droping database", parameters.getDatabaseName());
		String command = String.format(parameters.getServer().getDropDatabaseCommandFormat(),parameters.getUsername(), parameters.getPassword()
				,parameters.getDatabaseName());
		commonUtils.executeCommand(command);
	}
	
	public void exportDatabase(ExportParameters parameters) throws IOException, InterruptedException{
		logTrace("Exporting database", parameters.getDatabaseName());
		String command = String.format(parameters.getServer().getExportDatabaseCommandFormat(),parameters.getUsername(), parameters.getPassword()
				,parameters.getDatabaseName(),parameters.getProcessedFileName(),parameters.getFileExtension());
		commonUtils.executeCommand(command);
	}
	
	public void exportDatabase(String databaseName,Boolean autoTimeStampAction,String fileSuffix) throws IOException, InterruptedException{
		ExportParameters parameters = new ExportParameters();
		parameters.setDatabaseName(databaseName);
		parameters.setFileName(databaseName+_EXPORT);
		parameters.setAutoTimeStampAction(autoTimeStampAction);
		parameters.setFileSuffix(fileSuffix);
		exportDatabase(parameters);
	}
	
	/**/
	@Getter @Setter
	public static class DatabaseParameters extends AbstractBean implements Serializable {
		private static final long serialVersionUID = -509224779283619027L;
		protected Server server=Server.MYSQL;
		protected String databaseName,username="root",password="root";
		protected Boolean autoTimeStampAction = Boolean.TRUE;
	}
	
	@Getter @Setter
	public static class CreateParameters extends DatabaseParameters implements Serializable {
		private static final long serialVersionUID = -509224779283619027L;
		
	}
	
	@Getter @Setter
	public static class DropParameters extends DatabaseParameters implements Serializable {
		private static final long serialVersionUID = -509224779283619027L;
		
	}
	
	@Getter @Setter
	public static class ExportParameters extends DatabaseParameters implements Serializable {
		private static final long serialVersionUID = -509224779283619027L;
		protected String fileName,fileExtension="sql",fileSuffix=_EXPORT;
		
		public String getProcessedFileName(){
			return fileName + (StringUtils.isEmpty(fileSuffix)?Constant.EMPTY_STRING:fileSuffix) 
					+ ( Boolean.TRUE.equals(autoTimeStampAction) ? System.currentTimeMillis() : Constant.EMPTY_STRING);
		}
	}
	
	/**/
	
	
	/**/
	
	private static DatabaseUtils INSTANCE;
	public static DatabaseUtils getInstance() {
		return INSTANCE;
	}
	
	public static final String _EXPORT = "_export";
}
