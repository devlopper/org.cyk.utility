package org.cyk.utility.persistence.server.query.executor.field;

import java.io.Serializable;

import org.cyk.utility.persistence.server.query.executor.Executor;

public interface FieldBasedExecutor extends Executor {

	public static abstract class AbstractImpl extends Executor.AbstractImpl implements FieldBasedExecutor,Serializable {
		
	}
}