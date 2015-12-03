package org.cyk.utility.common;

import org.cyk.utility.common.database.DatabaseUtils;
import org.cyk.utility.common.database.DatabaseUtils.CreateParameters;
import org.cyk.utility.common.database.DatabaseUtils.DropParameters;
import org.cyk.utility.common.database.DatabaseUtils.ExportParameters;
import org.cyk.utility.test.unit.AbstractUnitTest;

public class DatabaseUtilsUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
 
	private DatabaseUtils databaseUtils = new DatabaseUtils();
	
	@Override
	protected void _execute_() {
		super._execute_();
		dropDatabase();
		createDatabase();
		exportDatabase();
		dropDatabase();
	}
	
	private void createDatabase(){
		CreateParameters parameters = new CreateParameters();
		parameters.setDatabaseName("cyk_testtodel_db");
		try {
			databaseUtils.createDatabase(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void dropDatabase(){
		DropParameters parameters = new DropParameters();
		parameters.setDatabaseName("cyk_testtodel_db");
		try {
			databaseUtils.dropDatabase(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void exportDatabase(){
		ExportParameters parameters = new ExportParameters();
		parameters.setDatabaseName("cyk_testtodel_db");
		parameters.setFileName("target/cyk_testtodel_db_export");
		try {
			databaseUtils.exportDatabase(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
