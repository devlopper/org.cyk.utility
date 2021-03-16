package org.cyk.utility.persistence.server;

import java.io.Serializable;

import org.cyk.utility.__kernel__.annotation.Test;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryName;
import org.cyk.utility.persistence.server.query.string.RuntimeQueryStringBuilder;

@Test
public class RuntimeQueryStringBuilderImpl extends RuntimeQueryStringBuilder.AbstractImpl implements Serializable {

	@Override
	protected String __build__(QueryExecutorArguments arguments) {
		if(arguments.getQuery().isIdentifierEquals(DataType.class, QueryName.READ_DYNAMIC))
			return "SELECT t.identifier,t.code,t.name FROM DataType t";
		return super.__build__(arguments);
	}
}