package org.cyk.utility.sql.builder;

public interface QueryClauseStringBuilderWhere extends QueryClauseStringBuilder {

	QueryWherePredicateStringBuilder getPredicateBuilder();
	QueryClauseStringBuilderWhere setPredicateBuilder(QueryWherePredicateStringBuilder predicate);
	
}