package org.cyk.utility.client.controller.test.arquillian;

public abstract class AbstractControllerEntityIntegrationTest<ENTITY> extends AbstractControllerArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;
	/*
	@Test
	public void createOne() throws Exception{
		Object action = __inject__(SystemActionCreate.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		__deleteEntitiesAll__(object.getClass());
	}
	
	@Test
	public void createMany() throws Exception{
		Object action = __inject__(SystemActionCreate.class);
		Collection<ENTITY> entities = __instanciateEntity__(action,3);
		__createEntity__(entities);
		__deleteEntitiesAll__(__getEntityClass__(action));
	}
	
	@Test
	public void readOneBySystemIdentifier() throws Exception{
		Object action = __inject__(SystemActionRead.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		__readEntity__(__getEntityClass__(action),__getFieldValueSystemIdentifier__(object),ValueUsageType.SYSTEM);
		__deleteEntitiesAll__(object.getClass());
	}
	
	@Test
	public void readOneByBusinessIdentifier() throws Exception{
		Object action = __inject__(SystemActionRead.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		__readEntity__(__getEntityClass__(action),__getFieldValueBusinessIdentifier__(object), ValueUsageType.BUSINESS);
		__deleteEntitiesAll__(object.getClass());
	}
	
	@Test
	public void updateOne() throws Exception{
		Object action = __inject__(SystemActionUpdate.class);
		ENTITY object = __instanciateEntity__(action);
		__createEntity__(object);
		object = (ENTITY) __getBusinessEntity__(action).findOne(__inject__(FieldHelper.class).getFieldValueSystemIdentifier(object));
		__setEntityFields__(object,action);
		__updateEntity__(object);
		__deleteEntitiesAll__(object.getClass());
	}
	
	@Test
	public void deleteOne() throws Exception{
		Object action = __inject__(SystemActionDelete.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		object = __getBusinessEntity__(action).findOne(__getFieldValueSystemIdentifier__(object));
		__deleteEntity__(object);
		__deleteEntitiesAll__(object.getClass());
	}
	*/
	/**/
	/*
	@SuppressWarnings("unchecked")
	protected Class<ENTITY> __getEntityClass__(Object action){
		return (Class<ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 0, Object.class);
	}
	
	protected ControllerEntity<ENTITY> __getControllerEntity__(Object action){
		return (ControllerEntity<ENTITY>) __inject__(ControllerLayer.class).injectInterfaceClassFromPersistenceEntityClass(__getEntityClass__(action));
	}
	
	protected ENTITY __instanciateEntity__(Object action) throws Exception{
		return __instanciate__(__getEntityClass__(action), action);
	}
	
	protected Collection<ENTITY> __instanciateEntity__(Object action,Integer count) throws Exception{
		return __instanciate__(__getEntityClass__(action), action, count);
	}
	
	protected void __setEntityFields__(ENTITY entity,Object action){}
	*/
}
