package org.cyk.utility.persistence.server.query.executor;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.AbstractObject;

public interface Executor {

	public static abstract class AbstractImpl extends AbstractObject implements Executor,Serializable {
		
	}
	
}