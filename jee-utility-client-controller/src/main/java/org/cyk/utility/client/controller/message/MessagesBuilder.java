package org.cyk.utility.client.controller.message;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.notification.Notification;
import org.cyk.utility.notification.Notifications;

public interface MessagesBuilder extends FunctionWithPropertiesAsInput<Collection<Object>> {

	Notifications getNotifications();
	Notifications getNotifications(Boolean injectIfNull);
	MessagesBuilder setNotifications(Notifications notifications);
	MessagesBuilder addNotifications(Collection<Notification> notifications);
	MessagesBuilder addNotifications(Notification...notifications);
	
}
