package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.server.business.BusinessEntity;
import org.cyk.utility.server.business.BusinessLayer;
import org.cyk.utility.value.ValueUsageType;

import lombok.Getter;

public abstract class AbstractRepresentationEntityImpl<PERSISTENCE_ENTITY,BUSINESS extends BusinessEntity<PERSISTENCE_ENTITY>,ENTITY extends AbstractEntity,ENTITY_COLLECTION> extends AbstractRepresentationServiceProviderImpl<PERSISTENCE_ENTITY,ENTITY> implements RepresentationEntity<PERSISTENCE_ENTITY,ENTITY,ENTITY_COLLECTION>,Serializable {
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
		entityClass =  (Class<ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 2, AbstractEntity.class);
	}
	
	//TODO think about getPersistenceEntityByIdentifier to be make as function
	
	//@Override
	public PERSISTENCE_ENTITY getPersistenceEntityByIdentifier(ENTITY entity) {
		PERSISTENCE_ENTITY persistenceEntity = getBusiness().findOne(__injectNumberHelper__().getLong(entity.getIdentifier()));
		
		return persistenceEntity;
	}
	
	//@Override
	public Collection<PERSISTENCE_ENTITY> getPersistenceEntityByIdentifier(Collection<ENTITY> entities) {
		Collection<PERSISTENCE_ENTITY> persistenceEntites = new ArrayList<>();
		for(ENTITY index : entities)
			persistenceEntites.add(getPersistenceEntityByIdentifier(index));
		return persistenceEntites;
	}
	
	/* */
	
	@Override
	public Response createOne(ENTITY entity) {
		return __inject__(RepresentationFunctionCreator.class).setEntity(entity).setPersistenceEntityClass(getPersistenceEntityClass()).execute().getResponse();
	}
	
	//TODO use representation function to get the response to the desired request
	@Override
	public Response createMany(Collection<ENTITY> entities) {
		return __inject__(RepresentationFunctionCreator.class).setEntities(entities).setPersistenceEntityClass(getPersistenceEntityClass()).execute().getResponse();
		
		//getBusiness().createMany(__injectInstanceHelper__().buildMany(getPersistenceEntityClass(),entities));
		//return Response.status(Response.Status.CREATED).build();
	}
	
	@Override
	public Response createMany(ENTITY_COLLECTION entityCollection) {
		return __inject__(RepresentationFunctionCreator.class).setEntities(__getEntities__(entityCollection)).setPersistenceEntityClass(getPersistenceEntityClass()).execute().getResponse();
	}
	
	//TODO use representation function to get the response to the desired request
	@Override
	public Response getMany() {
		List<ENTITY> entities = (List<ENTITY>) __injectInstanceHelper__().buildMany(getEntityClass(),getBusiness().findMany(/* properties */));
		if(entities == null)
			entities = new ArrayList<>();
		GenericEntity<List<ENTITY>> genericEntity = new GenericEntity<List<ENTITY>>(entities,getCollectionType(List.class, getEntityClass()));
		return Response.status(Response.Status.OK).entity(genericEntity).build();
	}
	
	protected ValueUsageType __getValueUsageType__(String string) {
		ValueUsageType valueUsageType = __injectStringHelper__().isBlank(string) || ValueUsageType.SYSTEM.name().equalsIgnoreCase(string) ? ValueUsageType.SYSTEM 
				: (ValueUsageType.BUSINESS.name().equalsIgnoreCase(string) ? ValueUsageType.BUSINESS : null);
		return valueUsageType;
	}
	
	//TODO use representation function to get the response to the desired request
	@Override
	public Response getOne(String identifier,String type) {
		ResponseBuilder responseBuilder = null;
		ValueUsageType valueUsageType = __getValueUsageType__(type);
		ENTITY entity = null;
		if(ValueUsageType.SYSTEM.equals(valueUsageType))
			entity =  __injectInstanceHelper__().buildOne(getEntityClass(),getBusiness().findOne(__injectNumberHelper__().getLong(identifier)));
		else if(ValueUsageType.BUSINESS.equals(valueUsageType))
			entity = __injectInstanceHelper__().buildOne(getEntityClass(),getBusiness().findOneByBusinessIdentifier(identifier));
		if(entity == null)
			responseBuilder = Response.status(Response.Status.NO_CONTENT);
		else
			responseBuilder = Response.status(Response.Status.OK).entity(new GenericEntity<ENTITY>(entity, getEntityClass()));
		return responseBuilder.build();
	}
	
	//TODO use representation function to get the response to the desired request
	@Override
	public Response updateOne(ENTITY entity) {
		getBusiness().update(getPersistenceEntityByIdentifier(entity));
		return Response.status(Response.Status.OK).build();
	}
	
	//TODO use representation function to get the response to the desired request
	@Override
	public Response updateMany(Collection<ENTITY> entities) {
		getBusiness().updateMany(getPersistenceEntityByIdentifier(entities));
		return Response.status(Response.Status.OK).build();
	}
	
	//TODO use representation function to get the response to the desired request
	@Override
	public Response deleteOne(String identifier,String type) {
		ValueUsageType valueUsageType = __getValueUsageType__(type);
		if(ValueUsageType.SYSTEM.equals(valueUsageType))
			getBusiness().deleteBySystemIdentifier(__injectNumberHelper__().getLong(identifier));
		else if(ValueUsageType.BUSINESS.equals(valueUsageType))
			getBusiness().deleteByBusinessIdentifier(identifier);
		return Response.status(Response.Status.OK).build();
	}
	
	//TODO use representation function to get the response to the desired request
	@Override
	public Response deleteMany() {
		//TODO get query parameters to build properties
		
		return Response.status(Response.Status.OK).build();
	}

	//TODO use representation function to get the response to the desired request
	@Override
	public Response deleteAll() {
		getBusiness().deleteAll();
		return Response.status(Response.Status.OK).build();
	}

	//TODO use representation function to get the response to the desired request
	@Override
	public Response count() {
		//TODO get query parameters to build properties
		Long count = getBusiness().count(/* properties */);
		return Response.status(Response.Status.OK).entity(count).build();
	}

	/**/
	
	protected <COLLECTION,ELEMENT> Type getCollectionType(Class<COLLECTION> collectionClass,Class<ELEMENT> elementClass) {
		return new ParameterizedType() {
			@Override
			public Type getRawType() {
				return collectionClass;
			}
			
			@Override
			public Type getOwnerType() {
				return collectionClass;
			}
			
			@Override
			public Type[] getActualTypeArguments() {
				return new Type[] { elementClass };
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	protected Collection<ENTITY> __getEntities__(ENTITY_COLLECTION entityCollection) {
		if(entityCollection instanceof AbstractEntityCollection<?>)
			return (Collection<ENTITY>) ((AbstractEntityCollection<?>)entityCollection).getCollection();
		return null;
	}
}
