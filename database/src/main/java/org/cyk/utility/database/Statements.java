package org.cyk.utility.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Statements{
	
	protected String driverName, url, username, password;
	protected Connection conn;
	protected Statement stmt;
	
	Statements(String driverName, String url, String username,String password){
		super();
		this.driverName = driverName;
		this.url = url;
		this.username = username;
		this.password = password;
		try {
			Class.forName(driverName);
			conn = (Connection) DriverManager.getConnection(url, username, password);
			stmt = (Statement) conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void __execute__() throws SQLException{
		System.out.println("\t --- NOTHING TO EXECUTE ---");
	}

	public final void execute() {
		try {
			__execute__();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			closeStatement(stmt);
			closeConnection(conn);
		}// end try
	}
	
	public static void closeStatement(Statement statement){
		if(statement!=null)
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public static void closeConnection(Connection connection){
		if(connection!=null)
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}