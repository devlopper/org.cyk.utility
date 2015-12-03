package org.cyk.utility.common.database;

import java.io.IOException;
import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Singleton
public class DatabaseUtils extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 9033325159439140564L;

	@Getter @AllArgsConstructor
	public static enum Server{
		MYSQL("mysqldump -u %s -p%s %s > %s.%s"),
		
		;
		private String exportDatabaseCommandFormat;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public void exportDatabase(ExportParameters parameters) throws IOException, InterruptedException{
		String command = String.format(parameters.getServer().getExportDatabaseCommandFormat(),parameters.getUsername(), parameters.getPassword()
				,parameters.getDatabaseName(),parameters.getFileName(),parameters.getFileExtension());
		commonUtils.executeCommand(command);
	}
	
	@Getter @Setter
	public static class ExportParameters extends DatabaseParameters implements Serializable {
		private static final long serialVersionUID = -509224779283619027L;
		protected String databaseName,fileName,fileExtension="sql";
		public String getFileName(){
			return fileName + ( Boolean.TRUE.equals(autoTimeStampAction) ? System.currentTimeMillis() : Constant.EMPTY_STRING);
		}
	}
	
	/**/
	@Getter @Setter
	public static class DatabaseParameters extends AbstractBean implements Serializable {
		private static final long serialVersionUID = -509224779283619027L;
		protected Server server=Server.MYSQL;
		protected String username="root",password="root";
		protected Boolean autoTimeStampAction = Boolean.TRUE;
	}
	
	/**/
	
	private static DatabaseUtils INSTANCE;
	public static DatabaseUtils getInstance() {
		return INSTANCE;
	}
	
	
}
