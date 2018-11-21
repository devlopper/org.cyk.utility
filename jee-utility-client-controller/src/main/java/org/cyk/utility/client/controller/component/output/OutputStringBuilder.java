package org.cyk.utility.client.controller.component.output;

public interface OutputStringBuilder<OUTPUT extends OutputString> extends OutputBuilder<OUTPUT,String> {
	
	String getValueInternalizationIdentifier();
	OutputStringBuilder<OUTPUT> setValueInternalizationIdentifier(String valueInternalizationIdentifier);
	
}
