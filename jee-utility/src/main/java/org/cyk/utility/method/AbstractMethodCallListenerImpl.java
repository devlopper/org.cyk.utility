package org.cyk.utility.method;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;

@Deprecated
public abstract class AbstractMethodCallListenerImpl implements MethodCallListener,Serializable {
	private static final long serialVersionUID = 1L;

	private Object object;
	private Method method;
	
	@Override
	public MethodCallListener setObject(Object object) {
		this.object = object;
		return this;
	}

	@Override
	public Object getObject() {
		return object;
	}

	@Override
	public MethodCallListener setMethod(Method method) {
		this.method = method;
		return this;
	}
	
	@Override
	public MethodCallListener setMethod(MethodName methodName) {
		Object object = getObject();
		if(object!=null) {
			Collection<Method> methods = DependencyInjection.inject(MethodGetter.class).setClazz(object.getClass()).setToken(methodName.name()).execute().getOutput();
			setMethod(CollectionHelper.getFirst(methods));
		}
		return this;
	}

	@Override
	public Method getMethod() {
		return method;
	}

}
