package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.data.DataRepresentationClassGetter;
import org.cyk.utility.client.controller.data.DataTransferObjectClassGetter;
import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.message.MessageRenderTypeDialog;
import org.cyk.utility.client.controller.message.MessageRenderTypeInline;
import org.cyk.utility.client.controller.proxy.ProxyGetter;
import org.cyk.utility.internationalization.InternalizationKeyStringType;
import org.cyk.utility.internationalization.InternalizationStringBuilder;
import org.cyk.utility.notification.NotificationBuilder;
import org.cyk.utility.notification.NotificationSeverityInformation;
import org.cyk.utility.notification.NotificationSeverityWarning;
import org.cyk.utility.notification.Notifications;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.ResponseEntityDto;
import org.cyk.utility.system.AbstractSystemFunctionClientImpl;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerController;

public abstract class AbstractControllerFunctionImpl extends AbstractSystemFunctionClientImpl implements ControllerFunction,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(SystemAction action) {
		if(action!=null /*&& (__injectCollectionHelper__().isNotEmpty(action.getEntities()) || __injectCollectionHelper__().isNotEmpty(action.getEntitiesIdentifiers()))*/) {
			Class<?> entityClass = action.getEntityClass(); //action.getEntities().getAt(0).getClass();
			Class<?> dataTransferClass = __inject__(DataTransferObjectClassGetter.class).setDataClass(entityClass).execute().getOutput();
			if(dataTransferClass == null)
				__injectThrowableHelper__().throwRuntimeException("Data Transfer Class is required for "+entityClass);
			Class<?> dataRepresentationClass = __inject__(DataRepresentationClassGetter.class).setDataClass(entityClass).execute().getOutput();
			if(dataRepresentationClass == null)
				__injectThrowableHelper__().throwRuntimeException("Data Representation Class is required for "+entityClass);
			
			if(__injectClassHelper__().isInstanceOf(dataRepresentationClass, RepresentationEntity.class)) {
				__execute__(action, dataTransferClass,dataRepresentationClass,__injectInstanceHelper__().buildMany(dataTransferClass, action.getEntities().get()));
			}else
				__injectThrowableHelper__().throwRuntimeException("Data Representation Class of type "+dataRepresentationClass+" is not an instanceof RepresentationEntity");
		}
	}
	
	protected void __execute__(SystemAction action,Class<?> dataTransferClass,Class<?> dataRepresentationClass,Collection<?> dataTransferObjects) {
		if(__injectClassHelper__().isInstanceOf(dataRepresentationClass, RepresentationEntity.class)) {
			__execute__(action, __inject__(ProxyGetter.class).setClassUniformResourceIdentifierStringRequest(Properties.getFromPath(getProperties(), Properties.REQUEST))
					.setClazz(dataRepresentationClass).execute().getOutput(), dataTransferObjects);
		}
	}
	
	protected void __execute__(SystemAction action,Object representation,Collection<?> dataTransferObjects) {
		if(representation instanceof RepresentationEntity) {
			Response response = __act__(action, representation, dataTransferObjects);
			getProperties().setResponse(response);
			getProperties().setAction(action);
			if(response == null) {
				if(getProperties().getThrowable() == null) {
					getProperties().setThrowable(new RuntimeException("No response for action <<"+action+">>")); 
					//__injectThrowableHelper__().throwRuntimeException(getClass()+" : No response for action <<"+action+">>");
				}
			}else {
				Response.Status.Family responseStatusFamily = Response.Status.Family.familyOf(response.getStatus());
				if(Response.Status.Family.SUCCESSFUL.equals(responseStatusFamily) || Response.Status.Family.SERVER_ERROR.equals(responseStatusFamily)){
					Object responseEntityDto = getResponseEntityDto(action, representation, response);		
					if(Response.Status.Family.SUCCESSFUL.equals(responseStatusFamily))
						;
					else if(responseEntityDto instanceof ResponseEntityDto)
						throw new RuntimeException( ((ResponseEntityDto)responseEntityDto).getMessageCollection().toString());
				}else if(Response.Status.Family.CLIENT_ERROR.equals(responseStatusFamily)){
					/*String summary = null;
					String summaryInternalizationStringKey = __getMessageSummaryInternalizationStringBuilderKey__(action,response);
					if(__injectStringHelper__().isBlank(summaryInternalizationStringKey)) {
						summary = response.readEntity(String.class);
					}else {
						summary = __inject__(InternalizationStringBuilder.class).setKey(summaryInternalizationStringKey)
								.setParameters(__getMessageSummaryInternalizationStringBuilderParameters__(action, response)).execute().getOutput();
					}
					
					__inject__(MessageRender.class).addNotificationBuilders(__inject__(NotificationBuilder.class)
							.setSummary(summary)
							.setDetails(summary)
							.setSeverity(__inject__(NotificationSeverityWarning.class))
							).addTypes(__inject__(MessageRenderTypeDialog.class),__inject__(MessageRenderTypeInline.class))
							.copyProperty(Properties.CONTEXT, getProperties())
							.execute();
					*/
					//throw new RuntimeException(message);
				}else if(Response.Status.Family.INFORMATIONAL.equals(responseStatusFamily) || Response.Status.Family.OTHER.equals(responseStatusFamily)){
					String summary = response.readEntity(String.class);
					__inject__(MessageRender.class).addNotificationBuilders(__inject__(NotificationBuilder.class)
							.setSummary(summary)
							.setDetails(summary)
							.setSeverity(__inject__(NotificationSeverityInformation.class))
							).setType(__inject__(MessageRenderTypeDialog.class)).execute();
				}else if(Response.Status.Family.REDIRECTION.equals(responseStatusFamily)){
					String summary = response.readEntity(String.class);
					__inject__(MessageRender.class).addNotificationBuilders(__inject__(NotificationBuilder.class)
							.setSummary(summary)
							.setDetails(summary)
							.setSeverity(__inject__(NotificationSeverityWarning.class))
							).setType(__inject__(MessageRenderTypeDialog.class)).execute();
				}
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	protected Response __act__(SystemAction action,Object representation,Collection<?> dataTransferObjects) {
		Response response = null;
		if(representation instanceof RepresentationEntity) {
			//try {
				response = __actWithRepresentationInstanceOfRepresentationEntity__(action, (RepresentationEntity) representation, dataTransferObjects);
			/*} catch (ProcessingException exception) {
				System.out.println("AbstractControllerFunctionImpl.__act__() : "+exception.getCause());
				getProperties().setThrowable(exception);
				//response = Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
			}*/
		}else
			__injectThrowableHelper__().throwRuntimeException("Data Representation of type "+representation.getClass()+" is not an instanceof RepresentationEntity");
		return response;
	}
	
	protected abstract Response __actWithRepresentationInstanceOfRepresentationEntity__(SystemAction action,@SuppressWarnings("rawtypes") RepresentationEntity representation,Collection<?> dataTransferObjects);
	
	protected Object getResponseEntityDto(SystemAction action,Object representation,Response response) {
		return response.readEntity(ResponseEntityDto.class);
	}
	
	protected String __getMessageSummaryInternalizationStringBuilderKey__(SystemAction systemAction,Response response) {
		if(Response.Status.NOT_FOUND.getStatusCode() == response.getStatus())
			return "service.of.act.not.found";
		return null;
	}
	
	protected Collection<Object> __getMessageSummaryInternalizationStringBuilderParameters__(SystemAction systemAction,Response response) {
		return __injectCollectionHelper__().instanciate(__inject__(InternalizationStringBuilder.class).setKeyValue(systemAction).setKeyType(InternalizationKeyStringType.NOUN).execute().getOutput() 
				,__inject__(InternalizationStringBuilder.class).setKeyValue(systemAction.getEntityClass()).execute().getOutput());
	}
	
	@Override
	public ControllerFunction setActionEntityClass(Class<?> entityClass) {
		return (ControllerFunction) super.setActionEntityClass(entityClass);
	}
	
	@Override
	public ControllerFunction addActionEntities(Object... entities) {
		return (ControllerFunction) super.addActionEntities(entities);
	}
	
	@Override
	public ControllerFunction setActionEntityIdentifierClass(Class<?> entityIdentifierClass) {
		return (ControllerFunction) super.setActionEntityIdentifierClass(entityIdentifierClass);
	}
	
	@Override
	public ControllerFunction addActionEntitiesIdentifiers(Collection<Object> entitiesIdentifiers) {
		return (ControllerFunction) super.addActionEntitiesIdentifiers(entitiesIdentifiers);
	}
	
	@Override
	public ControllerFunction addActionEntitiesIdentifiers(Object... entitiesIdentifiers) {
		return (ControllerFunction) super.addActionEntitiesIdentifiers(entitiesIdentifiers);
	}
	
	@Override
	protected SystemLayer getSystemLayer() {
		return __inject__(SystemLayerController.class);
	}
	
	@Override
	protected void __notifyOnThrowableIsNull__() {
		getNotificationBuilders(Boolean.TRUE).addAt(__inject__(NotificationBuilder.class)
				.setSummaryInternalizationStringKeyValue("operation.execution.success.summary")
				.setDetailsInternalizationStringKeyValue("operation.execution.success.details"),0);
		
		Notifications notifications = __inject__(Notifications.class);
		if(getProperties().getNotifications() instanceof Notifications)
			notifications.add( ((Notifications) getProperties().getNotifications()).get() );
		
		__inject__(MessageRender.class).addNotificationBuilders().setType(__inject__(MessageRenderTypeDialog.class)).setNotificationBuilders(getNotificationBuilders())
				.setNotifications(notifications).execute();
	}
	
	@Override
	protected void __notifyOnThrowableIsNotNull__(Throwable throwable) {
		throwable = __injectThrowableHelper__().getFirstCause(throwable);
		__inject__(MessageRender.class).addNotificationBuilders(__inject__(NotificationBuilder.class).setThrowable(throwable))
			.setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
}
