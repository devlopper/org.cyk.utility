package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.navigation.Navigation;
import org.cyk.utility.client.controller.navigation.NavigationBuilder;
import org.cyk.utility.client.controller.navigation.NavigationRedirector;
import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.object.Objects;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.ResponseHelper;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionRedirect;
import org.cyk.utility.system.exception.EntityNotFoundException;
import org.cyk.utility.type.TypeHelper;
import org.cyk.utility.value.ValueUsageType;

public class ControllerFunctionRedirectorImpl extends AbstractControllerFunctionImpl implements ControllerFunctionRedirector , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionRedirect.class));
	}

	@Override
	protected Response __actWithRepresentationInstanceOfRepresentationEntity__(SystemAction action,@SuppressWarnings("rawtypes") RepresentationEntity representation, Collection<?> dataTransferObjects) {
		Response response = null;
		Properties properties = new Properties().setValueUsageType(ValueUsageType.BUSINESS);	
		Object entity = __inject__(Controller.class).readOne(action.getEntityClass(),action.getEntitiesIdentifiers().getFirst(),properties);
		response = (Response) properties.getResponse();			
		if(Boolean.TRUE.equals(__inject__(ResponseHelper.class).isStatusSuccessfulOk(response))) {
			SystemActionRead systemActionRead = __inject__(SystemActionRead.class);
			systemActionRead.setEntityClass(getEntityClass());
			systemActionRead.getEntitiesIdentifiers(Boolean.TRUE).add(__injectFieldHelper__().getFieldValueSystemIdentifier(entity));
			
			NavigationBuilder navigationBuilder = __inject__(NavigationBuilder.class).setIdentifierBuilderSystemAction(systemActionRead);
			Navigation navigation = navigationBuilder.execute().getOutput();
			response.close();
			__inject__(NavigationRedirector.class).setNavigation(navigation).execute();
			
		}else if(Boolean.TRUE.equals(__inject__(ResponseHelper.class).isFamilyClientError(response))) {
			__injectThrowableHelper__().throw_(__inject__(EntityNotFoundException.class).setSystemAction(action).setResponse(response));
		}				
		return response;
	}
	
	@Override
	protected Boolean __isProcessReponse__() {
		//Response will be close by redirection so no need for further processing
		return Boolean.FALSE;
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
	public ControllerFunctionRedirector setActionEntityIdentifierClass(Class<?> entityIdentifierClass) {
		return (ControllerFunctionRedirector) super.setActionEntityIdentifierClass(entityIdentifierClass);
	}
	
	@Override
	public ControllerFunctionRedirector addActionEntitiesIdentifiers(Collection<Object> entitiesIdentifiers) {
		return (ControllerFunctionRedirector) super.addActionEntitiesIdentifiers(entitiesIdentifiers);
	}
	
	@Override
	public ControllerFunctionRedirector addActionEntitiesIdentifiers(Object... entitiesIdentifiers) {
		return (ControllerFunctionRedirector) super.addActionEntitiesIdentifiers(entitiesIdentifiers);
	}
	
}
