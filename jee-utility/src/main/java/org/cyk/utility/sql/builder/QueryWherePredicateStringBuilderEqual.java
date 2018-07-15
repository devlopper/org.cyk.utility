package org.cyk.utility.sql.builder;

public interface QueryWherePredicateStringBuilderEqual extends QueryWherePredicateStringBuilder {

	QueryWherePredicateStringBuilderEqual addOperandBuilderByAttributeByParameter(String attributeName,Tuple tuple,String parameterName);
	
}
