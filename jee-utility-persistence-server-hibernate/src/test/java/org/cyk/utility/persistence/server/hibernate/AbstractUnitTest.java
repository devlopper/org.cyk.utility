package org.cyk.utility.persistence.server.hibernate;

import javax.persistence.Persistence;

import org.cyk.utility.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.persistence.query.QueryHelper;

public class AbstractUnitTest extends org.cyk.utility.test.weld.AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {		
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(getPersistenceUnitName());
		Initializer.initialize();
	}
	
	protected String getPersistenceUnitName() {
		return "default";
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
	}
	
}