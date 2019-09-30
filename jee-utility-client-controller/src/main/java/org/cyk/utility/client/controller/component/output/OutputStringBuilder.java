package org.cyk.utility.client.controller.component.output;

import org.cyk.utility.__kernel__.internationalization.InternationalizationString;

public interface OutputStringBuilder<OUTPUT extends OutputString> extends OutputBuilder<OUTPUT,String> {
	
	InternationalizationString getValueInternationalizationString();
	InternationalizationString getValueInternationalizationString(Boolean injectIfNull);
	OutputStringBuilder<OUTPUT> setValueInternationalizationString(InternationalizationString valueInternationalizationString);
}
