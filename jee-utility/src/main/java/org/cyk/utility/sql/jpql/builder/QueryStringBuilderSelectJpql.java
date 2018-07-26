package org.cyk.utility.sql.jpql.builder;

import org.cyk.utility.sql.builder.QueryStringBuilderSelect;
import org.cyk.utility.sql.builder.QueryWherePredicateStringBuilder;
import org.cyk.utility.sql.builder.Tuple;

public interface QueryStringBuilderSelectJpql extends QueryStringBuilderSelect {

	QueryStringBuilderSelectJpql from(Tuple tuple);
	
	QueryStringBuilderSelectJpql where(QueryWherePredicateStringBuilder predicateBuilder);
	
	QueryStringBuilderSelectJpql select(Tuple tuple);
}
