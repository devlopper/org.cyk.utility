package org.cyk.utility.sql.builder;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndStringAsOutput;

public interface QueryParameterStringBuilder extends FunctionWithPropertiesAsInputAndStringAsOutput {

	QueryParameterStringBuilder setParameterName(String name);
	String getParameterName();
	
}
