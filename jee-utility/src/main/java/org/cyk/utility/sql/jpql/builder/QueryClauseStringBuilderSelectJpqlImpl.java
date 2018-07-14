package org.cyk.utility.sql.jpql.builder;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.sql.builder.AbstractQueryClauseStringBuilderSelectImpl;
import org.cyk.utility.sql.builder.Tuple;
import org.cyk.utility.sql.jpql.Jpql;

@Jpql
public class QueryClauseStringBuilderSelectJpqlImpl extends AbstractQueryClauseStringBuilderSelectImpl implements QueryClauseStringBuilderSelectJpql, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String getAllColumnsArgument(Collection<Tuple> tuples) {
		return __inject__(CollectionHelper.class).getFirst(tuples).getAlias();
	}

}
