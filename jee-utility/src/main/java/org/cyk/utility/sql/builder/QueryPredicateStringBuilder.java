package org.cyk.utility.sql.builder;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndStringAsOutput;

public interface QueryPredicateStringBuilder extends FunctionWithPropertiesAsInputAndStringAsOutput {
	
	QueryPredicateStringBuilder addOperandStringBuilderAttributeName(String attributeName,Tuple tuple);
	QueryPredicateStringBuilder addOperandStringBuilderParameterName(String parameterName);
	
}
