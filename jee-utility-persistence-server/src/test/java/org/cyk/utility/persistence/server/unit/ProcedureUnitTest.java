package org.cyk.utility.persistence.server.unit;

import javax.persistence.PersistenceException;

import org.cyk.utility.persistence.EntityManagerGetter;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutor;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutorArguments;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProcedureUnitTest extends AbstractUnitTest {

	@Test
	public void executeOutsideTransaction() {
		Assertions.assertThrows(PersistenceException.class, () -> {
			for(Integer index = 0; index < 1000; index = index + 1) {
				ProcedureExecutor.getInstance().execute("myproc");
			}
		});		
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
}