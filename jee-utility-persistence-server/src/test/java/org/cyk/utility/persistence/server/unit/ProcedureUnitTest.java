package org.cyk.utility.persistence.server.unit;

import javax.persistence.StoredProcedureQuery;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.persistence.EntityManagerGetter;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutor;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutorArguments;
import org.junit.jupiter.api.Test;

public class ProcedureUnitTest extends AbstractUnitTest {

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		DependencyInjection.setQualifierClassTo(org.cyk.utility.__kernel__.annotation.Test.class, ProcedureExecutor.class);
	}
	
	@Test
	public void executeOutsideTransaction() {		
		for(Integer index = 0; index < 1000; index = index + 1) {
			ProcedureExecutor.getInstance().execute("myproc");
		}		
	}
	
	@Test
	public void executeWithinTransaction() {
		for(Integer index = 0; index < 1000; index = index + 1) {
			ProcedureExecutorArguments arguments = new ProcedureExecutorArguments();
			arguments.setName("myproc");
			arguments.setEntityManager(EntityManagerGetter.getInstance().get());
			arguments.getEntityManager().getTransaction().begin();
			ProcedureExecutor.getInstance().execute(arguments);
			arguments.getEntityManager().getTransaction().commit();
		}
	}
	
	@org.cyk.utility.__kernel__.annotation.Test
	public static class ProcedureExecutorImpl extends ProcedureExecutor.AbstractImpl {
		
		@Override
		protected void releaseConnection(StoredProcedureQuery storedProcedureQuery, String name) {
			
		}
		
	}
}