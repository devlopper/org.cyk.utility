package org.cyk.utility.database;

import org.cyk.utility.test.unit.AbstractUnitTest;

public class DatabaseUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;

	private MySqlDatasource mySqlDatasource = new MySqlDatasource();
	
	@Override
	protected void _execute_() {
		super._execute_();
		mySqlDatasource.setDropCreateDatabase(true);
		mySqlDatasource.execute();
	}
	
	/**/
	
	class MySqlDatasource extends AbstractDatasource {

		private static final long serialVersionUID = 2955849831760041247L;

		@Override
		protected void __open__() {
			
			
		}

		@Override
		protected void __close__() {
			
		}

		@Override
		protected void __execute__() {
			
		}
		
	}
	
	
}
