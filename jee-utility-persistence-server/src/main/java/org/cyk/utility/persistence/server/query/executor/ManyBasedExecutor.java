package org.cyk.utility.persistence.server.query.executor;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryExecutor;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryType;

public interface ManyBasedExecutor extends Executor {

	/* read */
	
	<T> Collection<T> read(Class<T> klass,QueryExecutorArguments arguments);
	<T> Collection<T> read(Class<T> klass);
	
	/* count */
	
	Long count(Class<?> klass,QueryExecutorArguments arguments);
	Long count(Class<?> klass);
	
	public static abstract class AbstractImpl extends Executor.AbstractImpl implements ManyBasedExecutor,Serializable {
		
		/* read */
		
		@Override
		public <T> Collection<T> read(Class<T> klass, QueryExecutorArguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("read many executor klass", klass);
			if(arguments == null)
				arguments = new QueryExecutorArguments();
			if(arguments.getQuery() == null)
				setReadQuery(klass, arguments);
			if(StringHelper.isBlank(arguments.getQuery().getIdentifier()))
				arguments.getQuery().setIdentifier(getReadQueryIdentifier(klass, arguments));
			validateQuery(arguments);
			return __read__(klass, arguments);
		}
		
		protected void setReadQuery(Class<?> klass,QueryExecutorArguments arguments) {
			Query query = new Query().setType(QueryType.READ_MANY).setTupleClass(klass).setResultClass(klass);
			arguments.setQuery(query);
		}
		
		@Override
		public <T> Collection<T> read(Class<T> klass) {
			return read(klass, null);
		}
		
		protected String getReadQueryIdentifier(Class<?> klass, QueryExecutorArguments arguments) {
			return null;
		}
		
		protected <T> Collection<T> __read__(Class<T> klass, QueryExecutorArguments arguments) {
			return QueryExecutor.getInstance().executeReadMany(klass, arguments);
			//throw new RuntimeException(String.format("read many of %s not yet implemented",arguments.getQuery().getIdentifier()));
		}
		
		/* count */
		
		@Override
		public Long count(Class<?> klass,QueryExecutorArguments arguments) {
			if(arguments == null)
				arguments = new QueryExecutorArguments();
			if(arguments.getQuery() == null)
				setCountQuery(klass, arguments);
			if(StringHelper.isBlank(arguments.getQuery().getIdentifier()))
				arguments.getQuery().setIdentifier(getCountQueryIdentifier(klass,arguments));
			validateQuery(arguments);
			return __count__(klass,arguments);
		}
		
		protected void setCountQuery(Class<?> klass,QueryExecutorArguments arguments) {
			Query query = new Query().setType(QueryType.COUNT).setTupleClass(klass).setResultClass(Long.class);
			arguments.setQuery(query);
		}
		
		@Override
		public Long count(Class<?> klass) {
			return count(klass, null);
		}
		
		protected String getCountQueryIdentifier(Class<?> klass,QueryExecutorArguments arguments) {
			return null;
		}
		
		protected Long __count__(Class<?> klass,QueryExecutorArguments arguments) {
			throw new RuntimeException(String.format("count of %s not yet implemented",arguments.getQuery().getIdentifier()));
		}
		
		/**/
		
		private static void validateQuery(QueryExecutorArguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("many executor query", arguments.getQuery());
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("many executor query identifier", arguments.getQuery().getIdentifier());
		}
	}	
}