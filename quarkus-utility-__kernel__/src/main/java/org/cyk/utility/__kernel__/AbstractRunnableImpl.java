package org.cyk.utility.__kernel__;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public abstract class AbstractRunnableImpl extends AbstractObject implements Runnable,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void run() {
		try {
			__run__();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	protected abstract void __run__() throws Exception;

}
