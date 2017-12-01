package org.cyk.utility.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.cyk.utility.common.helper.DatabaseHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class DatabaseHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
 
	@Test
	public void connectionString(){
		DatabaseHelper.Source.Server mysql = new DatabaseHelper.Source.Server.MySql.Adapter.Default();
		mysql.setHost("localhost").setPort(3306).setDatabaseName("cyk_todel_db").setDriverName("com.mysql.jdbc.Driver");
		assertEquals("jdbc:mysql://localhost:3306/cyk_todel_db", mysql.getConnectionString());
	}
	
	@Test
	public void connectUsingHsqlInMemory() throws SQLException{
		Connection connection = (Connection) DriverManager.getConnection("jdbc:hsqldb:mem:mymemdb", "SA", "");
		Statement statement = connection.createStatement();
		statement.execute("CREATE TABLE MyTABLE (identifier int,thename varchar(255))");
		ResultSet resultSet = statement.executeQuery("SELECT * FROM MyTABLE");
		
		statement.execute("INSERT INTO MyTABLE VALUES(159,'Hello wold')");
		resultSet = statement.executeQuery("SELECT * FROM MyTABLE");
		while(resultSet.next()){
			assertEquals(159, resultSet.getInt(1));
			assertEquals("Hello wold", resultSet.getString(2));
		}
	}
	
	@Test
	public void connectUsingMySql() throws Exception{
		Class.forName(com.mysql.jdbc.Driver.class.getName());
		Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cyk_memory_db", "root", "root");
		Statement statement = connection.createStatement();
		statement.execute("DROP TABLE IF EXISTS MyTABLE");
		statement.execute("CREATE TABLE MyTABLE (identifier int,thename varchar(255))");
		ResultSet resultSet = statement.executeQuery("SELECT * FROM MyTABLE");
		
		statement.execute("INSERT INTO MyTABLE VALUES(159,'Hello wold')");
		resultSet = statement.executeQuery("SELECT * FROM MyTABLE");
		while(resultSet.next()){
			assertEquals(159, resultSet.getInt(1));
			assertEquals("Hello wold", resultSet.getString(2));
		}
	}
	
}
