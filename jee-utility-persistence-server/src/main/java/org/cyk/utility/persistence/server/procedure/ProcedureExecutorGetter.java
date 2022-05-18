package org.cyk.utility.persistence.server.procedure;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface ProcedureExecutorGetter {

	ProcedureExecutor getProcedureExecutor();
	ProcedureExecutorGetter setProcedureExecutor(ProcedureExecutor procedureExecutor);
	
	@Getter @Setter @Accessors(chain=true)
	public static abstract class AbstractImpl extends AbstractObject implements ProcedureExecutorGetter,Serializable {
		
		private ProcedureExecutor procedureExecutor;
		
	}
}