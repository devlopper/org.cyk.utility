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

public abstract class AbstractRepresentationEntityImpl<ENTITY,BUSINESS extends BusinessEntity<ENTITY>,DTO extends AbstractEntity<ENTITY>> extends AbstractRepresentationServiceProviderImpl<ENTITY,DTO> implements RepresentationEntity<ENTITY,DTO>,Serializable {
	private static final long serialVersionUID = 1L;

	@Getter private Class<ENTITY> entityClass;
	@Getter private Class<DTO> representationEntityClass;
	@Getter private BUSINESS business;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		entityClass = (Class<ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 0, Object.class);
		business = (BUSINESS) __inject__(BusinessLayer.class).injectInterfaceClassFromEntityClass(getEntityClass());
		representationEntityClass =  (Class<DTO>) __inject__(ClassHelper.class).getParameterAt(getClass(), 2, AbstractEntity.class);
	}
	
	@Override
	public Response createOne(DTO dto) {
		getBusiness().create(dto.getPersistenceEntity());
		return Response.ok().status(Response.Status.CREATED).build();
	}
	
	@Override
	public Collection<DTO> getMany() {
		Collection<DTO> dtos = new ArrayList<>();
		for(ENTITY index : getBusiness().findMany()) {
			DTO dto = __injectClassHelper__().instanciate(representationEntityClass,new Object[] {entityClass,index});
			dtos.add(/*new MyEntityDto(index)*/dto);
		}
		return dtos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ENTITY findOne(Object identifier, Properties properties) {
		return (ENTITY) __inject__(RepresentationFunctionReader.class).setEntityClass(getEntityClass()).setEntityIdentifier(identifier)
				.setEntityIdentifierValueUsageType(properties == null ? ValueUsageType.SYSTEM: (ValueUsageType) properties.getValueUsageType()).execute().getProperties().getEntity();
	}

	@Override
	public ENTITY findOne(Object identifier, ValueUsageType valueUsageType) {
		return findOne(identifier,new Properties().setValueUsageType(valueUsageType));
	}

	@Override
	public ENTITY findOne(Object identifier) {
		return findOne(identifier,(Properties)null);
	}

	@Override
	public ENTITY findOneByBusinessIdentifier(Object identifier) {
		return findOne(identifier, ValueUsageType.BUSINESS);
	}

	@Override
	public Collection<ENTITY> findMany(Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ENTITY> findMany() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long count(Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**/
	
}
