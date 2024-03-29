package org.cyk.utility.client.controller.web;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.controller.EntityReader;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.persistence.query.Querier;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueConverter;
import org.cyk.utility.client.controller.ControllerEntity;
import org.cyk.utility.client.controller.ControllerLayer;

@ApplicationScoped
public class WebController extends AbstractObject implements Serializable {

	/**/
	
	public String getRequestParameter(String name) {
		if(StringHelper.isBlank(name))
			return null;
		HttpServletRequest request = __inject__(HttpServletRequest.class);
		return request.getParameter(name);
	}
	
	public String getRequestParameter(ParameterName name) {
		if(name == null)
			return null;
		return getRequestParameter(name.getValue());
	}
	
	public Boolean isPageRenderedAsDialog() {
		return REQUEST_PARAMETER_VALUE_WINDOW_RENDER_TYPE_DIALOG.equalsIgnoreCase(getRequestParameter(REQUEST_PARAMETER_NAME_WINDOW_RENDER_TYPE));
	}
	
	public Action getRequestParameterAction() {
		String value = getRequestParameter(ParameterName.ACTION_IDENTIFIER);
		if(StringHelper.isBlank(value))
			return null;
		return Action.getByNameCaseInsensitive(value);
	}
	
	public Boolean getRequestParameterIsStatic() {
		return getRequestParameterAsBoolean(ParameterName.IS_STATIC);
	}
	
	public <T> T getUsingRequestParameterAsSystemIdentifierByQueryIdentifierByParameterName(Class<T> entityClass,String queryIdentifier,String parameterName) {
		if(entityClass == null || StringHelper.isBlank(queryIdentifier) || StringHelper.isBlank(parameterName))
			return null;
		String identifier = getRequestParameter(parameterName);
		if(StringHelper.isBlank(identifier))
			return null;
		T instance = EntityReader.getInstance().readOne(entityClass, queryIdentifier, Querier.PARAMETER_NAME_IDENTIFIER,identifier);
		return instance;
	}
	
	public <T> T getUsingRequestParameterAsSystemIdentifierByQueryIdentifier(Class<T> entityClass,String queryIdentifier) {
		return getUsingRequestParameterAsSystemIdentifierByQueryIdentifierByParameterName(entityClass, queryIdentifier, ParameterName.ENTITY_IDENTIFIER.getValue());
	}
	
	public <T> T getUsingRequestParameterParentAsSystemIdentifierByQueryIdentifier(Class<T> entityClass,String queryIdentifier) {
		return getUsingRequestParameterAsSystemIdentifierByQueryIdentifierByParameterName(entityClass, queryIdentifier, ParameterName.stringify(entityClass));
	}
	
	public <T> T getRequestParameterEntityBySystemIdentifier(Class<T> entityClass,String parameterName,Properties properties) {
		if(entityClass == null || StringHelper.isBlank(parameterName))
			return null;
		String identifier = getRequestParameter(parameterName);
		if(StringHelper.isBlank(identifier))
			return null;
		ControllerEntity<T> controllerEntity = __inject__(ControllerLayer.class).injectInterfaceClassFromEntityClass(entityClass);
		if(controllerEntity == null)
			return null;
		return controllerEntity.readBySystemIdentifier(identifier,properties);
	}
	
	public <T> T getRequestParameterEntityBySystemIdentifier(Class<T> entityClass,String parameterName) {
		return getRequestParameterEntityBySystemIdentifier(entityClass, parameterName, null);
	}
	
	public <T> T getRequestParameterEntityBySystemIdentifier(Class<T> entityClass) {
		return getRequestParameterEntityBySystemIdentifier(entityClass,ParameterName.ENTITY_IDENTIFIER.getValue());
	}

	public <T> T getRequestParameterEntityBySystemIdentifier(Class<T> klass,Collection<T> collection,T instance) {
		if(instance != null)
			return instance;
		String identifier = WebController.getInstance().getRequestParameter(ParameterName.stringify(klass));
		if(StringHelper.isBlank(identifier))
			instance = CollectionHelper.getFirst(collection);
		else {
			if(CollectionHelper.isEmpty(collection))
				instance = __inject__(ControllerLayer.class).injectInterfaceClassFromEntityClass(klass).readBySystemIdentifier(identifier);
			else
				for(T index : collection)
					if(identifier.equals(FieldHelper.readSystemIdentifier(index)))
						return index;
		}
		return instance;
	}
	
	public <T> T getRequestParameterEntity(Class<T> entityClass,String parameterName) {
		return getRequestParameterEntityBySystemIdentifier(entityClass,parameterName);
	}
	
	public <T> T getRequestParameterEntity(Class<T> entityClass) {
		return getRequestParameterEntityBySystemIdentifier(entityClass);
	}
	
	public <T> T getRequestParameterEntityAsParentBySystemIdentifier(Class<T> entityClass) {
		return getRequestParameterEntityBySystemIdentifier(entityClass,ParameterName.stringify(entityClass));
	}
	
	public <T> T getRequestParameterEntityAsParentBySystemIdentifier(Class<T> entityClass,Collection<T> collection) {
		String identifier = getRequestParameter(ParameterName.stringify(entityClass));
		if(CollectionHelper.isEmpty(collection)) {
			return getRequestParameterEntityAsParentBySystemIdentifier(entityClass);
		}else {
			if(StringHelper.isNotBlank(identifier))
				for(T index : collection) {
					if(identifier.equals(FieldHelper.readSystemIdentifier(index)))
						return index;
				}
		}
		return null;
	}
	
	public <T> T getRequestParameterEntityAsParent(Class<T> entityClass) {
		return getRequestParameterEntityAsParentBySystemIdentifier(entityClass);
	}
	
	/**/
	
	public Boolean getRequestParameterAsBoolean(ParameterName name) {
		return ValueConverter.getInstance().convertToBoolean(getRequestParameter(name));
	}
	
	public Boolean getRequestParameterAsBoolean(String name) {
		return ValueConverter.getInstance().convertToBoolean(getRequestParameter(name));
	}
	
	/**/
	
	public Action computeActionFromRequestParameter() {
		Action action = getRequestParameterAction();
		if(action == null) {			
			String identifier = WebController.getInstance().getRequestParameter(ParameterName.ENTITY_IDENTIFIER.getValue());
			if(StringHelper.isBlank(identifier)) {
				
			}else {
				action = Action.READ;
			}			
		}
		return action;
	}
	
	public String getRequestedURI(HttpServletRequest httpServletRequest) {
		StringBuilder stringBuilder = new StringBuilder(httpServletRequest.getRequestURL().toString());
		if(StringHelper.isNotBlank(httpServletRequest.getQueryString()))
			stringBuilder.append("?"+httpServletRequest.getQueryString());
		return stringBuilder.toString();
	}
	
	public String getRequestedURI() {
		return getRequestedURI(DependencyInjection.inject(HttpServletRequest.class));
	}
	
	/**/
	
	public static WebController getInstance() {
		return DependencyInjection.inject(WebController.class);
	}
	
	public static final String REQUEST_PARAMETER_NAME_WINDOW_RENDER_TYPE = "windowrendertype";	
	public static final String REQUEST_PARAMETER_VALUE_WINDOW_RENDER_TYPE_DIALOG = "windowrendertypedialog";
}