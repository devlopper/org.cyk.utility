package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;
import java.util.List;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.session.SessionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.user.interface_.UserInterfaceEvent;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.__kernel__.user.interface_.message.Severity;
import org.cyk.utility.__kernel__.variable.VariableName;

public abstract class AbstractUserInterfaceEventListenerImpl extends org.cyk.utility.__kernel__.user.interface_.AbstractUserInterfaceEventListenerImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void listen(UserInterfaceEvent event) {
		if(event == null)
			return;
		String message;
		Severity severity = null;
		switch(event) {
		case IDLE:
			message = "Vous êtes inactif.vous risquez de perdre votre session.";
			severity = Severity.WARNING;
			break;
		case ACTIVATE:
			message = "Vous êtes de retour.";
			severity = Severity.INFORMATION;
			break;
		case LOGOUT:
			String username = SessionHelper.getUserName();
			LogHelper.logInfo("Logout of user named <<"+username+">>", getClass());
	    	SessionHelper.destroy(username);
	    	listenLogoutRedirect(username);
			return;
		default:
			message = "Quel évènement ?";
			severity = Severity.ERROR;
		}
		__inject__(MessageRenderer.class).render("Notification", message, severity, List.of(RenderType.GROWL));
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