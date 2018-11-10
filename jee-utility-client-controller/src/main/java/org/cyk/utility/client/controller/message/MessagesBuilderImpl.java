package org.cyk.utility.client.controller.message;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.notification.Notification;
import org.cyk.utility.notification.Notifications;

public class MessagesBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Collection<Object>> implements MessagesBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Notifications notifications;
	
	@Override
	public Notifications getNotifications() {
		return notifications;
	}

	@Override
	public Notifications getNotifications(Boolean injectIfNull) {
		return (Notifications) __getInjectIfNull__(FIELD_NOTIFICATIONS, injectIfNull);
	}
	
	@Override
	public MessagesBuilder setNotifications(Notifications notifications) {
		this.notifications = notifications;
		return this;
	}
	
	@Override
	public MessagesBuilder addNotifications(Collection<Notification> notifications) {
		getNotifications(Boolean.TRUE).add(notifications);
		return this;
	}
	
	@Override
	public MessagesBuilder addNotifications(Notification... notifications) {
		getNotifications(Boolean.TRUE).add(notifications);
		return this;
	}

	/**/
	
	public static final String FIELD_NOTIFICATIONS = "notifications";
	
}
