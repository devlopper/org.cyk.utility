package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import org.cyk.utility.__kernel__.user.interface_.UserInterfaceEvent;
import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.message.MessageRenderTypeDialog;
import org.cyk.utility.notification.NotificationBuilder;
import org.cyk.utility.notification.NotificationSeverity;
import org.cyk.utility.notification.NotificationSeverityError;
import org.cyk.utility.notification.NotificationSeverityInformation;
import org.cyk.utility.notification.NotificationSeverityWarning;

public abstract class AbstractUserInterfaceEventListenerImpl extends org.cyk.utility.__kernel__.user.interface_.AbstractUserInterfaceEventListenerImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void listen(UserInterfaceEvent event) {
		if(event == null)
			return;
		String message;
		NotificationSeverity severity;
		switch(event) {
		case IDLE:
			message = "Vous êtes inactif.vous risquez de perdre votre session.";
			severity = __inject__(NotificationSeverityWarning.class);
			break;
		case ACTIVATE:
			message = "Vous êtes de retour.";
			severity = __inject__(NotificationSeverityInformation.class);
			break;
		default:
			message = "Quel évènement ?";
			severity = __inject__(NotificationSeverityError.class);
		}
		MessageRender messageRender = __inject__(MessageRender.class);
		messageRender.addNotificationBuilders(__inject__(NotificationBuilder.class)
				.setSummary("Notification")
				.setDetails(message)
				.setSeverity(severity)
				);
		messageRender.addTypes(__inject__(MessageRenderTypeDialog.class));
		messageRender.execute();
	}
	
}
