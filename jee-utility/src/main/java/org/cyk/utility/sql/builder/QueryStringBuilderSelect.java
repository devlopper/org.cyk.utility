package org.cyk.utility.sql.builder;

public interface QueryStringBuilderSelect extends QueryStringBuilder {

	QueryClauseStringBuilderSelect getSelectBuilder();
	QueryStringBuilder setSelectBuilder(QueryClauseStringBuilderSelect builder);
	
}
