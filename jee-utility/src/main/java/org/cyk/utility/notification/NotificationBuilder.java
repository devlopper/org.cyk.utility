package org.cyk.utility.notification;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.internationalization.InternalizationStringBuilder;

public interface NotificationBuilder extends FunctionWithPropertiesAsInput<Notification> {

	NotificationSeverity getSeverity();
	NotificationBuilder setSeverity(NotificationSeverity severity);
	
	InternalizationStringBuilder getSummaryInternalizationStringBuilder();
	NotificationBuilder setSummaryInternalizationStringBuilder(InternalizationStringBuilder summaryInternalizationStringBuilder);
	
	InternalizationStringBuilder getDetailsInternalizationStringBuilder();
	NotificationBuilder setDetailsInternalizationStringBuilder(InternalizationStringBuilder detailsInternalizationStringBuilder);
	
}
