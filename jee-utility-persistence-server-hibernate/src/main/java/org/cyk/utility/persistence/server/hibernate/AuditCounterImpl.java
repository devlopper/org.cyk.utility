package org.cyk.utility.persistence.server.hibernate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.audit.Arguments;
import org.cyk.utility.persistence.server.audit.AuditClassGetter;
import org.cyk.utility.persistence.server.audit.AuditCounter;
import org.cyk.utility.persistence.server.query.executor.DynamicManyExecutor;

public class AuditCounterImpl extends AuditCounter.AbstractImpl implements Serializable {

	@Override
	protected <T> Long __countByIdentifiers__(Class<T> klass, Arguments<T> arguments, Collection<Object> identifiers) {
		throw new RuntimeException("Count audits by identifiers not yet implemented");
	}

	@Override
	protected <T> Long __countByDates__(Class<T> klass, Arguments<T> arguments, LocalDateTime fromDate,LocalDateTime toDate) {
		@SuppressWarnings("unchecked")
		Class<T> auditClass = (Class<T>) AuditClassGetter.getInstance().get(klass);
		if(auditClass == null)
			return null;
		QueryExecutorArguments queryExecutorArguments = arguments.getQueryExecutorArguments();
		if(queryExecutorArguments == null)
			queryExecutorArguments = new QueryExecutorArguments();
		AuditReaderImpl.prepareQueryExecutorArgumentsForReadByDates(klass, arguments, fromDate, toDate, queryExecutorArguments);
		queryExecutorArguments.setQuery(null);
		return DynamicManyExecutor.getInstance().count(auditClass, queryExecutorArguments);
	}
}