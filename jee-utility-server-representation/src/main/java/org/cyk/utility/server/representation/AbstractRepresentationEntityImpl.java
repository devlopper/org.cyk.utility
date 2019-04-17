package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.server.business.BusinessEntity;
import org.cyk.utility.server.business.BusinessLayer;

import lombok.Getter;

public abstract class AbstractRepresentationEntityImpl<PERSISTENCE_ENTITY,BUSINESS extends BusinessEntity<PERSISTENCE_ENTITY>,ENTITY extends AbstractEntityFromPersistenceEntity,ENTITY_COLLECTION> extends AbstractRepresentationServiceProviderImpl<PERSISTENCE_ENTITY,ENTITY> implements RepresentationEntity<PERSISTENCE_ENTITY,ENTITY,ENTITY_COLLECTION>,Serializable {
	private static final long serialVersionUID = 1L;

	@Getter private Class<ENTITY> entityClass;
	@Getter private Class<PERSISTENCE_ENTITY> persistenceEntityClass;
	@Getter private BUSINESS business;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		persistenceEntityClass = (Class<PERSISTENCE_ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 0, Object.class);
		business = (BUSINESS) __inject__(BusinessLayer.class).injectInterfaceClassFromPersistenceEntityClass(getPersistenceEntityClass());
		entityClass =  (Class<ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 2, AbstractEntityFromPersistenceEntity.class);
	}
	
	/* */
	
	@Override
	public Response createOne(ENTITY entity) {
		return __inject__(RepresentationFunctionCreator.class).setEntity(entity).setPersistenceEntityClass(getPersistenceEntityClass()).execute().getResponse();
	}
	
	@Override
	public Response createMany(Collection<ENTITY> entities) {
		return __inject__(RepresentationFunctionCreator.class).setEntities(entities).setPersistenceEntityClass(getPersistenceEntityClass()).execute().getResponse();
	}
	
	@Override
	public Response createMany(ENTITY_COLLECTION entityCollection) {
		return createMany(__getEntities__(entityCollection));
	}
	
	@Override
	public Response getMany(Long from,Long count,String fields) {
		return __inject__(RepresentationFunctionReader.class).setEntityClass(getEntityClass()).setPersistenceEntityClass(getPersistenceEntityClass())
				.setEntityFieldNames(__getFieldNames__(fields))
				.setProperty(Properties.QUERY_FIRST_TUPLE_INDEX, from)
				.setProperty(Properties.QUERY_NUMBER_OF_TUPLE, count)
				.execute().getResponse();
	}
	
	@Override
	public Response getOne(String identifier,String type,String fields) {
		return __inject__(RepresentationFunctionReader.class).setEntityClass(getEntityClass()).setEntityIdentifier(identifier).setEntityIdentifierValueUsageType(type)
				.setPersistenceEntityClass(getPersistenceEntityClass()).setEntityFieldNames(__getFieldNames__(fields)).execute().getResponse();
	}
	
	@Override
	public Response updateOne(ENTITY entity,String fields) {
		return __inject__(RepresentationFunctionModifier.class).setPersistenceEntityClass(getPersistenceEntityClass()).setEntity(entity)
				.setEntityFieldNames(__getFieldNames__(fields)).execute().getResponse();
	}
	
	@Override
	public Response updateMany(Collection<ENTITY> entities,String fields) {
		return __inject__(RepresentationFunctionModifier.class).setEntities(entities).execute().getResponse();
	}
	
	/*
	@Override
	public Response deleteOne(String identifier,String type) {
		System.out.println("AbstractRepresentationEntityImpl.deleteOne() DELETEONE "+identifier+" : "+type);
		return __inject__(RepresentationFunctionRemover.class).setEntityIdentifier(identifier).setEntityIdentifierValueUsageType(type)
			.setPersistenceEntityClass(getPersistenceEntityClass()).execute().getResponse();
	}
	*/
	@Override
	public Response deleteOne(ENTITY entity) {
		return __inject__(RepresentationFunctionRemover.class).setEntity(entity)
			.setPersistenceEntityClass(getPersistenceEntityClass()).execute().getResponse();
	}
	
	@Override
	public Response deleteMany() {
		return __inject__(RepresentationFunctionRemover.class).setPersistenceEntityClass(getPersistenceEntityClass()).execute().getResponse();
	}

	@Override
	public Response deleteAll() {
		return __inject__(RepresentationFunctionRemover.class).setPersistenceEntityClass(getPersistenceEntityClass()).execute().getResponse();
	}

	@Override
	public Response count() {
		return __inject__(RepresentationFunctionCounter.class).setPersistenceEntityClass(getPersistenceEntityClass()).execute().getResponse();
	}

	/**/
	
	@SuppressWarnings("unchecked")
	protected Collection<ENTITY> __getEntities__(ENTITY_COLLECTION entityCollection) {
		if(entityCollection instanceof AbstractEntityCollection<?>)
			return (Collection<ENTITY>) ((AbstractEntityCollection<?>)entityCollection).getCollection();
		return null;
	}
	
	/**/
	
	protected static String[] __getFieldNames__(String string) {
		return StringUtils.isBlank(string) ? null : StringUtils.split(string,ConstantCharacter.COMA.toString());
	}
}
