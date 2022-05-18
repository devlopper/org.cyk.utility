package org.cyk.utility.persistence.server.view;

import java.io.Serializable;

public interface MaterializedViewUpdator extends MaterializedViewProcedureExecutor {

	public static abstract class AbstractImpl extends MaterializedViewProcedureExecutor.AbstractImpl implements MaterializedViewUpdator,Serializable {
		
		public static final String PROCEDURE_NAME = "AP_UPDATE_MV";
		
		public AbstractImpl() {
			procedureName = PROCEDURE_NAME;
		}
		
	}
	
}