package org.cyk.utility.persistence.server.query.executor;

import java.io.Serializable;

import org.cyk.utility.persistence.query.QueryExecutorArguments;

public interface OneBasedExecutor extends Executor {

	<T> T read(Class<T> klass,QueryExecutorArguments arguments);
	
	public static abstract class AbstractImpl extends Executor.AbstractImpl implements OneBasedExecutor,Serializable {
		
	}
}