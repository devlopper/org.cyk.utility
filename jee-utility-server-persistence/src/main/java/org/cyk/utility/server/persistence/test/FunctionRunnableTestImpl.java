package org.cyk.utility.server.persistence.test;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.test.Test;

public class FunctionRunnableTestImpl extends AbstractFunctionRunnableImpl<Test> implements FunctionRunnableTest,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public FunctionRunnableTest setRunnable(Runnable runnable) {
		return (FunctionRunnableTest) super.setRunnable(runnable);
	}
	
}
