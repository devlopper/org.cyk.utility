package org.cyk.utility.sql.builder;

import org.cyk.utility.__kernel__.computation.SortOrder;

public interface QueryStringBuilderSelect extends QueryStringBuilder {

	QueryClauseStringBuilderSelect getSelectClauseBuilder();
	QueryClauseStringBuilderSelect getSelectClauseBuilder(Boolean injectIfNull);
	QueryStringBuilderSelect setSelectClauseBuilder(QueryClauseStringBuilderSelect builder);
	QueryStringBuilderSelect select(Tuple tuple);
	
	QueryStringBuilderSelect from(Tuple tuple);
	
	QueryStringBuilderSelect where(QueryWherePredicateStringBuilder predicateBuilder);
	
	QueryClauseStringBuilderOrderBy getOrderByClauseBuilder();
	QueryClauseStringBuilderOrderBy getOrderByClauseBuilder(Boolean injectIfNull);
	QueryStringBuilderSelect setOrderByClauseBuilder(QueryClauseStringBuilderOrderBy builder);
	QueryStringBuilderSelect orderBy(Attribute...attributes);
	QueryStringBuilderSelect orderBy(String...attributeNames);
	QueryStringBuilderSelect orderBy(String attributeName,SortOrder sortOrder);
}
