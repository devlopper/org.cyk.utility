package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.server.business.BusinessEntity;
import org.cyk.utility.server.business.BusinessLayer;
import org.cyk.utility.value.ValueUsageType;

import lombok.Getter;

public abstract class AbstractRepresentationEntityImpl<PERSISTENCE_ENTITY,BUSINESS extends BusinessEntity<PERSISTENCE_ENTITY>,ENTITY extends AbstractEntity<PERSISTENCE_ENTITY>> extends AbstractRepresentationServiceProviderImpl<PERSISTENCE_ENTITY,ENTITY> implements RepresentationEntity<PERSISTENCE_ENTITY,ENTITY>,Serializable {
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
	
	//@Override
	public ENTITY instantiate(PERSISTENCE_ENTITY persistenceEntity) {
		return __injectClassHelper__().instanciate(getEntityClass(),new Object[] {getPersistenceEntityClass(),persistenceEntity});
	}
	
	//@Override
	public Collection<ENTITY> instantiate(Collection<PERSISTENCE_ENTITY> persistenceEntities) {
		Collection<ENTITY> entities = new ArrayList<>();
		for(PERSISTENCE_ENTITY index : persistenceEntities)
			entities.add(instantiate(index));
		return entities;
	}
	
	//@Override
	public PERSISTENCE_ENTITY instantiatePersistenceEntity(ENTITY entity) {
		return entity.getPersistenceEntity();
	}
	
	//@Override
	public Collection<PERSISTENCE_ENTITY> instantiatePersistenceEntity(Collection<ENTITY> entities) {
		Collection<PERSISTENCE_ENTITY> persistenceEntites = new ArrayList<>();
		for(ENTITY index : entities)
			persistenceEntites.add(instantiatePersistenceEntity(index));
		return persistenceEntites;
	}
	
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
	
	@Override
	public Response createOne(ENTITY dto) {
		return __inject__(RepresentationFunctionCreator.class).setEntity(dto).execute().getResponse();
	}
	
	@Override
	public Response createMany(Collection<ENTITY> dtos) {
		Collection<PERSISTENCE_ENTITY> persistenceEntites = new ArrayList<>();
		for(ENTITY index : dtos)
			persistenceEntites.add(index.getPersistenceEntity());
		getBusiness().createMany(persistenceEntites);
		return Response.ok().status(Response.Status.CREATED).build();
	}
	
	@Override
	public Collection<ENTITY> getMany() {
		Collection<ENTITY> dtos = new ArrayList<>();
		//TODO get query parameters to build properties
		for(PERSISTENCE_ENTITY index : getBusiness().findMany(/* properties */)) {
			ENTITY dto = instantiate(index);
			dtos.add(/*new MyEntityDto(index)*/dto);
		}
		return dtos;
	}
	
	protected ValueUsageType __getValueUsageType__(String string) {
		ValueUsageType valueUsageType = __injectStringHelper__().isBlank(string) || ValueUsageType.SYSTEM.name().equalsIgnoreCase(string) ? ValueUsageType.SYSTEM 
				: (ValueUsageType.BUSINESS.name().equalsIgnoreCase(string) ? ValueUsageType.BUSINESS : null);
		return valueUsageType;
	}
	
	@Override
	public Response getOne(String identifier,String type) {
		ValueUsageType valueUsageType = __getValueUsageType__(type);
		ENTITY entity = null;
		if(ValueUsageType.SYSTEM.equals(valueUsageType))
			entity =  instantiate(getBusiness().findOne(__injectNumberHelper__().getLong(identifier)));
		else if(ValueUsageType.BUSINESS.equals(valueUsageType))
			entity = instantiate(getBusiness().findOneByBusinessIdentifier(identifier));
		return Response.ok().status(Response.Status.OK).entity(new GenericEntity<ENTITY>(entity, getEntityClass())).build();
	}
	
	@Override
	public Response updateOne(ENTITY entity) {
		getBusiness().update(getPersistenceEntityByIdentifier(entity));
		return Response.ok().status(Response.Status.OK).build();
	}
	
	@Override
	public Response updateMany(Collection<ENTITY> entities) {
		getBusiness().updateMany(getPersistenceEntityByIdentifier(entities));
		return Response.ok().status(Response.Status.OK).build();
	}
	
	@Override
	public Response deleteOne(String identifier,String type) {
		ValueUsageType valueUsageType = __getValueUsageType__(type);
		if(ValueUsageType.SYSTEM.equals(valueUsageType))
			getBusiness().deleteBySystemIdentifier(__injectNumberHelper__().getLong(identifier));
		else if(ValueUsageType.BUSINESS.equals(valueUsageType))
			getBusiness().deleteByBusinessIdentifier(identifier);
		return Response.ok().status(Response.Status.OK).build();
	}
	
	@Override
	public Response deleteMany() {
		//TODO get query parameters to build properties
		
		return Response.ok().status(Response.Status.OK).build();
	}

	@Override
	public Response deleteAll() {
		getBusiness().deleteAll();
		return Response.ok().status(Response.Status.OK).build();
	}

	@Override
	public Response count() {
		//TODO get query parameters to build properties
		Long count = getBusiness().count(/* properties */);
		return Response.ok().status(Response.Status.OK).entity(count).build();
	}

	/**/
	
}
