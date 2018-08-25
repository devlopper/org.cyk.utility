package org.cyk.utility.method;

import java.lang.reflect.Method;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;

public interface MethodCallListener extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	MethodCallListener setObject(Object object);
	Object getObject();
	
	MethodCallListener setMethod(Method method);
	MethodCallListener setMethod(MethodName methodName);
	Method getMethod();
	
}
