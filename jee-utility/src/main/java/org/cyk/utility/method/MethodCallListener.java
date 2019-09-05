package org.cyk.utility.method;

import java.lang.reflect.Method;

@Deprecated
public interface MethodCallListener {

	MethodCallListener setObject(Object object);
	Object getObject();
	
	MethodCallListener setMethod(Method method);
	MethodCallListener setMethod(MethodName methodName);
	Method getMethod();
	
	void execute();
}
