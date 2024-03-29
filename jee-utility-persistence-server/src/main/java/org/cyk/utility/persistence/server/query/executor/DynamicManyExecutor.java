package org.cyk.utility.persistence.server.query.executor;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.persistence.query.QueryName;

public interface DynamicManyExecutor extends ManyBasedExecutor {

	public static abstract class AbstractImpl extends ManyBasedExecutor.AbstractImpl implements DynamicManyExecutor,Serializable {
		
		@Override
		protected String getReadQueryIdentifier(Class<?> klass, QueryExecutorArguments arguments) {
			return QueryIdentifierBuilder.getInstance().build(klass, QueryName.READ_DYNAMIC);
		}
		
		@Override
		protected String getCountQueryIdentifier(Class<?> klass,QueryExecutorArguments arguments) {
			return QueryIdentifierBuilder.getInstance().build(klass, QueryName.COUNT_DYNAMIC);
		}
		
	}
	
	/**/
	
	static DynamicManyExecutor getInstance() {
		return Helper.getInstance(DynamicManyExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}