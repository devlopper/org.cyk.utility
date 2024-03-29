package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.Objects;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionRead;
import org.cyk.utility.__kernel__.system.action.SystemActionRedirect;
import org.cyk.utility.__kernel__.throwable.EntityNotFoundException;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.server.representation.ResponseHelper;
import org.cyk.utility.type.TypeHelper;

public abstract class AbstractControllerFunctionRedirectorImpl extends AbstractControllerFunctionImpl implements ControllerFunctionRedirector , Serializable {
	private static final long serialVersionUID = 1L;

	private SystemAction targetSystemAction;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionRedirect.class));
	}

	@Override
	protected void __executeRepresentation__() {
		Response response = null;
		Properties properties = new Properties().setValueUsageType(ValueUsageType.SYSTEM);	
		//TODO is it necessary ?
		//Object entity = __inject__(Controller.class).readBySystemIdentifier(__action__.getEntityClass(),__action__.getEntitiesIdentifiers().getFirst(),properties);
		response = (Response) properties.getResponse();			
		if(Boolean.TRUE.equals(ResponseHelper.isStatusSuccessfulOk(response))) {
			/*
			SystemActionRead systemActionRead = __inject__(SystemActionRead.class);
			systemActionRead.setEntityClass(getEntityClass());
			systemActionRead.getEntitiesIdentifiers(Boolean.TRUE).add(FieldHelper.readSystemIdentifier(entity));
			*/
			
			SystemAction targetSystemAction = getTargetSystemAction();
			if(targetSystemAction == null) {
				targetSystemAction = __inject__(SystemActionRead.class);
			}
			
			targetSystemAction.setEntityClass(getEntityClass());
			targetSystemAction.getEntitiesIdentifiers(Boolean.TRUE).add(__action__.getEntitiesIdentifiers().getFirst());
			
			//Object navigationIdentifier = getProperty(Properties.NAVIGATION_IDENTIFIER);
			/*NavigationBuilder navigationBuilder = __inject__(NavigationBuilder.class).setIdentifierBuilderSystemAction(targetSystemAction);
			
			Navigation navigation = navigationBuilder.execute().getOutput();
			response.close();
			__inject__(NavigationRedirector.class).setNavigation(navigation).execute();
			*/
			
		}else if(Boolean.TRUE.equals(ResponseHelper.isFamilyClientError(response))) {
			throw ((RuntimeException)(__inject__(EntityNotFoundException.class).setSystemAction(__action__).setResponse(response)));
		}				
	}
	
	@Override
	protected Boolean __isProcessReponse__() {
		//Response will be closed by redirection so no need for further processing
		return Boolean.FALSE;
	}
	
	@Override
	protected Object getResponseEntityDto(SystemAction action, Object representation, Response response) {
		//TODO tis code should be removed because it is like a duplicated one in Reader
		Object object = null;
		Class<?> dtoClass = __inject__(ControllerLayer.class).getDataTransferClassFromEntityClass(action.getEntityClass());
		Objects identifiers = action.getEntitiesIdentifiers();
		if(CollectionHelper.isEmpty(identifiers)) {
			Collection<?> dtos = (Collection<?>) response.readEntity(__inject__(TypeHelper.class)
					.instanciateGenericCollectionParameterizedTypeForJaxrs(Collection.class,dtoClass));
			object = new ArrayList<Object>();
			((Collection<Object>)object).addAll(MappingHelper.getSources(dtos,__inject__(__entityClass__).getClass()));
			setEntities((Collection<?>) object);
		}else {
			object = response.readEntity(dtoClass);
			object = MappingHelper.getSource(object,__inject__(__entityClass__).getClass());
			setEntity(object);	
		}
		return object;
	}
	
	@Override
	public SystemAction getTargetSystemAction() {
		return targetSystemAction;
	}
	
	@Override
	public ControllerFunctionRedirector setTargetSystemAction(SystemAction targetSystemAction) {
		this.targetSystemAction = targetSystemAction;
		return this;
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
