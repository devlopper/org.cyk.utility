package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.object.Objects;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.type.TypeHelper;
import org.cyk.utility.value.ValueUsageType;

public class ControllerFunctionReaderImpl extends AbstractControllerFunctionImpl implements ControllerFunctionReader , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionRead.class));
	}
	/*
	@SuppressWarnings("rawtypes")
	@Override
	protected void __execute__(SystemAction action) {
		Class<?> dataRepresentationClass = __inject__(DataRepresentationClassGetter.class).setDataClass(action.getEntityClass()).execute().getOutput();
		RepresentationEntity representation = (RepresentationEntity) __inject__(ProxyGetter.class).setClazz(dataRepresentationClass).execute().getOutput();	
		representation.getMany();
		
		Class<?> dataTransferClass = __inject__(DataTransferObjectClassGetter.class).setDataClass(action.getEntityClass()).execute().getOutput();
		Collection<?> dataTransferObjects = __injectInstanceHelper__().buildMany(dataTransferClass, action.getEntities().get());
	}
	*/
	@Override
	protected Response __actWithRepresentationInstanceOfRepresentationEntity__(SystemAction action,@SuppressWarnings("rawtypes") RepresentationEntity representation, Collection<?> dataTransferObjects) {
		Response response;
		Objects identifiers = action.getEntitiesIdentifiers();
		if(__injectCollectionHelper__().isEmpty(identifiers)) {
			response = representation.getMany();
		}else {
			Object identifier = identifiers.getFirst();
			ValueUsageType valueUsageType = getEntityIdentifierValueUsageType();
			if(valueUsageType == null)
				valueUsageType = ValueUsageType.SYSTEM;
			response = representation.getOne(identifier.toString(),valueUsageType.name());
		}
		return response;
	}
	
	@Override
	protected Object getResponseEntityDto(SystemAction action, Object representation, Response response) {
		Object object;
		Class<?> dtoClass = __inject__(ControllerLayer.class).getDataTransferClassFromEntityClass(action.getEntityClass());
		Objects identifiers = action.getEntitiesIdentifiers();
		if(__injectCollectionHelper__().isEmpty(identifiers)) {
			Collection<?> dtos = (Collection<?>) response.readEntity(__inject__(TypeHelper.class)
					.instanciateGenericCollectionParameterizedTypeForJaxrs(Collection.class,dtoClass));
			object = new ArrayList<Object>();
			for(Object index : dtos)
				((Collection<Object>)object).add(__inject__(InstanceHelper.class).buildOne(__inject__(action.getEntityClass()).getClass(), index));
			setEntities((Collection<?>) object);
		}else {
			object = response.readEntity(dtoClass);
			object = __injectInstanceHelper__().buildOne(__inject__(action.getEntityClass()).getClass(), object);
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

}
