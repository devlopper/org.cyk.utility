package org.cyk.utility.persistence.server.view;

import java.io.Serializable;

public interface MaterializedViewActualizer extends MaterializedViewProcedureExecutor {

	public static abstract class AbstractImpl extends MaterializedViewProcedureExecutor.AbstractImpl implements MaterializedViewActualizer,Serializable {
		
		public static final String PROCEDURE_NAME = "AP_ACTUALIZE_MV";
		
		public AbstractImpl() {
			procedureName = PROCEDURE_NAME;
		}
		
	}
	
}