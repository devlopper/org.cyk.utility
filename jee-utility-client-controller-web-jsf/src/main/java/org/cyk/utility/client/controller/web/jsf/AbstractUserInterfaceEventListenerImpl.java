package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.session.SessionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.user.interface_.UserInterfaceEvent;
import org.cyk.utility.__kernel__.variable.VariableName;
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
		case LOGOUT:
			String username = SessionHelper.getUserName();
			LogHelper.logInfo("Logout of user named <<"+username+">>", getClass());
	    	SessionHelper.destroy(username);
	    	listenLogoutRedirect(username);
			return;
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
	
	protected void listenLogoutRedirect(String username) {
		String url = ConfigurationHelper.getValueAsString(VariableName.SYSTEM_WEB_HOME_URL);
		if(StringHelper.isBlank(url)) {
			LogHelper.logInfo("Redirecting logout user <<"+username+">> to index view", getClass());
			JsfController.getInstance().redirect("indexView");			
		}else {
			LogHelper.logInfo("Redirecting logout user <<"+username+">> to "+url, getClass());
			Redirector.getInstance().redirect(url);		
		}
	}
}