package org.cyk.utility.persistence.server.query.executor;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.persistence.query.QueryName;

public interface DynamicOneExecutor extends OneBasedExecutor {

	public static abstract class AbstractImpl extends OneBasedExecutor.AbstractImpl implements DynamicOneExecutor,Serializable {	
		@Override
		protected String getReadQueryIdentifier(Class<?> klass, QueryExecutorArguments arguments) {
			return QueryIdentifierBuilder.getInstance().build(klass, QueryName.READ_DYNAMIC_ONE);
		}
	}
	
	/**/
	
	static DynamicOneExecutor getInstance() {
		return Helper.getInstance(DynamicOneExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}