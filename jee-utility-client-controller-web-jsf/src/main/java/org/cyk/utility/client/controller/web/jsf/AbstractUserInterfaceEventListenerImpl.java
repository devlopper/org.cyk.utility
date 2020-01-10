package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import org.cyk.utility.__kernel__.user.interface_.UserInterfaceEvent;
import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.message.MessageRenderTypeDialog;
import org.cyk.utility.notification.NotificationBuilder;
import org.cyk.utility.notification.NotificationSeverityError;

public abstract class AbstractUserInterfaceEventListenerImpl extends org.cyk.utility.__kernel__.user.interface_.AbstractUserInterfaceEventListenerImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void listen(UserInterfaceEvent event) {
		MessageRender messageRender = __inject__(MessageRender.class);
		messageRender.addNotificationBuilders(__inject__(NotificationBuilder.class)
				.setSummary("Event : "+event)
				.setSeverity(__inject__(NotificationSeverityError.class))
				);
		messageRender.addTypes(__inject__(MessageRenderTypeDialog.class));
		messageRender.execute();
	}
	
}
