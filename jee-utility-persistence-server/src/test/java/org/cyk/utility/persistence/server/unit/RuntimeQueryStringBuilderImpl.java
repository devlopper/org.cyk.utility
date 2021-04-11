package org.cyk.utility.persistence.server.unit;

import java.io.Serializable;

import org.cyk.utility.__kernel__.annotation.Test;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.DataType;
import org.cyk.utility.persistence.server.query.string.LikeStringBuilder;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder.Arguments;
import org.cyk.utility.persistence.server.query.string.RuntimeQueryStringBuilder;
import org.cyk.utility.persistence.server.query.string.WhereStringBuilder.Predicate;

@Test
public class RuntimeQueryStringBuilderImpl extends RuntimeQueryStringBuilder.AbstractImpl implements Serializable {
	
	@Override
	protected void populatePredicate(QueryExecutorArguments arguments, Arguments builderArguments,Predicate predicate,Filter filter) {
		super.populatePredicate(arguments, builderArguments, predicate,filter);
		if(arguments.getQuery().isIdentifierEqualsDynamic(DataType.class)) {
			if(arguments.getFilter().hasFieldWithPath(DataType.FIELD_NAME)) {
				//predicate.add("t.name LIKE :name");
				//filter.addField(DataType.FIELD_NAME, "%"+arguments.getFilter().getFieldValue(DataType.FIELD_NAME)+"%");
				
				predicate.add(LikeStringBuilder.getInstance().build("t", DataType.FIELD_NAME, DataType.FIELD_NAME, 2));
				filter.addFieldContainsStringOrWords(DataType.FIELD_NAME, 2, arguments);
			}	
		}
	}
}