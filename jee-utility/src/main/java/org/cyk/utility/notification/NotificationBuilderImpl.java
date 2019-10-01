package org.cyk.utility.notification;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationString;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Dependent @Getter @Setter @Accessors(chain=true)
public class NotificationBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Notification> implements NotificationBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Throwable throwable;
	private NotificationSeverity severity;
	private String summary;
	private InternationalizationString summaryInternationalizationString;
	private String details;
	private InternationalizationString detailsInternationalizationString;
	
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
		if(StringHelper.isBlank(summary)) {
			InternationalizationString summaryInternationalizationString = getSummaryInternationalizationString();
			if(summaryInternationalizationString==null) {
				if(throwable!=null) {
					__log__(throwable);
					summary = InternationalizationHelper.buildString(InternationalizationHelper.buildKey(throwable));
				}
			}else {
				InternationalizationHelper.processStrings(summaryInternationalizationString);
				summary = summaryInternationalizationString.getValue();
			}
		}
		notification.setSummary(summary);
		
		String details = getDetails();
		if(StringHelper.isBlank(details)) {
			InternationalizationString detailsInternationalizationString = getDetailsInternationalizationString();
			if(detailsInternationalizationString==null) {
				if(throwable!=null) {
					__log__(throwable);
					details = InternationalizationHelper.buildString(InternationalizationHelper.buildKey(throwable));
				}
			}else {
				InternationalizationHelper.processStrings(detailsInternationalizationString);
				details = detailsInternationalizationString.getValue();
			}
		}
		notification.setDetails(details);
		
		return notification;
	}
	
	@Override
	public InternationalizationString getSummaryInternationalizationString(Boolean injectIfNull) {
		if(summaryInternationalizationString == null && Boolean.TRUE.equals(injectIfNull))
			summaryInternationalizationString = new InternationalizationString();
		return summaryInternationalizationString;
	}
	
	@Override
	public NotificationBuilder setSummaryInternationalizationStringKeyValue(Object value) {
		getSummaryInternationalizationString(Boolean.TRUE).setKey(InternationalizationHelper.buildKey(value));
		return this;
	}

	@Override
	public InternationalizationString getDetailsInternationalizationString(Boolean injectIfNull) {
		if(detailsInternationalizationString == null && Boolean.TRUE.equals(injectIfNull))
			detailsInternationalizationString = new InternationalizationString();
		return detailsInternationalizationString;
	}

	@Override
	public NotificationBuilder setDetailsInternationalizationStringKeyValue(Object value) {
		getDetailsInternationalizationString(Boolean.TRUE).setKey(InternationalizationHelper.buildKey(value));
		return this;
	}
	
}
