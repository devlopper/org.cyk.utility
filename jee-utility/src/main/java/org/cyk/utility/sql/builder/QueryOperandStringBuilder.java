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
	QueryAttributeNameBuilder getAttributeNameBuilder();
	
	QueryOperandStringBuilder setLiteral(Object literal);
	Object getLiteral();
	
	QueryOperandStringBuilder setParameterStringBuilder(QueryParameterStringBuilder builder);
	QueryParameterStringBuilder getParameterStringBuilder();
}
