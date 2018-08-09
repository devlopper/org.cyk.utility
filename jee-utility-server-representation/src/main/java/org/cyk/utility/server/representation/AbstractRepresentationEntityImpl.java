package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.server.business.BusinessEntity;
import org.cyk.utility.server.business.BusinessLayer;
import org.cyk.utility.value.ValueUsageType;

import lombok.Getter;

public abstract class AbstractRepresentationEntityImpl<PERSISTENCE_ENTITY,BUSINESS extends BusinessEntity<PERSISTENCE_ENTITY>,ENTITY extends AbstractEntity<PERSISTENCE_ENTITY>> extends AbstractRepresentationServiceProviderImpl<PERSISTENCE_ENTITY,ENTITY> implements RepresentationEntity<PERSISTENCE_ENTITY,ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	@Getter private Class<ENTITY> representationEntityClass;
	@Getter private Class<PERSISTENCE_ENTITY> persistenceEntityClass;
	@Getter private BUSINESS business;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		persistenceEntityClass = (Class<PERSISTENCE_ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 0, Object.class);
		business = (BUSINESS) __inject__(BusinessLayer.class).injectInterfaceClassFromEntityClass(getPersistenceEntityClass());
		representationEntityClass =  (Class<ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 2, AbstractEntity.class);
	}
	
	@Override
	public ENTITY instantiate(PERSISTENCE_ENTITY persistenceEntity) {
		return __injectClassHelper__().instanciate(representationEntityClass,new Object[] {getPersistenceEntityClass(),persistenceEntity});
	}
	
	@Override
	public Collection<ENTITY> instantiate(Collection<PERSISTENCE_ENTITY> persistenceEntities) {
		Collection<ENTITY> entities = new ArrayList<>();
		for(PERSISTENCE_ENTITY index : persistenceEntities)
			entities.add(instantiate(index));
		return entities;
	}
	
	@Override
	public PERSISTENCE_ENTITY instantiatePersistenceEntity(ENTITY entity) {
		return entity.getPersistenceEntity();
	}
	
	@Override
	public Collection<PERSISTENCE_ENTITY> instantiatePersistenceEntity(Collection<ENTITY> entities) {
		Collection<PERSISTENCE_ENTITY> persistenceEntites = new ArrayList<>();
		for(ENTITY index : entities)
			persistenceEntites.add(instantiatePersistenceEntity(index));
		return persistenceEntites;
	}
	
	@Override
	public PERSISTENCE_ENTITY getPersistenceEntityByIdentifier(ENTITY entity) {
		PERSISTENCE_ENTITY persistenceEntity = getBusiness().findOne(__injectNumberHelper__().getLong(entity.getIdentifier()));
		
		return persistenceEntity;
	}
	
	@Override
	public Collection<PERSISTENCE_ENTITY> getPersistenceEntityByIdentifier(Collection<ENTITY> entities) {
		Collection<PERSISTENCE_ENTITY> persistenceEntites = new ArrayList<>();
		for(ENTITY index : entities)
			persistenceEntites.add(getPersistenceEntityByIdentifier(index));
		return persistenceEntites;
	}
	
	@Override
	public Response createOne(ENTITY dto) {
		getBusiness().create(dto.getPersistenceEntity());
		return Response.ok().status(Response.Status.CREATED).build();
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
		for(PERSISTENCE_ENTITY index : getBusiness().findMany()) {
			ENTITY dto = instantiate(index);
			dtos.add(/*new MyEntityDto(index)*/dto);
		}
		return dtos;
	}
	
	@Override
	public ENTITY getOne(Long identifier) {
		return getOneBySystemIdentifier(identifier);
	}
	
	@Override
	public ENTITY getOneBySystemIdentifier(Long identifier) {
		return instantiate(getBusiness().findOne(identifier));
	}
	
	@Override
	public ENTITY getOneByBusinessIdentifier(String identifier) {
		return instantiate(getBusiness().findOneByBusinessIdentifier(identifier));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PERSISTENCE_ENTITY findOne(Object identifier, Properties properties) {
		return (PERSISTENCE_ENTITY) __inject__(RepresentationFunctionReader.class).setEntityClass(getPersistenceEntityClass()).setEntityIdentifier(identifier)
				.setEntityIdentifierValueUsageType(properties == null ? ValueUsageType.SYSTEM: (ValueUsageType) properties.getValueUsageType()).execute().getProperties().getEntity();
	}

	@Override
	public PERSISTENCE_ENTITY findOne(Object identifier, ValueUsageType valueUsageType) {
		return findOne(identifier,new Properties().setValueUsageType(valueUsageType));
	}

	@Override
	public PERSISTENCE_ENTITY findOne(Object identifier) {
		return findOne(identifier,(Properties)null);
	}

	@Override
	public PERSISTENCE_ENTITY findOneByBusinessIdentifier(Object identifier) {
		return findOne(identifier, ValueUsageType.BUSINESS);
	}

	@Override
	public Collection<PERSISTENCE_ENTITY> findMany(Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<PERSISTENCE_ENTITY> findMany() {
		// TODO Auto-generated method stub
		return null;
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
	public Long count(Properties properties) {
		return getBusiness().count(properties);
	}

	@Override
	public Long count() {
		return count(null);
	}
	
	/**/
	
}
