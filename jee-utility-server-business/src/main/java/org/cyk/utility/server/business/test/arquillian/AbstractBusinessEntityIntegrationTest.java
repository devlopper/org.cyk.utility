package org.cyk.utility.server.business.test.arquillian;

import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.server.business.BusinessEntity;
import org.cyk.utility.server.business.BusinessLayer;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.value.ValueUsageType;
import org.junit.Test;

public abstract class AbstractBusinessEntityIntegrationTest<ENTITY> extends AbstractBusinessArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void createOne() throws Exception{
		Object action = __inject__(SystemActionCreate.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		__deleteEntitiesAll__(object.getClass());
	}
	
	@Test
	public void readOneBySystemIdentifier() throws Exception{
		Object action = __inject__(SystemActionRead.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		__readEntity__(__getEntityClass__(action),__getSystemIdentifier__(object),ValueUsageType.SYSTEM);
		__deleteEntitiesAll__(object.getClass());
	}
	
	@Test
	public void readOneByBusinessIdentifier() throws Exception{
		Object action = __inject__(SystemActionRead.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		__readEntity__(__getEntityClass__(action),__getBusinessIdentifier__(object), ValueUsageType.BUSINESS);
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
		object = __getBusinessEntity__(action).findOne(__getSystemIdentifier__(object));
		__deleteEntity__(object);
		__deleteEntitiesAll__(object.getClass());
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	protected Class<ENTITY> __getEntityClass__(Object action){
		return (Class<ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 0, Object.class);
	}
	
	protected BusinessEntity<ENTITY> __getBusinessEntity__(Object action){
		return (BusinessEntity<ENTITY>) __inject__(BusinessLayer.class).injectInterfaceClassFromPersistenceEntityClass(__getEntityClass__(action));
	}
	
	protected ENTITY __instanciateEntity__(Object action) throws Exception{
		ENTITY object = __inject__(ClassHelper.class).instanciateOne(__getEntityClass__(action));
		__inject__(FieldHelper.class).setFieldValueBusinessIdentifier(object, getRandomCode());
		return object;
	}
	
	protected void __setEntityFields__(ENTITY entity,Object action){}
}
