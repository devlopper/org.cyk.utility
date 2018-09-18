package org.cyk.utility.test;

import java.util.Collection;

import javax.transaction.UserTransaction;

import org.cyk.utility.value.ValueUsageType;

public interface TestSystemFunctionIntegration extends TestIntegration {

	UserTransaction getUserTransaction();
	TestSystemFunctionIntegration setUserTransaction(UserTransaction userTransaction);
	
	Class<?> getObjectClass();
	TestSystemFunctionIntegration setObjectClass(Class<?> objectClass);
	
	Collection<Object> getObjects();
	TestSystemFunctionIntegration setObjects(Collection<Object> objects);
	TestSystemFunctionIntegration addObjects(Object...objects);
	
	ValueUsageType getIdentifierValueUsageType();
	TestSystemFunctionIntegration setIdentifierValueUsageType(ValueUsageType valueUsageType);
	
	Collection<Object> getObjectIdentifiers();
	TestSystemFunctionIntegration setObjectIdentifiers(Collection<Object> objectIdentifiers);
	TestSystemFunctionIntegration addObjectIdentifiers(Object...objectIdentifiers);
	
	Collection<Object> getUnexistingObjectIdentifiers();
	TestSystemFunctionIntegration setUnexistingObjectIdentifiers(Collection<Object> unexistingObjectIdentifiers);
	TestSystemFunctionIntegration addUnexistingObjectIdentifiers(Object...unexistingObjectIdentifiers);
	
	TestSystemFunctionIntegration setExecutionCount(Integer count);
	Integer getExecutionCount();
	
	@Override TestSystemFunctionIntegration setExpectedThrowableCauseClassIsConstraintViolationException();
	TestSystemFunctionIntegration setExpectedThrowableCauseClassIsSqlException();
	TestSystemFunctionIntegration assertThrowableCauseIsInstanceOfSqlException();
	
	TestSystemFunctionIntegration execute();
	
	@Override TestSystemFunctionIntegration addObjectsToBeCreatedCollection(Collection<Object> objects);
	@Override TestSystemFunctionIntegration addObjectsToBeCreatedArray(Object... objects);
	
	@Override TestSystemFunctionIntegration setName(String name);
	
	TestSystemFunctionIntegration setIsTransactional(Boolean isTransactional);
	Boolean getIsTransactional();
	
	TestSystemFunctionIntegration setIsContainerManagedTransaction(Boolean isContainerManagedTransaction);
	Boolean getIsContainerManagedTransaction();
}
