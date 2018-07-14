package org.cyk.utility.sql.builder;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndStringAsOutput;

public interface QueryAttributeNameBuilder extends FunctionWithPropertiesAsInputAndStringAsOutput {

	QueryAttributeNameBuilder setAttribute(Attribute attribute);
	Attribute getAttribute();
	
	QueryAttributeNameBuilder setIsPrefixedWithTuple(Boolean isPrefixedWithTuple);
	Boolean getIsPrefixedWithTuple();
}
