package org.cyk.utility.client.controller.proxy;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Deprecated
public class ProxyGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements ProxyGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private ProxyClassUniformResourceIdentifierStringBuilder classUniformResourceIdentifierString;
	
	@Override
	public Object getProxyClassUniformResourceIdentifierStringBuilderRequest() {
		ProxyClassUniformResourceIdentifierStringBuilder proxyClassUniformResourceIdentifierStringBuilder = getClassUniformResourceIdentifierString();
		return proxyClassUniformResourceIdentifierStringBuilder == null ? null : proxyClassUniformResourceIdentifierStringBuilder.getRequest();
	}
	
	@Override
	public ProxyGetter setProxyClassUniformResourceIdentifierStringBuilderRequest(Object proxyClassUniformResourceIdentifierStringBuilderRequest) {
		getClassUniformResourceIdentifierString(Boolean.TRUE).setRequest(proxyClassUniformResourceIdentifierStringBuilderRequest);
		return this;
	}
	
	@Override
	public ProxyClassUniformResourceIdentifierStringBuilder getClassUniformResourceIdentifierString() {
		return classUniformResourceIdentifierString;
	}
	
	@Override
	public ProxyClassUniformResourceIdentifierStringBuilder getClassUniformResourceIdentifierString(Boolean injectIfNull) {
		if(classUniformResourceIdentifierString == null && Boolean.TRUE.equals(injectIfNull))
			classUniformResourceIdentifierString = __inject__(ProxyClassUniformResourceIdentifierStringBuilder.class);
		return classUniformResourceIdentifierString;
	}
	
	@Override
	public ProxyGetter setClassUniformResourceIdentifierString(ProxyClassUniformResourceIdentifierStringBuilder classUniformResourceIdentifierString) {
		this.classUniformResourceIdentifierString = classUniformResourceIdentifierString;
		return this;
	}
	
	@Override
	public ProxyGetter setClassUniformResourceIdentifierStringRequest(Object request) {
		getClassUniformResourceIdentifierString(Boolean.TRUE).setRequest(request);
		return this;
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
	
	public static final String FIELD_CLASS_UNIFORM_RESOURCE_IDENTIFIER_STRING = "classUniformResourceIdentifierString";
	
}
