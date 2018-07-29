package org.cyk.utility.sql.builder;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndStringAsOutput;

public interface QueryParameterNameBuilder extends FunctionWithPropertiesAsInputAndStringAsOutput {

	Parameter getParameter();
	QueryParameterNameBuilder setParameter(Parameter parameter);
	QueryParameterNameBuilder setParameter(String name);
	
	@Deprecated QueryParameterNameBuilder setParameterName(String name);
	@Deprecated String getParameterName();
	
	/**/
	
	String VALUE = "value";
}
