package org.cyk.utility.server.representation.test.arquillian;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.constant.ConstantNull;
import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.string.StringLocation;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.field.FieldNameGetter;
import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.map.MapHelper;
import org.cyk.utility.random.RandomHelper;
import org.cyk.utility.server.representation.AbstractEntityCollection;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.RepresentationLayer;
import org.cyk.utility.server.representation.ResponseEntityDto;
import org.cyk.utility.server.representation.test.ExpectedMessageDto;
import org.cyk.utility.server.representation.test.TestRepresentationCreate;
import org.cyk.utility.server.representation.test.TestRepresentationDelete;
import org.cyk.utility.server.representation.test.TestRepresentationRead;
import org.cyk.utility.server.representation.test.TestRepresentationUpdate;
import org.junit.Test;

public abstract class AbstractRepresentationEntityIntegrationTest<ENTITY> extends AbstractRepresentationArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void createOne() throws Exception{
		Object object = __instanciateEntity__(null);
		__inject__(TestRepresentationCreate.class).addObjects(object)
		.setExpectedResponseStatusCode(Response.Status.CREATED.getStatusCode())
		.setExpectedResponseEntityClass(ResponseEntityDto.class)
			.addExpectedResponseEntityDtoMessages(__inject__(ExpectedMessageDto.class).addHeadExpectedStrings(StringLocation.INSIDE,"a été Create avec succès.")).execute();
	}
	
	@Test
	public void createOne_businessIdentifierMustNotBeNull() throws Exception{
		Object object = __instanciateEntity__(null);
		org.cyk.utility.__kernel__.field.FieldHelper.writeBusinessIdentifier(object, null);
		__inject__(TestRepresentationCreate.class).addObjects(object)
		.setExpectedResponseStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
		.setExpectedResponseEntityClass(ResponseEntityDto.class)
			.addExpectedResponseEntityDtoMessages(__inject__(ExpectedMessageDto.class).addHeadExpectedStrings(StringLocation.INSIDE,"Une erreur est survenue")).execute();
	}
	
	@Test
	public void createOne_businessIdentifierMustBeUnique() throws Exception{
		Object object1 = __instanciateEntity__(null);
		Object object2 = __inject__(InstanceHelper.class).buildOne(object1.getClass(), object1);
		__inject__(TestRepresentationCreate.class).addObjectsToBeCreatedArray(object1).addObjects(object2)
		.setExpectedResponseStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
		.setExpectedResponseEntityClass(ResponseEntityDto.class)
		.addExpectedResponseEntityDtoMessages(__inject__(ExpectedMessageDto.class).addHeadExpectedStrings(StringLocation.INSIDE,"Une erreur est survenue")).execute();
	}
	
	@Test
	public void createMany() throws Exception{
		Collection<ENTITY> entities = __instanciateEntity__(null,3);
		__inject__(TestRepresentationCreate.class).addObjects(entities.toArray()).execute();
	}
	
	@Test
	public void readOneBySystemIdentifier() throws Exception{
		Object object = __instanciateEntity__(null);
		Object businessIdentifier = __getFieldValueBusinessIdentifier__(object);
		__inject__(RepresentationLayer.class).injectInterfaceClassFromEntity(object).createOne(object);
		object = __inject__(RepresentationLayer.class).injectInterfaceClassFromEntity(object).getOne(businessIdentifier.toString()
				, ValueUsageType.BUSINESS.name(),null).getEntity();
		Object systemIdentifier = __getFieldValueSystemIdentifier__(object);
		
		__inject__(TestRepresentationRead.class).addGarbagesArray(object).addObjectIdentifiers(systemIdentifier).setObjectClass(object.getClass())
		.setIdentifierValueUsageType(ValueUsageType.SYSTEM)
		.setExpectedResponseStatusCode(Response.Status.OK.getStatusCode())
		.setExpectedResponseEntityClass(object.getClass())
		.execute();
	}
	
	@Test
	public void readOneBySystemIdentifier_notFound() throws Exception{
		Object object = __instanciateEntity__(null);
		Object businessIdentifier = __getFieldValueBusinessIdentifier__(object);
		__inject__(RepresentationLayer.class).injectInterfaceClassFromEntity(object).createOne(object);
		object = __inject__(RepresentationLayer.class).injectInterfaceClassFromEntity(object).getOne(businessIdentifier.toString()
				, ValueUsageType.BUSINESS.name(),null).getEntity();
		Object systemIdentifier = __getFieldValueSystemIdentifier__(object).toString()+"1";
		
		__inject__(TestRepresentationRead.class).addGarbagesArray(object).addObjectIdentifiers(systemIdentifier).addUnexistingObjectIdentifiers(systemIdentifier).setObjectClass(object.getClass())
		.setIdentifierValueUsageType(ValueUsageType.SYSTEM)
		.setExpectedResponseStatusCode(Response.Status.NOT_FOUND.getStatusCode())
		.setExpectedResponseEntityIsNull(Boolean.TRUE)
		.execute();
	}
	
	@Test
	public void readOneByBusinessIdentifier() throws Exception{
		Object object = __instanciateEntity__(null);
		Object businessIdentifier = __getFieldValueBusinessIdentifier__(object);
		__inject__(TestRepresentationRead.class).addObjectsToBeCreatedArray(object).addObjectIdentifiers(businessIdentifier).setObjectClass(object.getClass())
		.setExpectedResponseStatusCode(Response.Status.OK.getStatusCode())
		.setExpectedResponseEntityClass(object.getClass())
		.execute();
	}
	
	@Test
	public void readOneByBusinessIdentifier_notFound() throws Exception{
		Object object = __instanciateEntity__(null);
		Object businessIdentifier = __getFieldValueBusinessIdentifier__(object).toString()+"a";
		__inject__(TestRepresentationRead.class).addObjectsToBeCreatedArray(object).addObjectIdentifiers(businessIdentifier).addUnexistingObjectIdentifiers(businessIdentifier).setObjectClass(object.getClass())
		.setExpectedResponseStatusCode(Response.Status.NOT_FOUND.getStatusCode())
		.setExpectedResponseEntityIsNull(Boolean.TRUE)
		.execute();
	}
	
	@Test
	public void readMany() throws Exception{
		Object object1 = __instanciateEntity__(null);
		Object object2 = __instanciateEntity__(null);
		
		__inject__(RepresentationLayer.class).injectInterfaceClassFromEntity(object1).createOne(object1);
		__inject__(RepresentationLayer.class).injectInterfaceClassFromEntity(object2).createOne(object2);
		
		@SuppressWarnings("unchecked")
		Collection<ENTITY> entities = (Collection<ENTITY>) __inject__(RepresentationLayer.class).injectInterfaceClassFromEntity(object1).getMany(Boolean.FALSE,null,null,ConstantNull.STRING,null).getEntity();
		
		assertThat(entities).asList().hasSize(2);
	}
	
	@Test
	public void updateOne() throws Exception{
		Object object = __instanciateEntity__(null);
		String businessIdentifierFieldName = __inject__(FieldNameGetter.class).execute(object.getClass(), FieldName.IDENTIFIER, ValueUsageType.BUSINESS).execute().getOutput();
		Object newBusinessIdentifierFieldValue = __inject__(RandomHelper.class).getAlphanumeric(5);
		
		__inject__(TestRepresentationUpdate.class).setFieldValuesMap(object, __inject__(MapHelper.class).instanciateKeyAsStringValueAsObject(businessIdentifierFieldName
				,newBusinessIdentifierFieldValue)).addObjectsToBeCreatedArray(object).addNotGarbagableArray(object).addObjects(object).setObjectClass(object.getClass())
			.addObjectBusinessIdentifiersToBeDeletedOnCleanArray(object.getClass(), newBusinessIdentifierFieldValue)
		.setExpectedResponseStatusCode(Response.Status.OK.getStatusCode())
		.setExpectedResponseEntityClass(ResponseEntityDto.class)
			.addExpectedResponseEntityDtoMessages(__inject__(ExpectedMessageDto.class).addHeadExpectedStrings(StringLocation.INSIDE,"a été Update avec succès.")).execute();
	}
	
	@Test
	public void deleteOne() throws Exception{
		Object object = __instanciateEntity__(null);
		//Object identifier = __getFieldValueSystemIdentifier__(object);
		__inject__(TestRepresentationDelete.class).addObjectsToBeCreatedArray(object).addNotGarbagableArray(object).addObjects(object)
		//.setIdentifierValueUsageType(ValueUsageType.SYSTEM)
		.setObjectClass(object.getClass())
		.setExpectedResponseStatusCode(Response.Status.OK.getStatusCode())
		.setExpectedResponseEntityClass(ResponseEntityDto.class)
		.setIsCatchThrowable(Boolean.FALSE)
		.execute();
	}
	
	/**/
		
	@SuppressWarnings("unchecked")
	protected Class<ENTITY> __getEntityClass__(Object action){
		return (Class<ENTITY>) ClassHelper.getParameterAt(getClass(), 0);
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
	
	protected void __setEntityFields__(ENTITY entity,Object action){}

	/**/
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends RepresentationEntity> __getLayerEntityInterfaceClass__() {
		return __inject__(RepresentationLayer.class).getInterfaceClassFromEntityClass(ClassHelper.getParameterAt(getClass(), 0));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> Class<? extends AbstractEntityCollection<T>> __getEntityCollectionClass__(Class<T> aClass) {
		aClass = (Class<T>) ClassHelper.getParameterAt(getClass(), 0);
		try {
			return (Class<? extends AbstractEntityCollection<T>>) Class.forName(aClass.getName()+"Collection");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}	
}
