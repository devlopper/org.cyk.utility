package org.cyk.utility.persistence.server.query.executor;

import java.io.Serializable;

import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryExecutor;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryType;

public interface OneBasedExecutor extends Executor {

	<T> T read(Class<T> klass,QueryExecutorArguments arguments);
	
	public static abstract class AbstractImpl extends Executor.AbstractImpl implements OneBasedExecutor,Serializable {
		
		@Override
		public <T> T read(Class<T> klass, QueryExecutorArguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("read one executor klass", klass);
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
			Query query = new Query().setType(QueryType.READ_ONE).setTupleClass(klass).setResultClass(klass);
			arguments.setQuery(query);
		}
		
		protected String getReadQueryIdentifier(Class<?> klass, QueryExecutorArguments arguments) {
			return null;
		}
		
		protected <T> T __read__(Class<T> klass, QueryExecutorArguments arguments) {
			return QueryExecutor.getInstance().executeReadOne(klass, arguments);
		}
		
		/**/
		
		private static void validateQuery(QueryExecutorArguments arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("one executor query", arguments.getQuery());
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("one executor query identifier", arguments.getQuery().getIdentifier());
		}
	}
}