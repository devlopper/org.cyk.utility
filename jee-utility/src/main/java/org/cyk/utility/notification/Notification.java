package org.cyk.utility.notification;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface Notification extends Objectable {

	NotificationSeverity getSeverity();
	Notification setSeverity(NotificationSeverity severity);
	
	String getSummary();
	Notification setSummary(String summary);
	
	String getDetails();
	Notification setDetails(String details);
}
