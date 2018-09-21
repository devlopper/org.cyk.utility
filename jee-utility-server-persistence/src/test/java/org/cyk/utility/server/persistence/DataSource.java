package org.cyk.utility.server.persistence;

import javax.annotation.sql.DataSourceDefinition;

@DataSourceDefinition(
		name="java:global/utility/testDataSource",
		//name = "java:jboss/datasources/testDataSource",
		className="org.h2.jdbcx.JdbcDataSource",
		url="jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
		user="sa",
		password="sa"
)
public class DataSource {

}
