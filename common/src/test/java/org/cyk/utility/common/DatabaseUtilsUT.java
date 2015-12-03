package org.cyk.utility.common;

import org.cyk.utility.common.database.DatabaseUtils;
import org.cyk.utility.common.database.DatabaseUtils.ExportParameters;
import org.cyk.utility.test.unit.AbstractUnitTest;

public class DatabaseUtilsUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
 
	private DatabaseUtils databaseUtils = new DatabaseUtils();
	
	@Override
	protected void _execute_() {
		super._execute_();
		exportDatabase();
	}
	
	private void exportDatabase(){
		ExportParameters parameters = new ExportParameters();
		parameters.setDatabaseName("cyk_iesaschool_db");
		parameters.setFileName("target/cyk_iesaschool_db_export");
		try {
			databaseUtils.exportDatabase(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
