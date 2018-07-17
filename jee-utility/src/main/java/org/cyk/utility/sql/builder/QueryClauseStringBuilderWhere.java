package org.cyk.utility.sql.builder;

import org.cyk.utility.filter.Filter;

public interface QueryClauseStringBuilderWhere extends QueryClauseStringBuilder {

	QueryWherePredicateStringBuilder getPredicateBuilder();
	QueryClauseStringBuilderWhere setPredicateBuilder(QueryWherePredicateStringBuilder predicate);
	
	Filter getFilter();
	QueryClauseStringBuilderWhere setFilter(Filter filter);
	
}
