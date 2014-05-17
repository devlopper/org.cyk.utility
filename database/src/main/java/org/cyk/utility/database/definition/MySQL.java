package org.cyk.utility.database.definition;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Statement;

import org.cyk.utility.database.AbstractDatabase;
import org.cyk.utility.database.AbstractMode;
import org.cyk.utility.database.EmbeddedMode;
import org.cyk.utility.database.ServerMode;

public class MySQL extends AbstractDatabase implements Serializable {

	private static final long serialVersionUID = 2824346122596177300L;

	public static String SERVER_DRIVER_NAME = "com.mysql.jdbc.Driver";
	public static String TARGET = "MySQL";
	public static int DEFAULT_PORT = 3306;
	
	public MySQL(AbstractMode mode, String name, String username, String password) {
		super(mode, name, username, password);
	}
	
	public MySQL(AbstractMode mode) {
		super(mode);
	}
	
	@Override
	public void __drop__(Statement statement) throws SQLException {
		statement.executeUpdate("DROP DATABASE " + name);
	}
	
	@Override
	public void __create__(Statement statement) throws SQLException {
		statement.executeUpdate("Create DATABASE " + name);
	}
	
	@Override
	public String __embeddedModeConnectionUrl__(EmbeddedMode embeddedMode) {
		return null;
	}
	
	@Override
	public String __serverModeConnectionUrl__(ServerMode serverMode) {
		return "jdbc:mysql://"+serverMode.getHost()+":"+serverMode.getPort()+"/"+name;
	}
	
	/* short cut method */
	
	public static MySQL create(boolean serverMode,String name, String username, String password){
		AbstractMode mode = serverMode?
				new ServerMode(SERVER_DRIVER_NAME, TARGET, DEFAULT_PORT):
				null;
		return new MySQL(mode, name, username, password);
	}
}
