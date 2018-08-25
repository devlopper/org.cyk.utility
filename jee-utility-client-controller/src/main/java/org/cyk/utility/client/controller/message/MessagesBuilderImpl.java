package org.cyk.utility.client.controller.message;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.notification.Notification;

public class MessagesBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Collection<Object>> implements MessagesBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Notification> getNotifications() {
		return (Collection<Notification>) getProperties().get("notifications");
	}

	@Override
	public MessagesBuilder setNotifications(Collection<Notification> notifications) {
		getProperties().set("notifications", notifications);
		return this;
	}

}
