package org.cyk.utility.sql.builder;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndStringAsOutput;

public interface QueryStringBuilder extends FunctionWithPropertiesAsInputAndStringAsOutput {

	QueryClauseStringBuilderFrom getFromClauseBuilder();
	QueryClauseStringBuilderFrom getFromClauseBuilder(Boolean injectIfNull);
	QueryStringBuilder setFromClauseBuilder(QueryClauseStringBuilderFrom builder);
	QueryStringBuilder from(Tuple tuple);
	
	QueryClauseStringBuilderWhere getWhereClauseBuilder();
	QueryClauseStringBuilderWhere getWhereClauseBuilder(Boolean injectIfNull);
	QueryStringBuilder setWhereClauseBuilder(QueryClauseStringBuilderWhere builder);
	QueryStringBuilder where(QueryWherePredicateStringBuilder predicateBuilder);
	<I extends QueryWherePredicateStringBuilder> I getWherePredicateBuilderAs(Class<I> aClass);
	QueryWherePredicateStringBuilderEqual getWherePredicateBuilderAsEqual();
}
