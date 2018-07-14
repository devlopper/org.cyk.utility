package org.cyk.utility.function;

import java.util.Collection;

public interface FunctionWithPropertiesAsInputAndStringAsOutput extends FunctionWithPropertiesAsInput<String> {

	FunctionWithPropertiesAsInputAndStringAsOutput setFormat(String format);
	String getFormat();
	
	Collection<String> getFormatArguments();
	FunctionWithPropertiesAsInputAndStringAsOutput setFormatArguments(Collection<String> formatArguments);
	FunctionWithPropertiesAsInputAndStringAsOutput addFormatArguments(Collection<String> formatArguments);
	FunctionWithPropertiesAsInputAndStringAsOutput addFormatArguments(String...formatArguments);
	
	@Override FunctionWithPropertiesAsInputAndStringAsOutput getParent();
}
