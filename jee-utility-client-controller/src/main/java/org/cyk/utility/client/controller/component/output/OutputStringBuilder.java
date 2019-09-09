package org.cyk.utility.client.controller.component.output;

import org.cyk.utility.internationalization.InternationalizationString;

public interface OutputStringBuilder<OUTPUT extends OutputString> extends OutputBuilder<OUTPUT,String> {
	
	InternationalizationString getValueInternationalizationString();
	InternationalizationString getValueInternationalizationString(Boolean injectIfNull);
	OutputStringBuilder<OUTPUT> setValueInternationalizationString(InternationalizationString valueInternationalizationString);
}
