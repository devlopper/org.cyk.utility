package org.cyk.utility.sql.builder;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndStringAsOutput;

public interface QueryStringBuilder extends FunctionWithPropertiesAsInputAndStringAsOutput {

	QueryClauseStringBuilderFrom getFromClauseBuilder();
	QueryStringBuilder setFromClauseBuilder(QueryClauseStringBuilderFrom builder);
	QueryStringBuilder from(Tuple tuple);
	
	QueryClauseStringBuilderWhere getWhereClauseBuilder();
	QueryStringBuilder setWhereClauseBuilder(QueryClauseStringBuilderWhere builder);
	QueryStringBuilder where(QueryWherePredicateStringBuilder predicateBuilder);
}
