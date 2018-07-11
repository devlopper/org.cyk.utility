package org.cyk.utility.sql.builder;

import java.io.Serializable;
import java.util.Collection;

public class QueryClauseStringBuilderSelectImpl extends AbstractQueryClauseStringBuilderSelectImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String getAllColumnsArgument(Collection<Tuple> tuples) {
		return "*";
	}
	
}
