package org.cyk.utility.sql.builder;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.throwable.ThrowableHelper;

public abstract class AbstractQueryClauseStringBuilderWhereImpl extends AbstractQueryClauseStringBuilderImpl implements QueryClauseStringBuilderWhere, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setKeyword("WHERE");
		//getProperties().setFromPath(new Object[]{Properties.IS,Properties.TUPLE,Properties.REQUIRED}, Boolean.TRUE);
	}
	
	@Override
	protected Collection<String> __executeGetArguments__(Collection<Tuple> tuples, Collection<String> arguments) {
		QueryWherePredicateStringBuilder predicateBuilder = getPredicateBuilder();
		if(predicateBuilder == null)
			__inject__(ThrowableHelper.class).throwRuntimeException("Sql query clause where : predicate is required");
		return __inject__(CollectionHelper.class).isEmpty(arguments) ? __inject__(CollectionHelper.class).instanciate(predicateBuilder.execute().getOutput()) 
				: super.__executeGetArguments__(tuples, arguments);
	}
	
	@Override
	public QueryWherePredicateStringBuilder getPredicateBuilder() {
		return (QueryWherePredicateStringBuilder) getProperties().getFromPath(Properties.PREDICATE,Properties.BUILDER);
	}

	@Override
	public QueryClauseStringBuilderWhere setPredicateBuilder(QueryWherePredicateStringBuilder builder) {
		getProperties().setFromPath(new Object[]{Properties.PREDICATE,Properties.BUILDER}, builder);
		return this;
	}
}
