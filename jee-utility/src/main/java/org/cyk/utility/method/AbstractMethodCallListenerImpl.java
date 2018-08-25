package org.cyk.utility.method;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;

public abstract class AbstractMethodCallListenerImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements MethodCallListener,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public MethodCallListener setObject(Object object) {
		getProperties().setObject(object);
		return this;
	}

	@Override
	public Object getObject() {
		return getProperties().getObject();
	}

	@Override
	public MethodCallListener setMethod(Method method) {
		getProperties().setMethod(method);
		return this;
	}
	
	@Override
	public MethodCallListener setMethod(MethodName methodName) {
		Object object = getObject();
		if(object!=null) {
			Collection<Method> methods = __inject__(MethodGetter.class).setClazz(object.getClass()).setToken(methodName.name()).execute().getOutput();
			setMethod(__injectCollectionHelper__().getFirst(methods));
		}
		return this;
	}

	@Override
	public Method getMethod() {
		return (Method) getProperties().getMethod();
	}

}
