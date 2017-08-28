package org.cyk.utility.common;

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
	
}
