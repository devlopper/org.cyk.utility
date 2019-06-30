package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.annotation.JavaScriptObjectNotation;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.object.ObjectToStringBuilder;
import org.cyk.utility.object.Objects;
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
	protected Response __actWithRepresentationInstanceOfRepresentationEntity__(SystemAction action,@SuppressWarnings("rawtypes") RepresentationEntity representation, Collection<?> dataTransferObjects) {
		Response response;
		Objects identifiers = action.getEntitiesIdentifiers();
		Properties properties = getProperties();
		String fields = (String) Properties.getFromPath(properties, Properties.FIELDS);
		if(__injectCollectionHelper__().isEmpty(identifiers)) {
			Boolean isPageable = __inject__(BooleanHelper.class).get(Properties.getFromPath(properties,Properties.IS_PAGEABLE));
			Long from = __injectNumberHelper__().getLong(Properties.getFromPath(properties,Properties.FROM));
			Long count = __injectNumberHelper__().getLong(Properties.getFromPath(properties,Properties.COUNT));
			Object filters = Properties.getFromPath(properties,Properties.FILTERS);
			response = representation.getMany(isPageable,from,count,fields,filters instanceof String? (String)filters 
					: __injectByQualifiersClasses__(ObjectToStringBuilder.class,JavaScriptObjectNotation.Class.class).setObject(filters).execute().getOutput());
		}else {
			Object identifier = identifiers.getFirst();
			ValueUsageType valueUsageType = getEntityIdentifierValueUsageType();
			if(valueUsageType == null)
				valueUsageType = ValueUsageType.SYSTEM;
			response = representation.getOne(identifier.toString(),valueUsageType.name(),fields);
		}
		return response;
	}
	
	@Override
	protected Object getResponseEntityDto(SystemAction action, Object representation, Response response) {
		Object object;
		Class<?> dtoClass = __inject__(ControllerLayer.class).getDataTransferClassFromEntityClass(action.getEntityClass());
		Objects identifiers = action.getEntitiesIdentifiers();
		Properties properties = new Properties();
		properties.copyFrom(getProperties(), Properties.CONTEXT,Properties.REQUEST);
		if(__injectCollectionHelper__().isEmpty(identifiers)) {
			Collection<?> dtos = (Collection<?>) response.readEntity(__inject__(TypeHelper.class)
					.instanciateGenericCollectionParameterizedTypeForJaxrs(Collection.class,dtoClass));
			object = new ArrayList<Object>();
			for(Object index : dtos)
				((Collection<Object>)object).add(__inject__(InstanceHelper.class).buildOne(__inject__(action.getEntityClass()).getClass(), index,properties));
			setEntities((Collection<?>) object);
		}else {
			object = response.readEntity(dtoClass);
			object = __injectInstanceHelper__().buildOne(__inject__(action.getEntityClass()).getClass(), object,properties);
			setEntity(object);	
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
