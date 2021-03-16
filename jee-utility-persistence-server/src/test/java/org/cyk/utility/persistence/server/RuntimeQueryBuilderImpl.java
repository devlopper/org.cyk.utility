package org.cyk.utility.persistence.server;

import java.io.Serializable;

import org.cyk.utility.__kernel__.annotation.Test;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryName;
import org.cyk.utility.persistence.server.query.RuntimeQueryBuilder;

@Test
public class RuntimeQueryBuilderImpl extends RuntimeQueryBuilder.AbstractImpl implements Serializable {

	@Override
	protected void setValue(Query query, String value) {
		super.setValue(query, value);
		if(query.isIdentifierEquals(DataType.class, QueryName.READ_DYNAMIC))
			query.setIntermediateResultClass(Object[].class).setTupleFieldsNamesIndexesFromFieldsNames("identifier","code","name");
	}

}