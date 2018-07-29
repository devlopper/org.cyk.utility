package org.cyk.utility.sql.builder;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;

public interface QueryWherePredicateStringBuilderGroup extends QueryWherePredicateStringBuilder {

	//QueryWherePredicateStringBuilderGroup setParent(Object parent);
	
	//QueryClauseStringBuilderWhere getParentAsWhereClause();
	
	QueryWherePredicateStringBuilderGroup addChild(Object... child);
	QueryWherePredicateStringBuilderGroup addOperandBuilderByAttribute(String attributeName,ComparisonOperator operator,Tuple tuple,String parameterName);
	QueryWherePredicateStringBuilderGroup addOperandBuilderByAttribute(String attributeName,ComparisonOperator operator,Tuple tuple);
	QueryWherePredicateStringBuilderGroup addOperandBuilderByAttribute(String attributeName,ComparisonOperator operator,String parameterName);
	QueryWherePredicateStringBuilderGroup addOperandBuilderByAttribute(String attributeName,ComparisonOperator operator);
	
	
	QueryWherePredicateStringBuilderGroup lp();
	QueryWherePredicateStringBuilderGroup rp();
	QueryWherePredicateStringBuilderGroup and();
	QueryWherePredicateStringBuilderGroup or();
}
