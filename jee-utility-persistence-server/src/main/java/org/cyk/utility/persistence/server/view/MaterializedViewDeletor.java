package org.cyk.utility.persistence.server.view;

import java.io.Serializable;

public interface MaterializedViewDeletor extends MaterializedViewProcedureExecutor {

	public static abstract class AbstractImpl extends MaterializedViewProcedureExecutor.AbstractImpl implements MaterializedViewDeletor,Serializable {
		
		public static final String PROCEDURE_NAME = "AP_DELETE_MV";
		
		public AbstractImpl() {
			procedureName = PROCEDURE_NAME;
		}
		
	}
	
}