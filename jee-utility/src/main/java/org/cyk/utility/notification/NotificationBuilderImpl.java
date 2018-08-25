package org.cyk.utility.notification;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.internationalization.InternalizationStringBuilder;

public class NotificationBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Notification> implements NotificationBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setSeverity(__inject__(NotificationSeverityInformation.class));
		setSummaryInternalizationStringBuilder(__inject__(InternalizationStringBuilder.class));
		setDetailsInternalizationStringBuilder(__inject__(InternalizationStringBuilder.class));
	}
	
	@Override
	protected Notification __execute__() throws Exception {
		Notification notification = __inject__(Notification.class);
		notification.setSeverity(getSeverity());
		notification.setSummary(getSummaryInternalizationStringBuilder().execute().getOutput());
		notification.setDetails(getDetailsInternalizationStringBuilder().execute().getOutput());
		return notification;
	}
	
	@Override
	public NotificationSeverity getSeverity() {
		return (NotificationSeverity) getProperties().getSeverity();
	}

	@Override
	public NotificationBuilder setSeverity(NotificationSeverity severity) {
		getProperties().setSeverity(severity);
		return this;
	}

	@Override
	public InternalizationStringBuilder getSummaryInternalizationStringBuilder() {
		return (InternalizationStringBuilder) getProperties().get("SummaryInternalizationStringBuilder");
	}

	@Override
	public NotificationBuilder setSummaryInternalizationStringBuilder(InternalizationStringBuilder summaryInternalizationStringBuilder) {
		getProperties().set("SummaryInternalizationStringBuilder", summaryInternalizationStringBuilder);
		return this;
	}

	@Override
	public InternalizationStringBuilder getDetailsInternalizationStringBuilder() {
		return (InternalizationStringBuilder) getProperties().get("DetailsInternalizationStringBuilder");
	}

	@Override
	public NotificationBuilder setDetailsInternalizationStringBuilder(InternalizationStringBuilder detailsInternalizationStringBuilder) {
		getProperties().set("DetailsInternalizationStringBuilder", detailsInternalizationStringBuilder);
		return this;
	}

}
