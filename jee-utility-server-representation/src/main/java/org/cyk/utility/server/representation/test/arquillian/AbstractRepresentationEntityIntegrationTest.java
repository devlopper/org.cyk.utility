package org.cyk.utility.server.representation.test.arquillian;

import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.clazz.ClassHelperImpl;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.server.representation.AbstractEntity;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.test.ExpectedMessageDto;
import org.cyk.utility.server.representation.test.TestRepresentationCreate;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.value.ValueUsageType;
import org.junit.Test;

public abstract class AbstractRepresentationEntityIntegrationTest<ENTITY> extends AbstractRepresentationArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void createOne() throws Exception{
		Object object = __instanciateEntity__(null);
		__inject__(TestRepresentationCreate.class).addObjects(object)
		.setExpectedResponseStatusCode(Response.Status.CREATED.getStatusCode())
			.addExpectedResponseEntityMessages(new ExpectedMessageDto()
				.setValueContains("a été créé avec succès.")).execute();
	}
	
	@Test
	public void createOne_businessIdentifierMustNotBeNull() throws Exception{
		Object object = __instanciateEntity__(null);
		__inject__(FieldHelper.class).setFieldValueBusinessIdentifier(object, null);
		__inject__(TestRepresentationCreate.class).addObjects(object)
		.setExpectedResponseStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
			.addExpectedResponseEntityMessages(new ExpectedMessageDto()
				.setValueContains("Une erreur est survenue")).execute();
	}
	
	@Test
	public void createOne_businessIdentifierMustBeUnique() throws Exception{
		Object object1 = __instanciateEntity__(null);
		Object object2 = __inject__(InstanceHelper.class).buildOne(object1.getClass(), object1);
		__inject__(TestRepresentationCreate.class).addObjectsToBeCreatedArray(object1).addObjects(object2)
		.setExpectedResponseStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).addExpectedResponseEntityMessages(new ExpectedMessageDto()
				.setValueContains("Une erreur est survenue")).execute();
	}
	
	@Test
	public void createMany() throws Exception{
		Collection<ENTITY> entities = __instanciateEntity__(null,3);
		__inject__(TestRepresentationCreate.class).addObjects(entities.toArray()).execute();
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
		Object action = null;//__inject__(SystemActionRead.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		__readEntity__(__getEntityClass__(action),__getFieldValueBusinessIdentifier__(object), ValueUsageType.BUSINESS);
		__deleteEntitiesAll__(object.getClass());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void updateOne() throws Exception{
		Object action = null;//__inject__(SystemActionUpdate.class);
		ENTITY object = __instanciateEntity__(action);
		__createEntity__(object);
		Object identifier = __getFieldValueSystemIdentifier__(object); //__inject__(FieldHelper.class).getFieldValueSystemIdentifier(object);
		object = (ENTITY) __getRepresentationEntity__(action).getOne(identifier == null ? null : identifier.toString(),ValueUsageType.SYSTEM.name()).getEntity();
		__setEntityFields__(object,action);
		__updateEntity__(object);
		__deleteEntitiesAll__(object.getClass());
	}
	
	@Test
	public void deleteOne() throws Exception{
		Object action = null;//__inject__(SystemActionDelete.class);
		Object object = __instanciateEntity__(action);
		__createEntity__(object);
		Object identifier = __getFieldValueSystemIdentifier__(object);//__getSystemIdentifier__(object);
		object = __getRepresentationEntity__(action).getOne(identifier == null ? null : identifier.toString(),ValueUsageType.SYSTEM.name()).getEntity();
		__deleteEntity__(object);
		__deleteEntitiesAll__(object.getClass());
	}
	
	/**/
	
	@Override
	protected Object __getFieldValueSystemIdentifier__(Object object) {
		if(object instanceof AbstractEntity)
			return ((AbstractEntity)__getLayerEntityInterfaceFromObject__(object).getOne(((AbstractEntity)object).getCode(),ValueUsageType.BUSINESS.name())
					.getEntity()).getIdentifier();
		return super.__getFieldValueSystemIdentifier__(object);
	}
	
	/*@Override
	protected Object __getSystemIdentifier__(Object object) {
		if(object instanceof AbstractEntity)
			return ((AbstractEntity)__getLayerEntityInterfaceFromObject__(object).getOne(((AbstractEntity)object).getCode(),ValueUsageType.BUSINESS.name())
					.getEntity()).getIdentifier();
		return super.__getSystemIdentifier__(object);
	}*/
	
	@Override
	protected Object __getFieldValueBusinessIdentifier__(Object object) {
		if(object instanceof AbstractEntity)
			return ((AbstractEntity)object).getCode();
		return super.__getFieldValueBusinessIdentifier__(object);
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
