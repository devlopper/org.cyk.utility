package org.cyk.utility.server.persistence.test;

import java.util.Collection;

import javax.transaction.UserTransaction;

import org.cyk.utility.test.TestIntegration;

public interface TestPersistenceServiceProviderFunction extends TestIntegration {

	UserTransaction getUserTransaction();
	TestPersistenceServiceProviderFunction setUserTransaction(UserTransaction userTransaction);
	
	Object getObject();
	TestPersistenceServiceProviderFunction setObject(Object object);
	
	Collection<Object> getObjects();
	TestPersistenceServiceProviderFunction setObjects(Collection<Object> objects);
	TestPersistenceServiceProviderFunction addObjects(Object...objects);
	
	TestPersistenceServiceProviderFunction setExecutionCount(Integer count);
	Integer getExecutionCount();
	
	TestPersistenceServiceProviderFunction assertThrowableCauseIsInstanceOfSqlException();
	
	TestPersistenceServiceProviderFunction execute();
}
