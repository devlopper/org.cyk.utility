package org.cyk.utility.system;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;
import org.cyk.utility.notification.NotificationBuilders;
import org.cyk.utility.notification.Notifications;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface SystemFunction extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	Boolean getIsActionRequired();
	SystemFunction setIsActionRequired(Boolean isActionRequired);
	
	SystemAction getAction();
	SystemFunction setAction(SystemAction action);
	SystemFunction setActionEntityClass(Class<?> entityClass);
	SystemFunction addActionEntities(Collection<Object> entities);
	SystemFunction addActionEntities(Object...entities);
	
	SystemFunction setActionEntityIdentifierClass(Class<?> entityIdentifierClass);
	SystemFunction addActionEntitiesIdentifiers(Collection<Object> entitiesIdentifiers);
	SystemFunction addActionEntitiesIdentifiers(Object...entitiesIdentifiers);
	
	SystemFunction setEntityClass(Class<?> aClass);
	Class<?> getEntityClass();
	
	Object getEntity();
	SystemFunction setEntity(Object entity);
	
	Strings getEntityFieldNames();
	SystemFunction setEntityFieldNames(Strings entityFieldNames);
	SystemFunction setEntityFieldNames(Collection<String> entityFieldNames);
	SystemFunction setEntityFieldNames(String...entityFieldNames);
	SystemFunction addEntityFieldNames(Collection<String> entityFieldNames);
	SystemFunction addEntityFieldNames(String...entityFieldNames);
	
	SystemFunction setEntityIdentifier(Object identifier);
	Object getEntityIdentifier();
	
	SystemFunction setEntityIdentifierValueUsageType(Object object);
	ValueUsageType getEntityIdentifierValueUsageType();
	
	Collection<?> getEntities();
	SystemFunction setEntities(Collection<?> entities);
	
	Long getEntitiesCount();
	SystemFunction setEntitiesCount(Long entitiesCount);
	
	SystemFunction execute();
	
	NotificationBuilders getNotificationBuilders();
	NotificationBuilders getNotificationBuilders(Boolean injectIfNull);
	SystemFunction setNotificationBuilders(NotificationBuilders notificationBuilders);
	
	Notifications getNotifications();
	Notifications getNotifications(Boolean injectIfNull);
	SystemFunction setNotifications(Notifications notifications);
	
}
