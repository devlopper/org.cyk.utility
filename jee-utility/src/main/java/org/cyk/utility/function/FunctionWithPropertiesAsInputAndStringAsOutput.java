package org.cyk.utility.function;

import java.util.Collection;

public interface FunctionWithPropertiesAsInputAndStringAsOutput extends FunctionWithPropertiesAsInput<String> {

	FunctionWithPropertiesAsInputAndStringAsOutput setFormat(String format);
	String getFormat();
	
	Collection<Object> getFormatArguments();
	FunctionWithPropertiesAsInputAndStringAsOutput setFormatArguments(Collection<Object> formatArguments);
	FunctionWithPropertiesAsInputAndStringAsOutput addFormatArguments(Collection<Object> formatArguments);
	FunctionWithPropertiesAsInputAndStringAsOutput addFormatArgumentObjects(Object...formatArguments);
	
	@Override FunctionWithPropertiesAsInputAndStringAsOutput getParent();
}
