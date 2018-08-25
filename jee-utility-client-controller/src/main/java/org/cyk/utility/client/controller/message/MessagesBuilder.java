package org.cyk.utility.client.controller.message;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.notification.Notification;

public interface MessagesBuilder extends FunctionWithPropertiesAsInput<Collection<Object>> {

	Collection<Notification> getNotifications();
	MessagesBuilder setNotifications(Collection<Notification> notificationss);
	
}
