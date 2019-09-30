package org.cyk.utility.client.controller.message;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;
import org.cyk.utility.notification.Notification;
import org.cyk.utility.notification.NotificationBuilder;
import org.cyk.utility.notification.NotificationBuilders;
import org.cyk.utility.notification.Notifications;
import org.cyk.utility.__kernel__.object.Objects;

public interface MessageRender extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	Notifications getNotifications();
	Notifications getNotifications(Boolean injectIfNull);
	MessageRender setNotifications(Notifications notifications);
	MessageRender addNotifications(Collection<Notification> notifications);
	MessageRender addNotifications(Notification...notifications);
	
	NotificationBuilders getNotificationBuilders();
	NotificationBuilders getNotificationBuilders(Boolean injectIfNull);
	MessageRender setNotificationBuilders(NotificationBuilders notificationBuilders);
	MessageRender addNotificationBuilders(Collection<NotificationBuilder> notificationBuilders);
	MessageRender addNotificationBuilders(NotificationBuilder...notificationBuilders);
	
	MessageRenderTypes getTypes();
	MessageRenderTypes getTypes(Boolean injectIfNull);
	MessageRender setTypes(MessageRenderTypes types);
	MessageRender addTypes(Collection<MessageRenderType> types);
	MessageRender addTypes(MessageRenderType...types);
	MessageRender setType(MessageRenderType type);
	
	Objects getMessages();
	Objects getMessages(Boolean injectIfNull);
	MessageRender setMessages(Objects messages);
	MessageRender addMessages(Collection<Object> messages);
	MessageRender addMessages(Object...messages);
}
