package org.cyk.utility.__kernel__.test.weld;

import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;

public abstract class AbstractPersistenceUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		initializeEntityManagerFactory(getPersistenceUnitName());
		createData();
	}
	
	protected void initializeEntityManagerFactory(String persistenceUnitName) {
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(persistenceUnitName);
	}
	
	protected String getPersistenceUnitName() {
		return "default";
	}
	
	protected void createData() {}
	protected void deleteData() {}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		deleteData();
		QueryHelper.clear();
	}
}