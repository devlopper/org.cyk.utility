package org.cyk.utility.sql.builder;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;

public interface QueryWherePredicateStringBuilderEqual extends QueryWherePredicateStringBuilder {

	ComparisonOperator getOperator();
	QueryWherePredicateStringBuilderEqual setOperator(ComparisonOperator operator);
	
	QueryWherePredicateStringBuilderEqual addOperandBuilderByAttributeByParameter(String attributeName,ComparisonOperator operator,Tuple tuple,String parameterName);
	QueryWherePredicateStringBuilderEqual addOperandBuilderByAttribute(String attributeName,ComparisonOperator operator,Tuple tuple);
	QueryWherePredicateStringBuilderEqual addOperandBuilderByAttribute(String attributeName,ComparisonOperator operator);
	
	/**/
	
}
