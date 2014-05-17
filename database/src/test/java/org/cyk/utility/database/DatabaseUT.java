package org.cyk.utility.database;

import org.cyk.utility.common.test.AbstractUnitTest;

public class DatabaseUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;

	private MySqlDatasource mySqlDatasource = new MySqlDatasource();
	
	@Override
	protected void _execute_() {
		super._execute_();
		mySqlDatasource.execute();
	}
	
	/**/
	
	class MySqlDatasource extends AbstractDatasource {

		@Override
		protected void __open__() {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void __close__() {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void __execute__() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
}
