package org.cyk.utility.server.representation.test.arquillian;

import java.util.Collection;

import org.cyk.utility.clazz.ClassHelperImpl;
import org.cyk.utility.server.representation.AbstractEntity;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.value.ValueUsageType;
import org.junit.Test;

public abstract class AbstractRepresentationEntityIntegrationTest<ENTITY> extends AbstractRepresentationArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void createOne() throws Exception{
		Object object = __instanciateEntity__(null);
		__createEntity__(object);
		__deleteEntitiesAll__(object.getClass());
	}
	
	@Test
	public void createMany() throws Exception{
		Collection<ENTITY> entities = __instanciateEntity__(null,3);
		__createEntity__(entities);
		__deleteEntitiesAll__(__getEntityClass__(null));
	}
	
	@Test
	public void readOneBySystemIdentifier() throws Exception{
		Object action = null;//__inject__(SystemActionRead.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		__readEntity__(__getEntityClass__(action),__getSystemIdentifier__(object),ValueUsageType.SYSTEM);
		__deleteEntitiesAll__(object.getClass());
	}
	
	@Test
	public void readOneByBusinessIdentifier() throws Exception{
		Object action = null;//__inject__(SystemActionRead.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		__readEntity__(__getEntityClass__(action),__getBusinessIdentifier__(object), ValueUsageType.BUSINESS);
		__deleteEntitiesAll__(object.getClass());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void updateOne() throws Exception{
		Object action = null;//__inject__(SystemActionUpdate.class);
		ENTITY object = __instanciateEntity__(action);
		__createEntity__(object);
		Object identifier = getFieldValueSystemIdentifier(object); //__inject__(FieldHelper.class).getFieldValueSystemIdentifier(object);
		object = (ENTITY) __getRepresentationEntity__(action).getOne(identifier == null ? null : identifier.toString(),ValueUsageType.SYSTEM.name()).readEntity(object.getClass());
		__setEntityFields__(object,action);
		__updateEntity__(object);
		__deleteEntitiesAll__(object.getClass());
	}
	
	@Test
	public void deleteOne() throws Exception{
		Object action = null;//__inject__(SystemActionDelete.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		Object identifier = getFieldValueSystemIdentifier(object);//__getSystemIdentifier__(object);
		object = __getRepresentationEntity__(action).getOne(identifier == null ? null : identifier.toString(),ValueUsageType.SYSTEM.name()).readEntity(object.getClass());
		__deleteEntity__(object);
		__deleteEntitiesAll__(object.getClass());
	}
	
	/**/
	
	@Override
	protected Object getFieldValueSystemIdentifier(Object object) {
		if(object instanceof AbstractEntity)
			return ((AbstractEntity)__getLayerEntityInterfaceFromObject__(object).getOne(((AbstractEntity)object).getCode(),ValueUsageType.BUSINESS.name())
					.readEntity(object.getClass())).getIdentifier();
		return super.getFieldValueSystemIdentifier(object);
	}
	
	@Override
	protected Object __getSystemIdentifier__(Object object) {
		if(object instanceof AbstractEntity)
			return ((AbstractEntity)__getLayerEntityInterfaceFromObject__(object).getOne(((AbstractEntity)object).getCode(),ValueUsageType.BUSINESS.name())
					.readEntity(object.getClass())).getIdentifier();
		return super.__getSystemIdentifier__(object);
	}
	
	@Override
	protected Object __getBusinessIdentifier__(Object object) {
		if(object instanceof AbstractEntity)
			return ((AbstractEntity)object).getCode();
		return super.__getBusinessIdentifier__(object);
	}
	
	@SuppressWarnings("unchecked")
	protected Class<ENTITY> __getEntityClass__(Object action){
		return (Class<ENTITY>) ClassHelperImpl.__getParameterAt__(getClass(), 0, Object.class);
	}
	
	@SuppressWarnings("unchecked")
	protected RepresentationEntity<ENTITY,?,?> __getRepresentationEntity__(Object action){
		return __getLayerEntityInterfaceFromClass__(null);//(RepresentationEntity<ENTITY,?>) __inject__(RepresentationLayer.class).injectInterfaceClassFromEntityClass(__getEntityClass__(action));
	}
	
	protected ENTITY __instanciateEntity__(Object action) throws Exception{
		return __instanciate__(__getEntityClass__(action), action);
	}
	
	protected Collection<ENTITY> __instanciateEntity__(Object action,Integer count) throws Exception{
		return __instanciate__(__getEntityClass__(action), action, count);
	}
	
	/*protected ENTITY __instanciateEntity__(Object action) throws Exception{
		ENTITY object = __getEntityClass__(action).newInstance();
		if(object instanceof AbstractEntity)
			((AbstractEntity)object).setCode(String.valueOf(System.currentTimeMillis()));
		return object;
	}*/
	
	protected void __setEntityFields__(ENTITY entity,Object action){}
}
