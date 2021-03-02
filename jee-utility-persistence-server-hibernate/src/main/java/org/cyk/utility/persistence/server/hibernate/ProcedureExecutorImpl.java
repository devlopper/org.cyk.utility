package org.cyk.utility.persistence.server.hibernate;
import java.io.Serializable;

import javax.persistence.StoredProcedureQuery;

import org.cyk.utility.persistence.server.hibernate.annotation.Hibernate;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutor;
import org.hibernate.procedure.ProcedureCall;

@Hibernate
public class ProcedureExecutorImpl extends ProcedureExecutor.AbstractImpl implements Serializable {

	@Override
	protected void releaseConnection(StoredProcedureQuery storedProcedureQuery, String name) {
		((ProcedureCall)storedProcedureQuery).getOutputs().release();
	}
}