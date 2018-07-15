package org.cyk.utility.sql.builder;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndStringAsOutput;

public interface QueryAttributeNameBuilder extends FunctionWithPropertiesAsInputAndStringAsOutput {

	QueryAttributeNameBuilder setAttribute(Attribute attribute);
	Attribute getAttribute();
	QueryAttributeNameBuilder setAttribute(String attributeName,Tuple tuple);
	QueryAttributeNameBuilder setAttribute(String attributeName);
	
	QueryAttributeNameBuilder setIsPrefixedWithTuple(Boolean isPrefixedWithTuple);
	Boolean getIsPrefixedWithTuple();
	
	QueryAttributeNameBuilder execute(String attributeName,Tuple tuple);
	QueryAttributeNameBuilder execute(String attributeName);
}
