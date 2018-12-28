package org.cyk.utility.notification;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.internationalization.InternalizationStringBuilder;

public interface NotificationBuilder extends FunctionWithPropertiesAsInput<Notification> {

	NotificationSeverity getSeverity();
	NotificationBuilder setSeverity(NotificationSeverity severity);
	
	String getSummary();
	NotificationBuilder setSummary(String summary);
	
	String getDetails();
	NotificationBuilder setDetails(String details);
	
	InternalizationStringBuilder getSummaryInternalizationString();
	InternalizationStringBuilder getSummaryInternalizationString(Boolean injectIfNull);
	NotificationBuilder setSummaryInternalizationString(InternalizationStringBuilder summaryInternalizationString);
	NotificationBuilder setSummaryInternalizationStringKeyValue(Object value);
	
	InternalizationStringBuilder getDetailsInternalizationString();
	InternalizationStringBuilder getDetailsInternalizationString(Boolean injectIfNull);
	NotificationBuilder setDetailsInternalizationString(InternalizationStringBuilder detailsInternalizationString);
	NotificationBuilder setDetailsInternalizationStringKeyValue(Object value);
	
	Throwable getThrowable();
	NotificationBuilder setThrowable(Throwable throwable);
}
