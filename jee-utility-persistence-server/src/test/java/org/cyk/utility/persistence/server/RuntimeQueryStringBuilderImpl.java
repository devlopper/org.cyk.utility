package org.cyk.utility.persistence.server;

import java.io.Serializable;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.annotation.Test;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryName;
import org.cyk.utility.persistence.server.query.string.RuntimeQueryStringBuilder;

@Test
public class RuntimeQueryStringBuilderImpl extends RuntimeQueryStringBuilder.AbstractImpl implements Serializable {

	@Override
	protected String __build__(QueryExecutorArguments arguments) {
		if(arguments.getQuery().isIdentifierEquals(DataType.class, QueryName.READ_DYNAMIC))
			if(CollectionHelper.isEmpty(arguments.getProjections()))
				return "SELECT t FROM DataType t";
			else
				return "SELECT "+arguments.getProjections().stream().map(x -> "t."+x.getFieldName()).collect(Collectors.joining(",")) +" FROM DataType t";
		return super.__build__(arguments);
	}
}