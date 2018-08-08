package org.cyk.utility.server.representation.test.arquillian;

import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.RepresentationLayer;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.value.ValueUsageType;
import org.junit.Test;

public abstract class AbstractRepresentationEntityIntegrationTest<ENTITY> extends AbstractRepresentationArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;

	/*@Test
	public void createOne() throws Exception{
		//Object action = __inject__(SystemActionCreate.class);
		//Object object = __instanciateEntity__(action);
		//__createEntity__(object);
	}*/
	
	//@Test
	public void readOneBySystemIdentifier() throws Exception{
		Object action = __inject__(SystemActionRead.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		__readEntity__(__getEntityClass__(action),__getSystemIdentifier__(object),ValueUsageType.SYSTEM);
	}
	
	//@Test
	public void readOneByRepresentationIdentifier() throws Exception{
		Object action = __inject__(SystemActionRead.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		__readEntity__(__getEntityClass__(action),__getBusinessIdentifier__(object), ValueUsageType.BUSINESS);
	}
	
	//@Test
	public void updateOne() throws Exception{
		Object action = __inject__(SystemActionUpdate.class);
		ENTITY object = __instanciateEntity__(action);
		__createEntity__(object);
		object = (ENTITY) __getRepresentationEntity__(action).findOne(__inject__(FieldHelper.class).getFieldValueSystemIdentifier(object));
		__setEntityFields__(object,action);
		__updateEntity__(object);
	}
	
	//@Test
	public void deleteOne() throws Exception{
		Object action = __inject__(SystemActionDelete.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		object = __getRepresentationEntity__(action).findOne(__getSystemIdentifier__(object));
		__deleteEntity__(object);
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	protected Class<ENTITY> __getEntityClass__(Object action){
		return (Class<ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 0, Object.class);
	}
	
	protected RepresentationEntity<ENTITY,?> __getRepresentationEntity__(Object action){
		return null;//(RepresentationEntity<ENTITY,?>) __inject__(RepresentationLayer.class).injectInterfaceClassFromEntityClass(__getEntityClass__(action));
	}
	
	protected ENTITY __instanciateEntity__(Object action) throws Exception{
		ENTITY object = __inject__(ClassHelper.class).instanciateOne(__getEntityClass__(action));
		__inject__(FieldHelper.class).setFieldValueBusinessIdentifier(object, getRandomCode());
		return object;
	}
	
	protected void __setEntityFields__(ENTITY entity,Object action){}
}
