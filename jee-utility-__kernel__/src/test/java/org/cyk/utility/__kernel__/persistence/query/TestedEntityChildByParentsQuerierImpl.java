package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.__entities__.TestedEntityChild;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;

public class TestedEntityChildByParentsQuerierImpl extends AbstractObject implements TestedEntityChildByParentsQuerier,Serializable {

	@Override
	public Collection<TestedEntityChild> readByIdentifiers(Collection<String> businessIdentifiers,QueryExecutorArguments arguments) {
		if(CollectionHelper.isEmpty(businessIdentifiers))
			return null;
		if(arguments == null)
			arguments = new QueryExecutorArguments().setQuery(QueryGetter.getInstance().getBySelect(TestedEntityChild.class,QUERY_NAME_READ))
			.addFilterField(PARAMETER_NAME_PARENTS_CODES,businessIdentifiers);
		return QueryExecutor.getInstance().executeReadMany(TestedEntityChild.class,arguments);
	}

	@Override
	public Long countByIdentifiers(Collection<String> businessIdentifiers, QueryExecutorArguments arguments) {
		if(CollectionHelper.isEmpty(businessIdentifiers))
			return null;
		if(arguments == null)
			arguments = new QueryExecutorArguments().setQuery(QueryGetter.getInstance().getByCount(TestedEntityChild.class,QUERY_NAME_COUNT))
			.addFilterField(PARAMETER_NAME_PARENTS_CODES,businessIdentifiers);
		return QueryExecutor.getInstance().executeCount(arguments);
	}	
}