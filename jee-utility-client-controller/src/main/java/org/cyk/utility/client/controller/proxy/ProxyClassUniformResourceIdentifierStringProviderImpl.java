package org.cyk.utility.client.controller.proxy;

import java.io.Serializable;

import org.cyk.utility.string.AbstractStringFunctionImpl;
@Deprecated
public class ProxyClassUniformResourceIdentifierStringProviderImpl extends AbstractStringFunctionImpl implements ProxyClassUniformResourceIdentifierStringProvider,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<?> aClass;
	private Object request;
	
	@Override
	protected String __execute__() throws Exception {
		return null;
	}
	
	@Override
	public Class<?> getClazz() {
		return aClass;
	}

	@Override
	public ProxyClassUniformResourceIdentifierStringProvider setClazz(Class<?> aClass) {
		this.aClass = aClass;
		return this;
	}
	
	@Override
	public Object getRequest() {
		return request;
	}
	@Override
	public ProxyClassUniformResourceIdentifierStringProvider setRequest(Object request) {
		this.request = request;
		return this;
	}
	
}
