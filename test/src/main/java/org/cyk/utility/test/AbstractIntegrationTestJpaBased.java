package org.cyk.utility.test;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

/**
 * Persistence integration test (IT)
 * @author Komenan Y .Christian
 *
 */ 
public abstract class AbstractIntegrationTestJpaBased extends AbstractIntegrationTest {
	 
	private static final long serialVersionUID = -3977685343817022628L;

	static {
		AFTER_CLASS_METHOD = new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 1L;
			@Override
			protected Object __execute__(Object parameter) {
				data = false;
				return null;
			}
		};
	}
	
	protected static Boolean data = Boolean.FALSE;
	
	@Inject private UserTransaction transaction;
	
	@Override
	protected void _before_() throws Exception {
		super._before_();
		if(Boolean.TRUE.equals(data))
			return;
		transaction(new TestMethod() {private static final long serialVersionUID = 1L; @Override protected void test() { populate(); data = Boolean.TRUE; } });	
		afterCommit();
	}
	
	protected void afterCommit(){}
	
	
	
	/**/
	
	protected abstract void populate();
	protected abstract void create();
	protected abstract void read();
	protected abstract void update();
	protected abstract void delete();
	
	/* Shortcut */
	
	protected void transaction(final TestMethod method,Class<? extends SQLException> exceptionClassExpected){
		new Transaction(this,transaction,exceptionClassExpected) {
			@Override
			public void _execute_() {
				method.execute();
			}
		}.run();
	}
	
	protected void transaction(final TestMethod method){
		transaction(method, null);
	}

	public abstract EntityManager getEntityManager();
	
}
