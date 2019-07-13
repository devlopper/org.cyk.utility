package org.cyk.utility.server.business.test.arquillian;

import java.util.Collection;

import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldsGetter;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.Fields;
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
		Fields fields = __inject__(FieldsGetter.class).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.BUSINESS).setClazz(object.getClass()).execute().getOutput();
		if(__inject__(CollectionHelper.class).isNotEmpty(fields)) {
			__createEntity__(object);
			__readEntity__(__getEntityClass__(action),__getFieldValueBusinessIdentifier__(object), ValueUsageType.BUSINESS);
			__deleteEntitiesAll__(object.getClass());	
		}
	}
	
	@Test
	public void updateOne() throws Exception{
		Object action = __inject__(SystemActionUpdate.class);
		ENTITY object = __instanciateEntity__(action);
		__createEntity__(object);
		object = (ENTITY) __getBusinessEntity__(action).findByIdentifier(__inject__(FieldHelper.class).getFieldValueSystemIdentifier(object));
		__setEntityFields__(object,action);
		__updateEntity__(object);
		__deleteEntitiesAll__(object.getClass());
	}
	
	@Test
	public void deleteOne() throws Exception{
		Object action = __inject__(SystemActionDelete.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		object = __getBusinessEntity__(action).findByIdentifier(__getFieldValueSystemIdentifier__(object));
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
		return __instanciate__(__getEntityClass__(action), action);
	}
	
	protected Collection<ENTITY> __instanciateEntity__(Object action,Integer count) throws Exception{
		return __instanciate__(__getEntityClass__(action), action, count);
	}
	
	protected void __setEntityFields__(ENTITY entity,Object action){}
}
