package org.cyk.utility.sql.builder;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndStringAsOutput;

public interface QueryOperandStringBuilder extends FunctionWithPropertiesAsInputAndStringAsOutput {

	/*
	 * Operand can be :
	 * 1 - attribute
	 * 2 - literal
	 * 3 - parameter
	 */
	
	QueryOperandStringBuilder setAttributeNameBuilder(QueryAttributeNameBuilder builder);
	QueryOperandStringBuilder setAttributeNameBuilder(String attributeName,Tuple tuple);
	QueryAttributeNameBuilder getAttributeNameBuilder();
	
	QueryOperandStringBuilder setLiteral(Object literal);
	Object getLiteral();
	
	QueryOperandStringBuilder setParameterNameBuilder(QueryParameterNameBuilder builder);
	QueryOperandStringBuilder setParameterNameBuilder(String parameterName);
	QueryParameterNameBuilder getParameterNameBuilder();
	
	QueryOperandStringBuilder executeAttribute(String attributeName,Tuple tuple);
	QueryOperandStringBuilder executeLiteral(Object literal);
	QueryOperandStringBuilder executeParameter(String name);
}
