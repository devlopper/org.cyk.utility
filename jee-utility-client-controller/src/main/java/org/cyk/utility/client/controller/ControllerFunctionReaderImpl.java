package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionRead;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.mapping.MappingHelper;
import org.cyk.utility.server.persistence.query.filter.FilterDto;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.type.BooleanHelper;
import org.cyk.utility.type.TypeHelper;

public class ControllerFunctionReaderImpl extends AbstractControllerFunctionImpl implements ControllerFunctionReader , Serializable {
	private static final long serialVersionUID = 1L;

	public static Boolean IS_PAGEABLE = Boolean.TRUE;
	public static Long FROM = 0l;
	public static Long COUNT = 25l;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionRead.class));
	}

	@Override
	protected void __executeRepresentation__() {
		//Objects identifiers = __action__.getEntitiesIdentifiers();
		Properties properties = getProperties();
		String fields = (String) Properties.getFromPath(properties, Properties.FIELDS);
		if(__isMany__ == null || __isMany__) {
			Boolean isPageable = ValueHelper.defaultToIfNull(BooleanHelper.get(Properties.getFromPath(properties,Properties.IS_PAGEABLE)),IS_PAGEABLE);
			Long from = ValueHelper.defaultToIfNull(NumberHelper.getLong(Properties.getFromPath(properties,Properties.FROM))
					,Boolean.TRUE.equals(isPageable) ? FROM : null);
			Long count = ValueHelper.defaultToIfNull(NumberHelper.getLong(Properties.getFromPath(properties,Properties.COUNT))
					,Boolean.TRUE.equals(isPageable) ? COUNT : null);
			FilterDto filters = (FilterDto) Properties.getFromPath(properties,Properties.FILTERS);
			if(__representation__ instanceof RepresentationEntity<?, ?, ?>)
				__response__ = ((RepresentationEntity<?,Object,?>)__representation__).getMany(isPageable,from,count,fields,filters);	
		}else {
			//Object identifier = identifiers.getFirst();
			//ValueUsageType valueUsageType = getEntityIdentifierValueUsageType();
			//if(valueUsageType == null)
			//	valueUsageType = ValueUsageType.SYSTEM;
			if(CollectionHelper.getSize(__entitiesSystemIdentifiers__) == 1) {
				if(__representation__ instanceof RepresentationEntity<?, ?, ?>)
					__response__ = ((RepresentationEntity<?,Object,?>)__representation__).getOne(__entitiesSystemIdentifiers__.iterator().next().toString(),ValueUsageType.SYSTEM.name(),fields);
			}else if(CollectionHelper.getSize(__entitiesBusinessIdentifiers__) == 1) {
				if(__representation__ instanceof RepresentationEntity<?, ?, ?>)
					__response__ = ((RepresentationEntity<?,Object,?>)__representation__).getOne(__entitiesBusinessIdentifiers__.iterator().next().toString(),ValueUsageType.BUSINESS.name(),fields);
			}else {
				
			}
				
		}
	}
	
	@Override
	protected Object getResponseEntityDto(SystemAction action, Object representation, Response response) {
		Object object = null;
		Class<?> dtoClass = __inject__(ControllerLayer.class).getDataTransferClassFromEntityClass(action.getEntityClass());
		//Objects identifiers = action.getEntitiesIdentifiers();
		Properties properties = new Properties();
		properties.copyFrom(getProperties(), Properties.CONTEXT,Properties.REQUEST);
		if(__isMany__ == null || __isMany__) {
			Collection<?> dtos = (Collection<?>) response.readEntity(__inject__(TypeHelper.class).instanciateGenericCollectionParameterizedTypeForJaxrs(Collection.class,dtoClass));
			if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(dtos))) {
				object = new ArrayList<Object>();
				//((Collection<Object>)object).addAll(__inject__(MappingHelper.class).getSources(dtos,__entityClass__));
				((Collection<Object>)object).addAll(__inject__(MappingHelper.class).getSources(dtos,__inject__(__entityClass__).getClass()));
				setEntities((Collection<?>) object);	
			}
		}else {
			object = response.readEntity(dtoClass);
			if(object != null) {
				object = __inject__(MappingHelper.class).getSource(object,__inject__(__entityClass__).getClass());
				setEntity(object);	
			}
		}
		return object;
	}

	@Override
	public ControllerFunctionReader setActionEntityIdentifierClass(Class<?> entityIdentifierClass) {
		return (ControllerFunctionReader) super.setActionEntityIdentifierClass(entityIdentifierClass);
	}
	
	@Override
	public ControllerFunctionReader addActionEntitiesIdentifiers(Collection<Object> entitiesIdentifiers) {
		return (ControllerFunctionReader) super.addActionEntitiesIdentifiers(entitiesIdentifiers);
	}
	
	@Override
	public ControllerFunctionReader addActionEntitiesIdentifiers(Object... entitiesIdentifiers) {
		return (ControllerFunctionReader) super.addActionEntitiesIdentifiers(entitiesIdentifiers);
	}
	/*
	@Override
	protected String __getMessageSummaryInternalizationStringBuilderKey__(SystemAction systemAction,Response response) {
		if(Response.Status.NOT_FOUND.getStatusCode() == response.getStatus()) {
			if(CollectionHelper.isEmpty(systemAction.getEntitiesIdentifiers())) {
				return "list.of.not.found";
			}
		}
		return super.__getMessageSummaryInternalizationStringBuilderKey__(systemAction,response);
	}
	*/
}
