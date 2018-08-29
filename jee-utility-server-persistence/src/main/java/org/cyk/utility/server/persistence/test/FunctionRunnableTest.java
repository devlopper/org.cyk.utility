package org.cyk.utility.server.persistence.test;

import org.cyk.utility.__kernel__.function.FunctionRunnable;
import org.cyk.utility.test.Test;

public interface FunctionRunnableTest extends FunctionRunnable<Test> {

	@Override FunctionRunnableTest setRunnable(Runnable runnable);
	
}
