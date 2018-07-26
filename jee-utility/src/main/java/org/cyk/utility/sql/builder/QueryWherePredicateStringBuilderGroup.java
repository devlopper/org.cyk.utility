package org.cyk.utility.sql.builder;

public interface QueryWherePredicateStringBuilderGroup extends QueryWherePredicateStringBuilder {

	//QueryWherePredicateStringBuilderGroup setParent(Object parent);
	
	//QueryClauseStringBuilderWhere getParentAsWhereClause();
	
	QueryWherePredicateStringBuilderGroup addChild(Object... child);
	QueryWherePredicateStringBuilderGroup addOperandBuilderByAttribute(String attributeName,Tuple tuple);
}
