package org.cyk.utility.test;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.transaction.UserTransaction;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractTestSystemFunctionIntegrationImpl extends AbstractTestIntegrationImpl implements TestSystemFunctionIntegration,Serializable {
	private static final long serialVersionUID = 1L;

	private Collection<Object> unexistingObjectIdentifiers;
	private Map<Class<?>,Object> actionableSingletonsMap;
	private Boolean isTransactional,isContainerManagedTransaction;
	private UserTransaction __tempUserTransaction__;
	
	@Override
	public UserTransaction getUserTransaction() {
		return (UserTransaction) getProperties().getFromPath(Properties.USER,Properties.TRANSACTION);
	}
	
	@Override
	public TestSystemFunctionIntegration setUserTransaction(UserTransaction userTransaction) {
		getProperties().setFromPath(new Object[]{Properties.USER,Properties.TRANSACTION}, userTransaction);
		return this;
	}
	
	@Override
	public Integer getExecutionCount() {
		return (Integer) getProperties().getFromPath(Properties.EXECUTION,Properties.COUNT);
	}
	
	@Override
	public TestSystemFunctionIntegration setExecutionCount(Integer count) {
		getProperties().setFromPath(new Object[]{Properties.EXECUTION,Properties.COUNT}, count);
		return this;
	}
	
	@Override
	public Class<?> getObjectClass() {
		return (Class<?>) getProperties().getClazz();
	}
	
	@Override
	public TestSystemFunctionIntegration setObjectClass(Class<?> aClass) {
		getProperties().setClass(aClass);
		return this;
	}
	
	@Override
	public Collection<Object> getObjects() {
		return (Collection<Object>) getProperties().getObjects();
	}
	
	@Override
	public TestSystemFunctionIntegration setObjects(Collection<Object> objects) {
		getProperties().setObjects(objects);
		return this;
	}
	
	@Override
	public TestSystemFunctionIntegration addObjects(Object... objects) {
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
	public TestSystemFunctionIntegration setIdentifierValueUsageType(ValueUsageType valueUsageType) {
		getProperties().setValueUsageType(valueUsageType);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> getObjectIdentifiers() {
		return (Collection<Object>) getProperties().getIdentifiers();
	}
	
	@Override
	public TestSystemFunctionIntegration setObjectIdentifiers(Collection<Object> objectIdentifiers) {
		getProperties().setIdentifiers(objectIdentifiers);
		return this;
	}
	
	@Override
	public TestSystemFunctionIntegration addObjectIdentifiers(Object... objectIdentifiers) {
		if(__inject__(ArrayHelper.class).isNotEmpty(objectIdentifiers)){
			Collection<Object> collection = getObjectIdentifiers();
			if(collection == null)
				setObjectIdentifiers(collection = new ArrayList<Object>());
			__inject__(CollectionHelper.class).add(collection, objectIdentifiers);
		}
		return this;
	}
	
	@Override
	public Collection<Object> getUnexistingObjectIdentifiers() {
		return unexistingObjectIdentifiers;
	}
	
	@Override
	public TestSystemFunctionIntegration setUnexistingObjectIdentifiers(Collection<Object> unexistingObjectIdentifiers) {
		this.unexistingObjectIdentifiers = unexistingObjectIdentifiers;
		return this;
	}
	
	@Override
	public Map<Class<?>,Object> getActionableSingletonsMap() {
		return actionableSingletonsMap;
	}
	
	@Override
	public TestSystemFunctionIntegration setActionableSingletonsMap(Map<Class<?>,Object> map) {
		this.actionableSingletonsMap = map;
		return this;
	}
	
	@Override
	public TestSystemFunctionIntegration setActionableSingleton(Class<?> aClass, Object object) {
		Map<Class<?>,Object> map = getActionableSingletonsMap();
		if(map == null) {
			setActionableSingletonsMap(map = new HashMap<>());
		}
		map.put(aClass, object);
		return this;
	}
	
	@Override
	public TestSystemFunctionIntegration addUnexistingObjectIdentifiers(Object... unexistingObjectIdentifiers) {
		if(__inject__(ArrayHelper.class).isNotEmpty(unexistingObjectIdentifiers)){
			Collection<Object> collection = getUnexistingObjectIdentifiers();
			if(collection == null)
				setUnexistingObjectIdentifiers(collection = new ArrayList<Object>());
			__inject__(CollectionHelper.class).add(collection, unexistingObjectIdentifiers);
		}
		return this;
	}
	
	@Override
	public TestSystemFunctionIntegration setIsTransactional(Boolean isTransactional) {
		this.isTransactional = isTransactional;
		return this;
	}
	
	@Override
	public Boolean getIsTransactional() {
		return isTransactional;
	}
	
	@Override
	public Boolean getIsContainerManagedTransaction() {
		return isContainerManagedTransaction;
	}
	
	@Override
	public TestSystemFunctionIntegration setIsContainerManagedTransaction(Boolean isContainerManagedTransaction) {
		this.isContainerManagedTransaction = isContainerManagedTransaction;
		return this;
	}
	
	@Override
	public TestSystemFunctionIntegration addObjectsToBeCreatedArray(Object... objects) {
		return (TestSystemFunctionIntegration) super.addObjectsToBeCreatedArray(objects);
	}
	
	@Override
	public TestSystemFunctionIntegration addObjectsToBeCreatedCollection(Collection<Object> objects) {
		return (TestSystemFunctionIntegration) super.addObjectsToBeCreatedCollection(objects);
	}
	
	@Override
	public TestSystemFunctionIntegration setName(String name) {
		return (TestSystemFunctionIntegration) super.setName(name);
	}
	
	@Override
	public TestSystemFunctionIntegration execute() {
		return (TestSystemFunctionIntegration) super.execute();
	}
	
	@Override
	public TestSystemFunctionIntegration setExpectedThrowableCauseClassIsConstraintViolationException() {
		return (TestSystemFunctionIntegration) super.setExpectedThrowableCauseClassIsConstraintViolationException();
	}
	
	@Override
	public TestSystemFunctionIntegration setExpectedThrowableCauseClassIsSqlException() {
		return (TestSystemFunctionIntegration) setExpectedThrowableCauseClass(SQLException.class);
	}
	
	@Override
	public TestSystemFunctionIntegration assertThrowableCauseIsInstanceOfSqlException() {
		assertThrowableCauseIsInstanceOf(SQLException.class);
		return this;
	}

	/**/
	
	protected Object __getActionableSingleton__(Object object) {
		Map<Class<?>,Object> map = getActionableSingletonsMap();
		Object value = null;
		if(map!=null) {
			Class<?> key;
			if(object instanceof Class<?>)
				key = (Class<?>) map.get(object);
			else
				key = object.getClass();
			value = map.get(key);
		}
		return value;
	}
	
	@Override
	protected void ______execute______() throws Exception{
		Collection<Object> objects = __getExecutionObjects__();
		Integer executionCount = __getExecutionCount__(objects);		
		__listenExecuteBeforeFor__();
		for(Integer index = 0 ; index < executionCount ; index++){
			for(Object indexObject : objects){
				__listenExecuteBeforePerform__();
				if(Boolean.TRUE.equals(getIsTransactional()) && !Boolean.TRUE.equals(getIsContainerManagedTransaction()))
					__beginTransaction__();
				__perform__(indexObject);
				if(Boolean.TRUE.equals(getIsTransactional()) && !Boolean.TRUE.equals(getIsContainerManagedTransaction()))
					__endTransaction__();
				__listenExecuteAfterPerform__();
				__assertAfterPerform__(indexObject);
			}
		}
		__listenExecuteAfterFor__();
	}

	protected abstract Collection<Object> __getExecutionObjects__() throws Exception;
	
	protected Integer __getExecutionCount__(Collection<Object> objects) throws Exception {
		Integer executionCount = getExecutionCount();
		if(executionCount == null)
			executionCount = 1;
		return executionCount;
	}
	
	protected void __listenExecuteBeforePerform__() throws Exception {}
	protected void __listenExecuteAfterPerform__() throws Exception {}
	
	protected void __listenExecuteBeforeFor__() throws Exception {}
	
	protected void __beginTransaction__() throws Exception {
		__tempUserTransaction__ = getUserTransaction();
		if(__tempUserTransaction__ == null)
			__tempUserTransaction__ = __inject__(UserTransaction.class);
		__tempUserTransaction__.begin();
	}
	
	protected void __endTransaction__() throws Exception {
		__tempUserTransaction__ = getUserTransaction();
		if(__tempUserTransaction__ == null)
			__tempUserTransaction__ = __inject__(UserTransaction.class);
		__tempUserTransaction__.commit();
	}
	
	protected abstract void __perform__(Object object) throws Exception;
	
	protected void __assertAfterPerform__(Object object) throws Exception {
		if(object.getClass().getAnnotation(Entity.class)!=null) {
			__assertSystemIdentifierIsNotNull__(object);
			__assertBusinessIdentifierIsNotNull__(object);
			__assertLogEventMessage__(object);		
		}
	}
	
	protected abstract void __deleteOne__(Object object);
	
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
