package org.cyk.utility.server.persistence.test.arquillian;

import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.field.FieldValueSetter;
import org.cyk.utility.server.persistence.PersistenceEntity;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.system.layer.SystemLayerPersistence;
import org.cyk.utility.value.ValueUsageType;
import org.junit.Test;

public abstract class AbstractPersistenceEntityIntegrationTest<ENTITY> extends AbstractArquillianIntegrationTestWithDefaultDeploymentAsSwram {
	private static final long serialVersionUID = 1L;

	@Test
	public void createOne() throws Exception{
		Object action = __inject__(SystemActionCreate.class);
		@SuppressWarnings("unchecked")
		PersistenceEntity<Object> persistence = (PersistenceEntity<Object>) __getPersistenceEntity__(action);
		Object object = __instanciateEntity__(action);
		create(persistence, object);
	}
	
	@Test
	public void readOneBySystemIdentifier() throws Exception{
		Object action = __inject__(SystemActionRead.class);
		@SuppressWarnings("unchecked")
		PersistenceEntity<Object> persistence = (PersistenceEntity<Object>) __getPersistenceEntity__(action);
		Object object = __instanciateEntity__(action);
		create(persistence, object);
		read(persistence, __inject__(FieldValueGetter.class).execute(object, FieldName.IDENTIFIER, ValueUsageType.SYSTEM).getOutput(), ValueUsageType.SYSTEM);
	}
	
	@Test
	public void readOneByBusinessIdentifier() throws Exception{
		Object action = __inject__(SystemActionRead.class);
		@SuppressWarnings("unchecked")
		PersistenceEntity<Object> persistence = (PersistenceEntity<Object>) __getPersistenceEntity__(action);
		Object object = __instanciateEntity__(action);
		create(persistence, object);
		read(persistence, __inject__(FieldValueGetter.class).execute(object, FieldName.IDENTIFIER, ValueUsageType.BUSINESS).getOutput(), ValueUsageType.BUSINESS);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void updateOne() throws Exception{
		Object action = __inject__(SystemActionUpdate.class);
		PersistenceEntity<Object> persistence = (PersistenceEntity<Object>) __getPersistenceEntity__(action);
		ENTITY object = __instanciateEntity__(action);
		create(persistence, object);
		Object identifier = __inject__(FieldValueGetter.class).execute(object, FieldName.IDENTIFIER, ValueUsageType.SYSTEM).getOutput();
		object = (ENTITY) persistence.readOne(identifier);
		__setEntityFields__(object,action);
		update(persistence, object);
	}
	
	@Test
	public void deleteOne() throws Exception{
		Object action = __inject__(SystemActionDelete.class);
		@SuppressWarnings("unchecked")
		PersistenceEntity<Object> persistence = (PersistenceEntity<Object>) __getPersistenceEntity__(action);
		Object object = __instanciateEntity__(action);
		create(persistence, object);
		Object identifier = __inject__(FieldValueGetter.class).execute(object, FieldName.IDENTIFIER, ValueUsageType.SYSTEM).getOutput();
		object = persistence.readOne(identifier);
		delete(persistence, object);
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	protected Class<ENTITY> __getEntityClass__(Object action){
		return (Class<ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 0, Object.class);
	}
	
	@SuppressWarnings("unchecked")
	protected PersistenceEntity<ENTITY> __getPersistenceEntity__(Object action){
		return (PersistenceEntity<ENTITY>) __inject__(SystemLayerPersistence.class).injectInterfaceClassFromEntityClassName(__getEntityClass__(action));
	}
	
	protected ENTITY __instanciateEntity__(Object action) throws Exception{
		ENTITY object = __inject__(ClassHelper.class).instanciateOne(__getEntityClass__(action));
		__inject__(FieldValueSetter.class).setObject(object).setField(FieldName.IDENTIFIER, ValueUsageType.BUSINESS).setValue(getRandomCode()).execute();
		return object;
	}
	
	protected void __setEntityFields__(ENTITY entity,Object action){}
}
