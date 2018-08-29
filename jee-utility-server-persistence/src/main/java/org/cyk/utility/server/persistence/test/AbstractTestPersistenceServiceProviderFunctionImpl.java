package org.cyk.utility.server.persistence.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.transaction.UserTransaction;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.test.AbstractTestIntegrationImpl;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractTestPersistenceServiceProviderFunctionImpl extends AbstractTestIntegrationImpl implements TestPersistenceServiceProviderFunction {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setUserTransaction(__inject__(UserTransaction.class));
	}
	
	@Override
	public UserTransaction getUserTransaction() {
		return (UserTransaction) getProperties().getFromPath(Properties.USER,Properties.TRANSACTION);
	}
	
	@Override
	public TestPersistenceServiceProviderFunction setUserTransaction(UserTransaction userTransaction) {
		getProperties().setFromPath(new Object[]{Properties.USER,Properties.TRANSACTION}, userTransaction);
		return this;
	}
	
	@Override
	public Integer getExecutionCount() {
		return (Integer) getProperties().getFromPath(Properties.EXECUTION,Properties.COUNT);
	}
	
	@Override
	public TestPersistenceServiceProviderFunction setExecutionCount(Integer count) {
		getProperties().setFromPath(new Object[]{Properties.EXECUTION,Properties.COUNT}, count);
		return this;
	}
	
	@Override
	public Class<?> getObjectClass() {
		return (Class<?>) getProperties().getClazz();
	}
	
	@Override
	public TestPersistenceServiceProviderFunction setObjectClass(Class<?> aClass) {
		getProperties().setClass(aClass);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> getObjects() {
		return (Collection<Object>) getProperties().getObjects();
	}
	
	@Override
	public TestPersistenceServiceProviderFunction setObjects(Collection<Object> objects) {
		getProperties().setObjects(objects);
		return this;
	}
	
	@Override
	public TestPersistenceServiceProviderFunction addObjects(Object... objects) {
		if(__inject__(ArrayHelper.class).isNotEmpty(objects)){
			Collection<Object> collection = getObjects();
			if(collection == null)
				setObjects(collection = new ArrayList<Object>());
			__inject__(CollectionHelper.class).add(collection, objects);
		}
		return this;
	}
	
	@Override
	public ValueUsageType getIdentifierValueUsageType() {
		return (ValueUsageType) getProperties().getValueUsageType();
	}
	
	@Override
	public TestPersistenceServiceProviderFunction setIdentifierValueUsageType(ValueUsageType valueUsageType) {
		getProperties().setValueUsageType(valueUsageType);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> getObjectIdentifiers() {
		return (Collection<Object>) getProperties().getIdentifiers();
	}
	
	@Override
	public TestPersistenceServiceProviderFunction setObjectIdentifiers(Collection<Object> objectIdentifiers) {
		getProperties().setIdentifiers(objectIdentifiers);
		return this;
	}
	
	@Override
	public TestPersistenceServiceProviderFunction addObjectIdentifiers(Object... objectIdentifiers) {
		if(__inject__(ArrayHelper.class).isNotEmpty(objectIdentifiers)){
			Collection<Object> collection = getObjectIdentifiers();
			if(collection == null)
				setObjectIdentifiers(collection = new ArrayList<Object>());
			__inject__(CollectionHelper.class).add(collection, objectIdentifiers);
		}
		return this;
	}
	
	@Override
	public TestPersistenceServiceProviderFunction addObjectsToBeCreatedArray(Object... objects) {
		return (TestPersistenceServiceProviderFunction) super.addObjectsToBeCreatedArray(objects);
	}
	
	@Override
	public TestPersistenceServiceProviderFunction addObjectsToBeCreatedCollection(Collection<Object> objects) {
		return (TestPersistenceServiceProviderFunction) super.addObjectsToBeCreatedCollection(objects);
	}
	
	@Override
	public TestPersistenceServiceProviderFunction setName(String name) {
		return (TestPersistenceServiceProviderFunction) super.setName(name);
	}
	
	@Override
	public final TestPersistenceServiceProviderFunction execute() {
		return (TestPersistenceServiceProviderFunction) super.execute();
	}
	
	@Override
	public TestPersistenceServiceProviderFunction assertThrowableCauseIsInstanceOfSqlException() {
		assertThrowableCauseIsInstanceOf(SQLException.class);
		return this;
	}

	@Override
	protected void __create__(Collection<Object> objects) {
		for(Object index : objects) {
			try {
				getUserTransaction().begin();
				__inject__(Persistence.class).create(index);
				getUserTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void __delete__(Collection<Object> objects) {
		for(Object index : objects) {
			try {
				getUserTransaction().begin();
				__inject__(Persistence.class).delete(index);
				getUserTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**/
	
	@Override
	protected void ______execute______() throws Exception{
		Collection<Object> objects = __getExecutionObjects__();
		Integer executionCount = __getExecutionCount__(objects);		
		__listenExecuteBeforeFor__();
		Boolean isTransactional = __getIsTransactional__();
		for(Integer index = 0 ; index < executionCount ; index++){
			for(Object indexObject : objects){
				if(Boolean.TRUE.equals(isTransactional))
					getUserTransaction().begin();
				__perform__(indexObject);
				if(Boolean.TRUE.equals(isTransactional))
					getUserTransaction().commit();
				__assertAfterPerform__(indexObject);
			}
		}
		__listenExecuteAfterFor__();
	}
	
	protected Boolean __getIsTransactional__() {
		return Boolean.FALSE;
	}
	
	protected abstract Collection<Object> __getExecutionObjects__() throws Exception;
	
	protected Integer __getExecutionCount__(Collection<Object> objects) throws Exception {
		Integer executionCount = getExecutionCount();
		if(executionCount == null)
			executionCount = 1;
		return executionCount;
	}
	
	protected void __listenExecuteBeforeFor__() throws Exception {}
	
	protected abstract void __perform__(Object object) throws Exception;
	
	protected void __assertAfterPerform__(Object object) throws Exception {
		if(object.getClass().getAnnotation(Entity.class)!=null) {
			__assertSystemIdentifierIsNotNull__(object);
			__assertBusinessIdentifierIsNotNull__(object);
			__assertLogEventMessage__(object);		
		}
		
	}
	
	protected void __assertSystemIdentifierIsNotNull__(Object object) {
		assertionHelper.assertNotNull(object, FieldName.IDENTIFIER,ValueUsageType.SYSTEM);
	}
	
	protected void __assertBusinessIdentifierIsNotNull__(Object object) {
		assertionHelper.assertNotNull(object, FieldName.IDENTIFIER,ValueUsageType.BUSINESS);
	}
	
	protected void __assertLogEventMessage__(Object object) {
		//assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Create "+object.getClass().getSimpleName())
		//.assertContainsLastLogEventMessage("code="+__inject__(FieldValueGetter.class).execute(object,FieldName.IDENTIFIER,ValueUsageType.BUSINESS).getOutput());
	}
	
	protected void __listenExecuteAfterFor__() throws Exception {}
}
