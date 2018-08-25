package org.cyk.utility.client.controller.proxy;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public abstract class AbstractProxyGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements ProxyGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object __execute__() throws Exception {
		String uri = getUri();
		if(__injectStringHelper__().isBlank(uri))
			__injectThrowableHelper__().throwRuntimeException(getClass()+" : uri is required");
		Class<?> aClass = getClazz();
		if(aClass == null)
			__injectThrowableHelper__().throwRuntimeException(getClass()+" : class is required");
		return ____execute____(uri,aClass);
	}
	
	protected abstract Object ____execute____(String uri,Class<?> aClass) throws Exception;
	
	@Override
	public ProxyGetter execute(String uri,Class<?> aClass) {
		return setUri(uri).setClazz(aClass).execute();
	}
	
	@Override
	public ProxyGetter execute() {
		return (ProxyGetter) super.execute();
	}
	
	@Override
	public ProxyGetter setUri(String uri) {
		getProperties().setUri(uri);
		return this;
	}
	
	@Override
	public String getUri() {
		return (String) getProperties().getUri();
	}
	
	@Override
	public Class<?> getClazz() {
		return (Class<?>) getProperties().getClazz();
	}
	
	@Override
	public ProxyGetter setClazz(Class<?> aClass) {
		getProperties().setClass(aClass);
		return this;
	}
	
}
