package org.cyk.utility.sql.builder;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;

public abstract class AbstractQueryStringBuilderSelectImpl extends AbstractQueryStringBuilderImpl implements QueryStringBuilderSelect, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __execute__() throws Exception {
		addChild(getSelectBuilder(),getFromBuilder(),getWhereBuilder());
		return super.__execute__();
	}
	
	@Override
	public QueryClauseStringBuilderSelect getSelectBuilder() {
		return (QueryClauseStringBuilderSelect) getProperties().getFromPath(Properties.BUILDER,Properties.SELECT);
	}

	@Override
	public QueryStringBuilder setSelectBuilder(QueryClauseStringBuilderSelect builder) {
		getProperties().setFromPath(new Object[]{Properties.BUILDER,Properties.SELECT}, builder);
		return this;
	}

	@Override
	public QueryClauseStringBuilderFrom getFromBuilder() {
		return (QueryClauseStringBuilderFrom) getProperties().getFromPath(Properties.BUILDER,Properties.FROM);
	}

	@Override
	public QueryStringBuilder setFromBuilder(QueryClauseStringBuilderFrom builder) {
		getProperties().setFromPath(new Object[]{Properties.BUILDER,Properties.FROM}, builder);
		return this;
	}

	@Override
	public QueryClauseStringBuilderWhere getWhereBuilder() {
		return (QueryClauseStringBuilderWhere) getProperties().getFromPath(Properties.BUILDER,Properties.WHERE);
	}

	@Override
	public QueryStringBuilder setWhereBuilder(QueryClauseStringBuilderWhere builder) {
		getProperties().setFromPath(new Object[]{Properties.BUILDER,Properties.WHERE}, builder);
		return this;
	}

}
