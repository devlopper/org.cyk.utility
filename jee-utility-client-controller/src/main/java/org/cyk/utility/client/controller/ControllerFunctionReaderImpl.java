package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.annotation.JavaScriptObjectNotation;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.mapping.MappingHelper;
import org.cyk.utility.object.ObjectToStringBuilder;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.type.BooleanHelper;
import org.cyk.utility.type.TypeHelper;
import org.cyk.utility.value.ValueUsageType;

public class ControllerFunctionReaderImpl extends AbstractControllerFunctionImpl implements ControllerFunctionReader , Serializable {
	private static final long serialVersionUID = 1L;

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
			Boolean isPageable = __inject__(BooleanHelper.class).get(Properties.getFromPath(properties,Properties.IS_PAGEABLE));
			Long from = __injectNumberHelper__().getLong(Properties.getFromPath(properties,Properties.FROM));
			Long count = __injectNumberHelper__().getLong(Properties.getFromPath(properties,Properties.COUNT));
			Object filters = Properties.getFromPath(properties,Properties.FILTERS);
			String filtersAsString = null;
			if(Boolean.TRUE.equals(__injectValueHelper__().isNotBlank(filters))) {
				if(filters instanceof String)
					filtersAsString = (String) filters;
				else
					filtersAsString = __injectByQualifiersClasses__(ObjectToStringBuilder.class,JavaScriptObjectNotation.Class.class).setObject(filters).execute().getOutput();
			}
			if(__representation__ instanceof RepresentationEntity<?, ?, ?>)
				__response__ = ((RepresentationEntity<?,Object,?>)__representation__).getMany(isPageable,from,count,fields,filtersAsString);	
		}else {
			//Object identifier = identifiers.getFirst();
			//ValueUsageType valueUsageType = getEntityIdentifierValueUsageType();
			//if(valueUsageType == null)
			//	valueUsageType = ValueUsageType.SYSTEM;
			if(__injectCollectionHelper__().getSize(__entitiesSystemIdentifiers__) == 1) {
				if(__representation__ instanceof RepresentationEntity<?, ?, ?>)
					__response__ = ((RepresentationEntity<?,Object,?>)__representation__).getOne(__entitiesSystemIdentifiers__.iterator().next().toString(),ValueUsageType.SYSTEM.name(),fields);
			}else if(__injectCollectionHelper__().getSize(__entitiesBusinessIdentifiers__) == 1) {
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
			if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(dtos))) {
				object = new ArrayList<Object>();
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
			if(__injectCollectionHelper__().isEmpty(systemAction.getEntitiesIdentifiers())) {
				return "list.of.not.found";
			}
		}
		return super.__getMessageSummaryInternalizationStringBuilderKey__(systemAction,response);
	}
	*/
}
