package org.cyk.utility.sql.builder;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndStringAsOutput;

public interface QueryStringBuilder extends FunctionWithPropertiesAsInputAndStringAsOutput {

	
	
	QueryClauseStringBuilderFrom getFromBuilder();
	QueryStringBuilder setFromBuilder(QueryClauseStringBuilderFrom builder);
	
	QueryClauseStringBuilderWhere getWhereBuilder();
	QueryStringBuilder setWhereBuilder(QueryClauseStringBuilderWhere builder);
}
