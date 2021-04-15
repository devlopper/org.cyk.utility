package org.cyk.utility.persistence.server.hibernate;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.PersistenceException;

import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.persistence.EntityManagerGetter;
import org.cyk.utility.persistence.server.MetricsManager;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutor;
import org.cyk.utility.persistence.server.procedure.ProcedureExecutorArguments;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HibernateProcedureUnitTest extends AbstractUnitTest {

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		MetricsManager.getInstance().enable();
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		MetricsManager.getInstance().disable();
	}
	
	@Test
	public void executeOutsideTransaction() {
		Assertions.assertThrows(PersistenceException.class, () -> {
			for(Integer index = 0; index < 100; index = index + 1) {
				Integer connectionCount = NumberHelper.getInteger(MetricsManager.getInstance().getConnectionCount());
				ProcedureExecutor.getInstance().execute("myproc");
				assertThat(NumberHelper.getInteger(MetricsManager.getInstance().getConnectionCount())).isEqualTo(connectionCount+1);
			}
		});
	}
	
	@Test
	public void executeWithinTransaction() {
		for(Integer index = 0; index < 1000; index = index + 1) {
			Integer connectionCount = NumberHelper.getInteger(MetricsManager.getInstance().getConnectionCount());
			ProcedureExecutorArguments arguments = new ProcedureExecutorArguments();
			arguments.setName("myproc");
			arguments.setEntityManager(EntityManagerGetter.getInstance().get());
			arguments.getEntityManager().getTransaction().begin();
			ProcedureExecutor.getInstance().execute(arguments);
			arguments.getEntityManager().getTransaction().commit();
			assertThat(MetricsManager.getInstance().getSuccessfulTransactionCount()).isEqualTo(connectionCount+1);
		}
	}
}