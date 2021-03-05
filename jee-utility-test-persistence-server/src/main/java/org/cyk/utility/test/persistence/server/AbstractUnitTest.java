package org.cyk.utility.test.persistence.server;

import javax.persistence.Persistence;

import org.cyk.utility.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.persistence.server.Initializer;
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
		Initializer.initialize();
	}
	
	protected String getPersistenceUnitName() {
		return "default";
	}
}