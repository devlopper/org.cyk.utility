package org.cyk.utility.persistence.server.view;

import java.io.Serializable;

public interface MaterializedViewCreator extends MaterializedViewProcedureExecutor {

	public static abstract class AbstractImpl extends MaterializedViewProcedureExecutor.AbstractImpl implements MaterializedViewCreator,Serializable {
		
		public static final String PROCEDURE_NAME = "AP_CREATE_MV";
		
		public AbstractImpl() {
			procedureName = PROCEDURE_NAME;
		}
		
	}
	
}