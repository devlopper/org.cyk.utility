package org.cyk.utility.function;

import org.cyk.utility.__kernel__.properties.Properties;

public interface FunctionWithPropertiesAsInput<OUTPUT> extends Function<Properties, OUTPUT> {

	Properties getOutputProperties();
	Properties getOutputProperties(Boolean injectIfNull);
	FunctionWithPropertiesAsInput<OUTPUT> setOutputProperties(Properties outputProperties);
	
	Object getOutputProperty(Object key);
	FunctionWithPropertiesAsInput<OUTPUT> setOutputProperty(Object key,Object value);
	
	Object getOutputPropertyName();
	FunctionWithPropertiesAsInput<OUTPUT> setOutputPropertyName(Object value);
	
	Object getOutputPropertyValue();
	FunctionWithPropertiesAsInput<OUTPUT> setOutputPropertyValue(Object value);
}
