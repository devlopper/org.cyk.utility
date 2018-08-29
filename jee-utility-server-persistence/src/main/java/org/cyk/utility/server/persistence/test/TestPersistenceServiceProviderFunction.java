package org.cyk.utility.server.persistence.test;

import java.util.Collection;

import javax.transaction.UserTransaction;

import org.cyk.utility.test.TestIntegration;
import org.cyk.utility.value.ValueUsageType;

public interface TestPersistenceServiceProviderFunction extends TestIntegration {

	UserTransaction getUserTransaction();
	TestPersistenceServiceProviderFunction setUserTransaction(UserTransaction userTransaction);
	
	Class<?> getObjectClass();
	TestPersistenceServiceProviderFunction setObjectClass(Class<?> objectClass);
	
	Collection<Object> getObjects();
	TestPersistenceServiceProviderFunction setObjects(Collection<Object> objects);
	TestPersistenceServiceProviderFunction addObjects(Object...objects);
	
	ValueUsageType getIdentifierValueUsageType();
	TestPersistenceServiceProviderFunction setIdentifierValueUsageType(ValueUsageType valueUsageType);
	
	Collection<Object> getObjectIdentifiers();
	TestPersistenceServiceProviderFunction setObjectIdentifiers(Collection<Object> objectIdentifiers);
	TestPersistenceServiceProviderFunction addObjectIdentifiers(Object...objectIdentifiers);
	
	TestPersistenceServiceProviderFunction setExecutionCount(Integer count);
	Integer getExecutionCount();
	
	@Override TestPersistenceServiceProviderFunction setExpectedThrowableCauseClassIsConstraintViolationException();
	TestPersistenceServiceProviderFunction setExpectedThrowableCauseClassIsSqlException();
	TestPersistenceServiceProviderFunction assertThrowableCauseIsInstanceOfSqlException();
	
	TestPersistenceServiceProviderFunction execute();
	
	@Override TestPersistenceServiceProviderFunction addObjectsToBeCreatedCollection(Collection<Object> objects);
	@Override TestPersistenceServiceProviderFunction addObjectsToBeCreatedArray(Object... objects);
	
	@Override TestPersistenceServiceProviderFunction setName(String name);
}
