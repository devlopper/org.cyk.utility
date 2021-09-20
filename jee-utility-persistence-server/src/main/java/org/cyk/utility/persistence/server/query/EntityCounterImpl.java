package org.cyk.utility.persistence.server.query;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.persistence.PersistenceHelper;
import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.persistence.query.QueryExecutor;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryGetter;
import org.cyk.utility.persistence.query.QueryHelper;
import org.cyk.utility.persistence.query.QueryName;
import org.cyk.utility.persistence.server.audit.Arguments;
import org.cyk.utility.persistence.server.audit.AuditCounter;

@ApplicationScoped
public class EntityCounterImpl extends EntityCounter.AbstractImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Long count(Class<?> tupleClass,QueryExecutorArguments arguments) {
		validatePreconditions(tupleClass);
		if(arguments == null)
			arguments = new QueryExecutorArguments();
		if(Boolean.TRUE.equals(arguments.getIsProcessableAsAuditByDates()))
			return countAudits(tupleClass, arguments);
		if(arguments.getQuery() == null) {
			String queryIdentifier = QueryHelper.getIdentifierCountAll(tupleClass);
			arguments.setQuery(QueryGetter.getInstance().get(queryIdentifier));
			if(arguments.getQuery() == null)
				arguments.setQuery(QueryGetter.getInstance().getByCount(tupleClass, QueryName.COUNT.getValue(),String.format("SELECT COUNT(tuple) FROM %s tuple"
						,PersistenceHelper.getEntityName(tupleClass))));
		}
		return QueryExecutor.getInstance().executeCount(arguments);
	}
	
	protected <T> Long countAudits(Class<T> tupleClass,QueryExecutorArguments arguments) {
		return AuditCounter.getInstance().count(tupleClass,new Arguments<T>().setIsReadableByDates(Boolean.TRUE).setQueryExecutorArguments(arguments));
	}
	
	@Override
	public Long count(Class<?> tupleClass,String queryIdentifier,Object...filterFieldsValues) {
		validatePreconditions(tupleClass);
		return count(tupleClass,new QueryExecutorArguments().setQueryFromIdentifier(queryIdentifier).addFilterFieldsValues(filterFieldsValues));
	}
	
	@Override
	public Long count(Class<?> tupleClass) {
		validatePreconditions(tupleClass);
		return count(tupleClass,null);
	}
	
	/**/
	
	protected static void validatePreconditions(Class<?> tupleClass) {
		if(tupleClass == null)
			throw new RuntimeException("Tuple class to be count is required");
	}
	
}