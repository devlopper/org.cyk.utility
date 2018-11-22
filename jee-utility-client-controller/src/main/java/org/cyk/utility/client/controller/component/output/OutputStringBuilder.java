package org.cyk.utility.client.controller.component.output;

import org.cyk.utility.internationalization.InternalizationStringBuilder;

public interface OutputStringBuilder<OUTPUT extends OutputString> extends OutputBuilder<OUTPUT,String> {
	
	InternalizationStringBuilder getValueInternalization();
	InternalizationStringBuilder getValueInternalization(Boolean injectIfNull);
	OutputStringBuilder<OUTPUT> setValueInternalization(InternalizationStringBuilder valueInternalization);
	OutputStringBuilder<OUTPUT> setValueInternalizationKeyValue(String valueInternalizationKeyValue);
}
