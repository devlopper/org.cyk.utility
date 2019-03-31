package org.cyk.utility.throwable;

import org.cyk.utility.helper.Helper;
import org.cyk.utility.system.exception.SystemException;

public interface ThrowableHelper extends Helper {

	Throwable getInstanceOf(Throwable throwable, Class<?> aClass);

	Throwable getInstanceOf(Throwable throwable, Class<?> aClass, Class<?>...classes);

	Throwable getFirstCause(Throwable throwable);
	//Throwable getLastCause(Throwable throwable);

	void throw_(RuntimeException runtimeException);
	void throw_(SystemException systemException);
	void throwRuntimeException(String message);
	void throwRuntimeExceptionNotYetImplemented(String name);
	void throwRuntimeExceptionNotYetImplemented();
	void throwRuntimeExceptionImplementationOrRunnableRequired(Class<?> aClass);
	/**/
	
	RuntimeException IMPLEMENTATION_OR_RUNNABLE_REQUIRED = new RuntimeException("Implementation or runnable required");
}
