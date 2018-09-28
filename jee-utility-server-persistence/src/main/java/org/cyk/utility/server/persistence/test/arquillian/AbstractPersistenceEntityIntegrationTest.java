package org.cyk.utility.server.persistence.test.arquillian;

import java.util.Collection;

import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.server.persistence.PersistenceEntity;
import org.cyk.utility.server.persistence.PersistenceLayer;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.value.ValueUsageType;
import org.junit.Test;

public abstract class AbstractPersistenceEntityIntegrationTest<ENTITY> extends AbstractPersistenceArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void createOne() throws Exception{
		Object action = __inject__(SystemActionCreate.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		//cleanup
		__deleteEntitiesAll__(object.getClass());
	}
	
	@Test
	public void createMany() throws Exception{
		Object action = __inject__(SystemActionCreate.class);
		Collection<ENTITY> entities = __instanciateEntity__(action,3);
		__createEntity__(entities);
		//cleanup
		__deleteEntitiesAll__(__getEntityClass__(action));
	}
	
	@Test
	public void readOneBySystemIdentifier() throws Exception{
		Object action = __inject__(SystemActionRead.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		__readEntity__(__getEntityClass__(action),__getFieldValueSystemIdentifier__(object),ValueUsageType.SYSTEM);
		//cleanup
		__deleteEntitiesAll__(object.getClass());
	}
	
	@Test
	public void readOneByBusinessIdentifier() throws Exception{
		Object action = __inject__(SystemActionRead.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		__readEntity__(__getEntityClass__(action),__getFieldValueBusinessIdentifier__(object), ValueUsageType.BUSINESS);
		//cleanup
		__deleteEntitiesAll__(object.getClass());
	}
	
	@Test
	public void updateOne() throws Exception{
		Object action = __inject__(SystemActionUpdate.class);
		ENTITY object = __instanciateEntity__(action);
		__createEntity__(object);
		object = (ENTITY) __getPersistenceEntity__(action).readOne(__inject__(FieldHelper.class).getFieldValueSystemIdentifier(object));
		__setEntityFields__(object,action);
		__updateEntity__(object);
		//cleanup
		__deleteEntitiesAll__(object.getClass());
	}
	
	@Test
	public void deleteOne() throws Exception{
		Object action = __inject__(SystemActionDelete.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		object = __getPersistenceEntity__(action).readOne(__getFieldValueSystemIdentifier__(object));
		__deleteEntity__(object);
		//cleanup
		__deleteEntitiesAll__(object.getClass());
	}
	
	//TODO following code must be enable
	
	/*
	@Test(expected=javax.transaction.RollbackException.class)
	public void isCodeMustBeUnique() throws Exception{
		String code = getRandomCode();
		userTransaction.begin();
		__getPersistenceEntity__(null).create(new MyEntity().setCode(code));
		assertThat(logEventEntityRepository.getLastMessage()).startsWith("Server Persistence Create MyEntity").contains("code="+code);
		__getPersistenceEntity__(null).create(new MyEntity().setCode(code));
		userTransaction.commit();
	}
	
	@Test(expected=javax.transaction.RollbackException.class)
	public void isCodeMustBeNotNull() throws Exception{
		userTransaction.begin();
		__getPersistenceEntity__(null).create(new MyEntity());
		userTransaction.commit();
	}
	*/
	
	/**/
	
	@SuppressWarnings("unchecked")
	protected Class<ENTITY> __getEntityClass__(Object action){
		return (Class<ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 0, Object.class);
	}
	
	protected PersistenceEntity<ENTITY> __getPersistenceEntity__(Object action){
		return (PersistenceEntity<ENTITY>) __inject__(PersistenceLayer.class).injectInterfaceClassFromEntityClass(__getEntityClass__(action));
	}
	
	protected ENTITY __instanciateEntity__(Object action) throws Exception{
		return __instanciate__(__getEntityClass__(action), action);
	}
	
	protected Collection<ENTITY> __instanciateEntity__(Object action,Integer count) throws Exception{
		return __instanciate__(__getEntityClass__(action), action, count);
	}
	
	protected void __setEntityFields__(ENTITY entity,Object action){}
}
