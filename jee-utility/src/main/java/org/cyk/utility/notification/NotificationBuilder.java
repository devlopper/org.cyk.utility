package org.cyk.utility.notification;

import org.cyk.utility.__kernel__.internationalization.InternationalizationString;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface NotificationBuilder extends FunctionWithPropertiesAsInput<Notification> {

	NotificationSeverity getSeverity();
	NotificationBuilder setSeverity(NotificationSeverity severity);
	
	String getSummary();
	NotificationBuilder setSummary(String summary);
	
	String getDetails();
	NotificationBuilder setDetails(String details);
	
	InternationalizationString getSummaryInternationalizationString();
	InternationalizationString getSummaryInternationalizationString(Boolean injectIfNull);
	NotificationBuilder setSummaryInternationalizationString(InternationalizationString summaryInternationalizationString);
	NotificationBuilder setSummaryInternationalizationStringKeyValue(Object value);
	
	InternationalizationString getDetailsInternationalizationString();
	InternationalizationString getDetailsInternationalizationString(Boolean injectIfNull);
	NotificationBuilder setDetailsInternationalizationString(InternationalizationString detailsInternationalizationString);
	NotificationBuilder setDetailsInternationalizationStringKeyValue(Object value);
	
	Throwable getThrowable();
	NotificationBuilder setThrowable(Throwable throwable);
}
