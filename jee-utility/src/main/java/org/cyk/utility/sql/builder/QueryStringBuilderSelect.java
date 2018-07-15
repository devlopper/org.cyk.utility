package org.cyk.utility.sql.builder;

public interface QueryStringBuilderSelect extends QueryStringBuilder {

	QueryClauseStringBuilderSelect getSelectClauseBuilder();
	QueryClauseStringBuilderSelect getSelectClauseBuilder(Boolean injectIfNull);
	QueryStringBuilderSelect setSelectClauseBuilder(QueryClauseStringBuilderSelect builder);
	QueryStringBuilderSelect select(Tuple tuple);
	
	QueryStringBuilderSelect from(Tuple tuple);
	QueryStringBuilderSelect where(QueryWherePredicateStringBuilder predicateBuilder);
}
