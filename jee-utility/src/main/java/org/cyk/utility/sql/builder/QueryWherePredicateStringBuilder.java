package org.cyk.utility.sql.builder;

public interface QueryWherePredicateStringBuilder extends QueryPredicateStringBuilder {

	QueryWherePredicateStringBuilder setParent(Object parent);
	
	QueryClauseStringBuilderWhere getParentAsWhereClause();
	
}
