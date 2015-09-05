package org.cyk.utility.database.definition;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Statement;

import org.cyk.utility.database.AbstractDatabase;
import org.cyk.utility.database.AbstractMode;
import org.cyk.utility.database.EmbeddedMode;
import org.cyk.utility.database.ServerMode;

public class Derby extends AbstractDatabase implements Serializable {

	private static final long serialVersionUID = 2824346122596177300L;

	public static String SERVER_DRIVER_NAME = "org.apache.derby.jdbc.ClientDriver";
	public static String TARGET = "Derby";
	public static int DEFAULT_PORT = 1527;
	
	public Derby(AbstractMode mode, String name, String username, String password) {
		super(mode, name, username, password);
		
	}
	
	public Derby(AbstractMode mode) {
		super(mode);
	}
	
	@Override
	public void __drop__(Statement statement) throws SQLException {
		System.out.println("   ---   DO DELETE THE DERBY DATABASE FOLDER BY HAND ONLY   ---");
	}
	
	@Override
	public void __create__(Statement statement) throws SQLException {
		System.out.println("   ---   THE DERBY DATABASE FOLDER Will be AUTO CREATED   ---");
	}
	
	@Override
	public String __embeddedModeConnectionUrl__(EmbeddedMode embeddedMode) {
		return null;
	}
	
	@Override
	public String __serverModeConnectionUrl__(ServerMode serverMode) {
		return "jdbc:derby://"+serverMode.getHost()+":"+serverMode.getPort()+"/"+name+";create=true";
	}
	
}
