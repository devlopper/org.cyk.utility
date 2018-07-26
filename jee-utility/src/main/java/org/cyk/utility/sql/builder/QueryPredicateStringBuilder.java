package org.cyk.utility.sql.builder;

import org.cyk.utility.criteria.Criteria;
import org.cyk.utility.function.FunctionWithPropertiesAsInputAndStringAsOutput;

public interface QueryPredicateStringBuilder extends FunctionWithPropertiesAsInputAndStringAsOutput {
	
	QueryPredicateStringBuilder addOperandStringBuilderAttributeName(String attributeName,Tuple tuple);
	QueryPredicateStringBuilder addOperandStringBuilderParameterName(String parameterName);
	
	Criteria getCriteria();
	QueryPredicateStringBuilder setCriteria(Criteria criteria);
	
	QueryPredicateStringBuilder addChild(Object... child);
	
	@Override FunctionWithPropertiesAsInputAndStringAsOutput and();
	@Override FunctionWithPropertiesAsInputAndStringAsOutput or();
	
	Tuple getTuple();
	QueryPredicateStringBuilder setTuple(Tuple tuple);
}
