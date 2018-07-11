package org.cyk.utility.server.persistence.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.test.AbstractTestIntegrationImpl;

public abstract class AbstractTestPersistenceServiceProviderFunctionImpl extends AbstractTestIntegrationImpl implements TestPersistenceServiceProviderFunction {
	private static final long serialVersionUID = 1L;

	@Inject private UserTransaction userTransaction;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setUserTransaction(userTransaction);
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
	public Object getObject() {
		return getProperties().getObject();
	}
	
	@Override
	public TestPersistenceServiceProviderFunction setObject(Object object) {
		getProperties().setObject(object);
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
	public final TestPersistenceServiceProviderFunction execute() {
		return (TestPersistenceServiceProviderFunction) super.execute();
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
	public TestPersistenceServiceProviderFunction assertThrowableCauseIsInstanceOfSqlException() {
		assertThrowableCauseIsInstanceOf(SQLException.class);
		return this;
	}
}
