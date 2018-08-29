package org.cyk.utility.client.controller.web.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.message.MessagesBuilder;
import org.cyk.utility.notification.Notification;
import org.cyk.utility.notification.NotificationSeverity;
import org.cyk.utility.notification.NotificationSeverityError;
import org.cyk.utility.notification.NotificationSeverityFatal;
import org.cyk.utility.notification.NotificationSeverityInformation;
import org.cyk.utility.notification.NotificationSeverityWarning;

public class MessagesBuilderFunctionRunnableImpl extends AbstractFunctionRunnableImpl<MessagesBuilder> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public MessagesBuilderFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				Collection<Object> collection = new ArrayList<>();
				for(Notification  index : getFunction().getNotifications())
					collection.add(__instanciate__(index));
				setOutput(collection);
			}
		});
	}
	
	protected FacesMessage __instanciate__(Notification notification) {
		return new FacesMessage(__getSeverity__(notification.getSeverity()), notification.getSummary(), notification.getDetails());
	}
	
	protected Severity __getSeverity__(NotificationSeverity severity) {
		if(severity instanceof NotificationSeverityInformation)
			return FacesMessage.SEVERITY_INFO;
		if(severity instanceof NotificationSeverityWarning)
			return FacesMessage.SEVERITY_WARN;
		if(severity instanceof NotificationSeverityError)
			return FacesMessage.SEVERITY_ERROR;
		if(severity instanceof NotificationSeverityFatal)
			return FacesMessage.SEVERITY_FATAL;
		System.err.println("Face message severity cannot be found from "+severity);
		return null;
	}
}