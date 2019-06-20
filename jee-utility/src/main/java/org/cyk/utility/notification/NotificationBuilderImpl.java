package org.cyk.utility.notification;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.internationalization.InternalizationStringBuilder;
import org.cyk.utility.string.StringHelper;

@Dependent
public class NotificationBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Notification> implements NotificationBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Throwable throwable;
	private NotificationSeverity severity;
	private String summary;
	private InternalizationStringBuilder summaryInternalizationString;
	private String details;
	private InternalizationStringBuilder detailsInternalizationString;
	
	@Override
	protected Notification __execute__() throws Exception {
		Notification notification = __inject__(Notification.class);
		Throwable throwable = getThrowable();
		NotificationSeverity severity = getSeverity();
		if(severity == null) {
			if(throwable == null)
				severity = __inject__(NotificationSeverityInformation.class);
			else
				severity = __inject__(NotificationSeverityError.class);
		}
		notification.setSeverity(severity);
		
		String summary = getSummary();
		if(__inject__(StringHelper.class).isBlank(summary)) {
			InternalizationStringBuilder summaryInternalizationString = getSummaryInternalizationString();
			if(summaryInternalizationString==null) {
				if(throwable!=null) {
					throwable.printStackTrace();//TODO to be removed or to be logged to a specific file named notification
					summary = __inject__(InternalizationStringBuilder.class).setKeyValue(throwable).execute().getOutput();
				}
			}else
				summary = summaryInternalizationString.execute().getOutput();	
		}
		notification.setSummary(summary);
		
		String details = getDetails();
		if(__inject__(StringHelper.class).isBlank(details)) {
			InternalizationStringBuilder detailsInternalizationString = getDetailsInternalizationString();
			if(detailsInternalizationString==null) {
				if(throwable!=null) {
					//TODO find a way to get the stack trace as lines in order to build the details
					details = throwable.getMessage();//__inject__(StackTraceHelper.class).getStackTraceAsString();
				}
			}else
				details = detailsInternalizationString.execute().getOutput();	
		}
		notification.setDetails(details);
		
		return notification;
	}
	
	@Override
	public Throwable getThrowable() {
		return throwable;
	}
	
	@Override
	public NotificationBuilder setThrowable(Throwable throwable) {
		this.throwable = throwable;
		return this;
	}
	
	@Override
	public NotificationSeverity getSeverity() {
		return severity;
	}

	@Override
	public NotificationBuilder setSeverity(NotificationSeverity severity) {
		this.severity = severity;
		return this;
	}

	@Override
	public InternalizationStringBuilder getSummaryInternalizationString() {
		return summaryInternalizationString;
	}
	
	@Override
	public InternalizationStringBuilder getSummaryInternalizationString(Boolean injectIfNull) {
		return (InternalizationStringBuilder) __getInjectIfNull__(FIELD_SUMMARY_INTERNALIZATION_STRING, injectIfNull);
	}

	@Override
	public NotificationBuilder setSummaryInternalizationString(InternalizationStringBuilder summaryInternalizationString) {
		this.summaryInternalizationString = summaryInternalizationString;
		return this;
	}
	
	@Override
	public NotificationBuilder setSummaryInternalizationStringKeyValue(Object value) {
		getSummaryInternalizationString(Boolean.TRUE).setKeyValue(value);
		return this;
	}

	@Override
	public InternalizationStringBuilder getDetailsInternalizationString() {
		return detailsInternalizationString;
	}
	
	@Override
	public InternalizationStringBuilder getDetailsInternalizationString(Boolean injectIfNull) {
		return (InternalizationStringBuilder) __getInjectIfNull__(FIELD_DETAILS_INTERNALIZATION_STRING, injectIfNull);
	}

	@Override
	public NotificationBuilder setDetailsInternalizationString(InternalizationStringBuilder detailsInternalizationString) {
		this.detailsInternalizationString = detailsInternalizationString;
		return this;
	}
	
	@Override
	public NotificationBuilder setDetailsInternalizationStringKeyValue(Object value) {
		getDetailsInternalizationString(Boolean.TRUE).setKeyValue(value);
		return this;
	}
	
	@Override
	public String getDetails() {
		return details;
	}
	
	@Override
	public NotificationBuilder setDetails(String details) {
		this.details = details;
		return this;
	}
	@Override
	public String getSummary() {
		return summary;
	}
	
	@Override
	public NotificationBuilder setSummary(String summary) {
		this.summary = summary;
		return this;
	}
	
	public static final String FIELD_SUMMARY_INTERNALIZATION_STRING = "summaryInternalizationString";
	public static final String FIELD_DETAILS_INTERNALIZATION_STRING = "detailsInternalizationString";

}
