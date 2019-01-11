package org.cyk.utility.client.controller.message;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.notification.Notification;
import org.cyk.utility.notification.NotificationBuilder;
import org.cyk.utility.notification.NotificationBuilders;
import org.cyk.utility.notification.Notifications;
import org.cyk.utility.object.Objects;

public class MessageRenderImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements MessageRender,Serializable {
	private static final long serialVersionUID = 1L;

	private Objects messages;
	private Notifications notifications;
	private NotificationBuilders notificationBuilders;
	private MessageRenderTypes types;
	
	@Override
	public Objects getMessages() {
		return messages;
	}

	@Override
	public MessageRender setMessages(Objects messages) {
		this.messages = messages;
		return this;
	}
	
	@Override
	public Objects getMessages(Boolean injectIfNull) {
		return (Objects) __getInjectIfNull__(FIELD_MESSAGES, injectIfNull);
	}
	
	@Override
	public MessageRender addMessages(Collection<Object> messages) {
		getMessages(Boolean.TRUE).add(messages);
		return this;
	}
	
	@Override
	public MessageRender addMessages(Object... messages) {
		getMessages(Boolean.TRUE).add(messages);
		return this;
	}
	
	@Override
	public Notifications getNotifications() {
		return notifications;
	}

	@Override
	public MessageRender setNotifications(Notifications notifications) {
		this.notifications = notifications;
		return this;
	}
	
	@Override
	public Notifications getNotifications(Boolean injectIfNull) {
		return (Notifications) __getInjectIfNull__(FIELD_NOTIFICATIONS, injectIfNull);
	}
	
	@Override
	public MessageRender addNotifications(Collection<Notification> notifications) {
		getNotifications(Boolean.TRUE).add(notifications);
		return this;
	}
	
	@Override
	public MessageRender addNotifications(Notification... notifications) {
		getNotifications(Boolean.TRUE).add(notifications);
		return this;
	}
	
	@Override
	public NotificationBuilders getNotificationBuilders() {
		return notificationBuilders;
	}

	@Override
	public MessageRender setNotificationBuilders(NotificationBuilders notificationBuilders) {
		this.notificationBuilders = notificationBuilders;
		return this;
	}
	
	@Override
	public NotificationBuilders getNotificationBuilders(Boolean injectIfNull) {
		return (NotificationBuilders) __getInjectIfNull__(FIELD_NOTIFICATION_BUILDERS, injectIfNull);
	}
	
	@Override
	public MessageRender addNotificationBuilders(Collection<NotificationBuilder> notificationBuilders) {
		getNotificationBuilders(Boolean.TRUE).add(notificationBuilders);
		return this;
	}
	
	@Override
	public MessageRender addNotificationBuilders(NotificationBuilder... notificationBuilders) {
		getNotificationBuilders(Boolean.TRUE).add(notificationBuilders);
		return this;
	}

	@Override
	public MessageRenderTypes getTypes() {
		return types;
	}
	
	@Override
	public MessageRenderTypes getTypes(Boolean injectIfNull) {
		return (MessageRenderTypes) __getInjectIfNull__(FIELD_TYPES, injectIfNull);
	}
	
	@Override
	public MessageRender setTypes(MessageRenderTypes types) {
		this.types = types;
		return this;
	}
	
	@Override
	public MessageRender addTypes(Collection<MessageRenderType> types) {
		getTypes(Boolean.TRUE).add(types);
		return this;
	}
	
	@Override
	public MessageRender addTypes(MessageRenderType... types) {
		getTypes(Boolean.TRUE).add(types);
		return this;
	}

	@Override
	public MessageRender setType(MessageRenderType type) {
		getTypes(Boolean.TRUE).removeAll().add(type);
		return this;
	}
	
	public static final String FIELD_MESSAGES = "messages";
	public static final String FIELD_NOTIFICATIONS = "notifications";
	public static final String FIELD_NOTIFICATION_BUILDERS = "notificationBuilders";
	public static final String FIELD_TYPES = "types";
}
