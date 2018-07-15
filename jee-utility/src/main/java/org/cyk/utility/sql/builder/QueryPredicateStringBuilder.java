package org.cyk.utility.sql.builder;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndStringAsOutput;

public interface QueryPredicateStringBuilder extends FunctionWithPropertiesAsInputAndStringAsOutput {

	String getOperator();
	QueryPredicateStringBuilder setOperator(String operator);
	
	QueryOperandStringBuilder getFirstOperandStringBuilder();
	QueryPredicateStringBuilder setFirstOperandStringBuilder(QueryOperandStringBuilder builder);
	
	QueryOperandStringBuilder getSecondOperandStringBuilder();
	QueryPredicateStringBuilder setSecondOperandStringBuilder(QueryOperandStringBuilder builder);
	
	QueryPredicateStringBuilder setOperandStringBuilders(QueryOperandStringBuilder...operandsBuilders);
	QueryPredicateStringBuilder addOperandStringBuilderAttributeName(String attributeName,Tuple tuple);
	QueryPredicateStringBuilder addOperandStringBuilderParameterString(String parameterName);
	
	QueryPredicateStringBuilder execute(QueryOperandStringBuilder...operandsBuilders);
}
