package org.cyk.utility.test.business.server;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.cyk.utility.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.persistence.EntityManagerGetter;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;

public abstract class AbstractUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		initializeEntityManagerFactory(getPersistenceUnitName());
	}
	
	protected void initializeEntityManagerFactory(String persistenceUnitName) {
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(persistenceUnitName);
	}
	
	protected String getPersistenceUnitName() {
		return "default";
	}
	
	/**/
	
	protected static void executeTransaction(Runnable runnable) {
		if(runnable == null)
			return;
		EntityManager entityManager = EntityManagerGetter.getInstance().get();
		entityManager.getTransaction().begin();
		runnable.run();
		entityManager.getTransaction().commit();
	}
}