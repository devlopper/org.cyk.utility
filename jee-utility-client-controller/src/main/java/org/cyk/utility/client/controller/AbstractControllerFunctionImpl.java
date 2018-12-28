package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.message.MessageRenderTypeDialog;
import org.cyk.utility.notification.NotificationBuilder;
import org.cyk.utility.system.AbstractSystemFunctionClientImpl;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerController;

public abstract class AbstractControllerFunctionImpl extends AbstractSystemFunctionClientImpl implements ControllerFunction,Serializable {
	private static final long serialVersionUID = 1L;

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
	protected void __notifyOnSuccess__() {
		__inject__(MessageRender.class).addNotificationBuilders(__inject__(NotificationBuilder.class)
				.setSummaryInternalizationStringKeyValue("operation.execution.success.summary")
				.setDetailsInternalizationStringKeyValue("operation.execution.success.details")
				).setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
	
	@Override
	protected void __notifyOnThrowable__(Throwable throwable) {
		__inject__(MessageRender.class).addNotificationBuilders(__inject__(NotificationBuilder.class).setThrowable(throwable))
			.setType(__inject__(MessageRenderTypeDialog.class)).execute();
	}
}
