package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationKeyStringType;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.log.LogLevel;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.throwable.ServiceNotFoundException;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.client.controller.data.DataRepresentationClassGetter;
import org.cyk.utility.client.controller.data.DataTransferObjectClassGetter;
import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.message.MessageRenderTypeDialog;
import org.cyk.utility.client.controller.proxy.ProxyGetter;
import org.cyk.utility.mapping.MappingHelper;
import org.cyk.utility.notification.NotificationBuilder;
import org.cyk.utility.notification.NotificationSeverityInformation;
import org.cyk.utility.notification.NotificationSeverityWarning;
import org.cyk.utility.notification.Notifications;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.ResponseEntityDto;
import org.cyk.utility.server.representation.ResponseHelper;
import org.cyk.utility.system.AbstractSystemFunctionClientImpl;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerController;

public abstract class AbstractControllerFunctionImpl extends AbstractSystemFunctionClientImpl implements ControllerFunction,Serializable {
	private static final long serialVersionUID = 1L;

	public static LogLevel LOG_LEVEL = LogLevel.TRACE;
	
	protected Response __response__;
	protected Class<?> __representationEntityClass__;
	protected Class<?> __representationClass__;
	protected Object __representation__;
	protected Collection<Object> __representationEntities__;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setLogLevel(LOG_LEVEL);
	}
	
	@Override
	protected void __initialiseWorkingVariables__() {
		super.__initialiseWorkingVariables__();
		if(__entityClass__ != null) {
			__representationEntityClass__ = ValueHelper.returnOrThrowIfBlank(String.format("Data Transfer Class of %s", __entityClass__.getName())
					, __inject__(DataTransferObjectClassGetter.class).setDataClass(__entityClass__).execute().getOutput());
			__representationClass__ = ValueHelper.returnOrThrowIfBlank(String.format("Data Representation Class of %s", __entityClass__.getName()),
					__inject__(DataRepresentationClassGetter.class).setDataClass(__entityClass__).execute().getOutput());
			
			__representation__ = ValueHelper.returnOrThrowIfBlank(String.format("Data Representation of %s", __entityClass__.getName())
					,__inject__(ProxyGetter.class).setClassUniformResourceIdentifierStringRequest(Properties.getFromPath(getProperties(), Properties.REQUEST))
					.setClazz(__representationClass__).execute().getOutput())
					;	
		}
	}
	
	@Override
	protected void __execute__(SystemAction action) {
		if(__action__!=null) {
			if(ClassHelper.isInstanceOf(__representationClass__, RepresentationEntity.class)) {
				//__representationEntities__ = (Collection<Object>) __injectInstanceHelper__().buildMany(__representationEntityClass__, __action__.getEntities().get());
				if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(__entities__)))
					__representationEntities__ = (Collection<Object>) __inject__(MappingHelper.class).getDestinations(__entities__, __representationEntityClass__);
				__execute__(__action__, __representationEntityClass__,__representationClass__,__representationEntities__);
			}else
				throw new RuntimeException("Data Representation Class of type "+__representationClass__+" is not an instanceof RepresentationEntity");
		}
	}
	
	protected void __execute__(SystemAction action,Class<?> dataTransferClass,Class<?> dataRepresentationClass,Collection<?> dataTransferObjects) {
		if(ClassHelper.isInstanceOf(dataRepresentationClass, RepresentationEntity.class)) {
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
					//throw new RuntimeException(getClass()+" : No response for action <<"+action+">>");
				}
			}else {
				if(Boolean.TRUE.equals(__isProcessReponse__())) {
					Response.Status.Family responseStatusFamily = Response.Status.Family.familyOf(response.getStatus());
					if(Response.Status.Family.SUCCESSFUL.equals(responseStatusFamily) || Response.Status.Family.SERVER_ERROR.equals(responseStatusFamily)){
						Object responseEntityDto = Boolean.TRUE.equals(response.hasEntity()) ? getResponseEntityDto(action, representation, response) : null;		
						if(Response.Status.Family.SUCCESSFUL.equals(responseStatusFamily))
							;
						else if(responseEntityDto instanceof ResponseEntityDto)
							throw new RuntimeException( ((ResponseEntityDto)responseEntityDto).getMessageCollection().toString());
					}else if(Response.Status.Family.CLIENT_ERROR.equals(responseStatusFamily)){
						System.out.println("AbstractControllerFunctionImpl("+getClass().getSimpleName()+").__execute__() CLIENT ERROR : "+response.getStatusInfo()+" : "+response.readEntity(String.class));
						/*String summary = null;
						String summaryInternalizationStringKey = __getMessageSummaryInternalizationStringBuilderKey__(action,response);
						if(StringHelper.isBlank(summaryInternalizationStringKey)) {
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
	}
	
	protected Boolean __isProcessReponse__() {
		return Boolean.TRUE;
	}
	
	protected Response __act__(SystemAction action,Object representation,Collection<?> dataTransferObjects) {
		if(representation instanceof RepresentationEntity) {
			//try {
				__executeRepresentation__();
				if(Boolean.TRUE.equals(ResponseHelper.isStatusClientErrorNotFound(__response__)))
					__listenExecuteThrowServiceNotFoundException__();
				else
					__listenExecuteServiceFound__();
			/*} catch (ProcessingException exception) {
				System.out.println("AbstractControllerFunctionImpl.__act__() : "+exception.getCause());
				getProperties().setThrowable(exception);
				//response = Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
			}*/
		}else
			throw new RuntimeException("Data Representation of type "+representation.getClass()+" is not an instanceof RepresentationEntity");
		return __response__;
	}
	
	protected abstract void __executeRepresentation__();
	
	protected void __listenExecuteThrowServiceNotFoundException__() {
		System.out.println("AbstractControllerFunctionImpl.__listenExecuteThrowServiceNotFoundException__() : "+__response__.readEntity(String.class)
		+" : "+__response__.getStatusInfo());
		throw ((RuntimeException) (__inject__(ServiceNotFoundException.class).setSystemAction(__action__).setResponse(__response__)));
	}
	
	protected void __listenExecuteServiceFound__() {}
	
	protected Object getResponseEntityDto(SystemAction action,Object representation,Response response) {
		return response.readEntity(ResponseEntityDto.class);
	}
	
	protected String __getMessageSummaryInternalizationStringBuilderKey__(SystemAction systemAction,Response response) {
		if(Response.Status.NOT_FOUND.getStatusCode() == response.getStatus())
			return "service.of.act.not.found";
		return null;
	}
	
	protected Collection<Object> __getMessageSummaryInternalizationStringBuilderParameters__(SystemAction systemAction,Response response) {
		return CollectionHelper.listOf(
				InternationalizationHelper.buildString(InternationalizationHelper.buildKey(systemAction, InternationalizationKeyStringType.NOUN))
				,InternationalizationHelper.buildString(InternationalizationHelper.buildKey(systemAction.getEntityClass()))
				);
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
				.setSummaryInternationalizationStringKeyValue("operation.execution.success.summary")
				.setDetailsInternationalizationStringKeyValue("operation.execution.success.details"),0);
		
		Notifications notifications = __inject__(Notifications.class);
		if(getProperties().getNotifications() instanceof Notifications)
			notifications.add( ((Notifications) getProperties().getNotifications()).get() );
		
		__inject__(MessageRender.class).addNotificationBuilders().setType(__inject__(MessageRenderTypeDialog.class)).setNotificationBuilders(getNotificationBuilders())
				.setNotifications(notifications).execute();
	}
	
	@Override
	protected void __notifyOnThrowableIsNotNull__(Throwable throwable) {
		throwable = ThrowableHelper.getFirstCause(throwable);
		__inject__(MessageRender.class).addNotificationBuilders(__inject__(NotificationBuilder.class).setThrowable(throwable))
			.setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
}
