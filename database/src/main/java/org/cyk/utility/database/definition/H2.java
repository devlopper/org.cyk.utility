package org.cyk.utility.database.definition;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.FileUtils;
import org.cyk.utility.database.AbstractDatabase;
import org.cyk.utility.database.AbstractMode;
import org.cyk.utility.database.EmbeddedMode;
import org.cyk.utility.database.ServerMode;

public class H2 extends AbstractDatabase implements Serializable {

	private static final long serialVersionUID = 2824346122596177300L;

	public static String EMBEDDED_DRIVER_NAME = "org.h2.Driver";
	public static String SERVER_DRIVER_NAME = "org.h2.Driver";
	public static String TARGET = "h2";
	public static int DEFAULT_PORT = -1;//unknown yet
	
	{
		if(mode instanceof EmbeddedMode){
			File[] files =  new File(((EmbeddedMode) mode).getPath()).listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File file, String fname) {
					return fname.startsWith(name+".") && fname.endsWith(".db");
				}
			});
			for(File file : files)
				if(!FileUtils.deleteQuietly(file))
					System.out.println("Cannot delete "+file);
		}
	}
	
	public H2(AbstractMode mode, String name, String username, String password) {
		super(mode, name, username, password);
	}
	
	public H2(AbstractMode mode) {
		super(mode);
	}
	
	@Override
	public void __drop__(Statement statement) throws SQLException {
		//System.out.println("Statement is :");
		//System.out.println("Here - "+statement);
		//System.out.println(name);
		
		//statement.executeUpdate("DROP SCHEMA IF EXISTS " +name);
	}
	
	@Override
	public void __create__(Statement statement) throws SQLException {
		statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS " +name+" AUTHORIZATION  "+username);
	}
	
	@Override
	public String __embeddedModeConnectionUrl__(EmbeddedMode embeddedMode) {
		return "jdbc:h2:file:"+ embeddedMode.getPath()+name+";AUTO_SERVER=TRUE";
	}
	
	@Override
	public String __serverModeConnectionUrl__(ServerMode serverMode) {
		return "jdbc:h2:tcp://"+ serverMode.getHost() +/*":"+ serverMode.getPort() +*/"/~/"+name;
	}
	
	/* short cut method */
	
	public static H2 create(boolean serverMode,String name, String username, String password,String databasePath){
		AbstractMode mode = serverMode?
				new ServerMode(SERVER_DRIVER_NAME, TARGET, DEFAULT_PORT):
				new EmbeddedMode(EMBEDDED_DRIVER_NAME, TARGET, databasePath);
		return new H2(mode, name, username, password);
	}
	
	public static H2 create(boolean serverMode,String name, String username, String password){
		return create(serverMode, name, username, password, EMBEDDED_MODE_DEFAULT_PATH);
	}
	
}
