package org.cyk.utility.sql.builder;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndStringAsOutput;

public interface QueryPredicateStringBuilder extends FunctionWithPropertiesAsInputAndStringAsOutput {

	String getOperator();
	QueryPredicateStringBuilder setOperator(String operator);
	
	QueryOperandStringBuilder getLeftOperandStringBuilder();
	QueryPredicateStringBuilder setLeftOperandStringBuilder(QueryOperandStringBuilder builder);
	
	QueryOperandStringBuilder getRightOperandStringBuilder();
	QueryPredicateStringBuilder setRightOperandStringBuilder(QueryOperandStringBuilder builder);
}
