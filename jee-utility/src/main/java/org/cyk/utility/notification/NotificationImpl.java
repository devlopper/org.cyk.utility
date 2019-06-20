package org.cyk.utility.notification;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

@Dependent
public class NotificationImpl extends AbstractObject implements Notification,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public NotificationSeverity getSeverity() {
		return (NotificationSeverity) getProperties().getSeverity();
	}

	@Override
	public Notification setSeverity(NotificationSeverity severity) {
		getProperties().setSeverity(severity);
		return this;
	}

	@Override
	public String getSummary() {
		return (String) getProperties().get("summary");
	}

	@Override
	public Notification setSummary(String summary) {
		getProperties().set("summary", summary);
		return this;
	}

	@Override
	public String getDetails() {
		return (String) getProperties().get("details");
	}

	@Override
	public Notification setDetails(String details) {
		getProperties().set("details", details);
		return this;
	}

}
