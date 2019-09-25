package org.cyk.utility.throwable;

import org.cyk.utility.__kernel__.throwable.SystemException;
import org.cyk.utility.helper.Helper;

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
	
	void throwRuntimeExceptionIfNull(Object value,String message);
	void throwRuntimeExceptionIfEmpty(Object value,String message);
	void throwRuntimeExceptionIfBlank(Object value,String message);
	
	/**/
	
	RuntimeException IMPLEMENTATION_OR_RUNNABLE_REQUIRED = new RuntimeException("Implementation or runnable required");
}
