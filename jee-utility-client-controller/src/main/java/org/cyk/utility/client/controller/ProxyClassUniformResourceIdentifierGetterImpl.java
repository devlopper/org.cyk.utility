package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.net.URI;

import org.apache.commons.lang.StringUtils;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class ProxyClassUniformResourceIdentifierGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<URI> implements ProxyClassUniformResourceIdentifierGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected URI __execute__() throws Exception {
		Class<?> aClass = getClazz();
		if(aClass == null)
			__injectThrowableHelper__().throwRuntimeException(getClass()+" : class is required");
		String identifier = StringUtils.substringBefore(aClass.getName(), ".server")+".server"+".uri";
		String uriString = System.getProperty(identifier);
		if(__injectStringHelper__().isBlank(uriString))
			__injectThrowableHelper__().throwRuntimeException(getClass()+" : uniform resource identifier is required for "+identifier+". It must be defined under system properties");
		return URI.create(uriString);
	}
	
	@Override
	public Class<?> getClazz() {
		return (Class<?>) getProperties().getClazz();
	}

	@Override
	public ProxyClassUniformResourceIdentifierGetter setClazz(Class<?> aClass) {
		getProperties().setClass(aClass);
		return this;
	}

	@Override
	public ProxyClassUniformResourceIdentifierGetter execute(Class<?> aClass) {
		return setClazz(aClass).execute();
	}

	@Override
	public ProxyClassUniformResourceIdentifierGetter execute() {
		return (ProxyClassUniformResourceIdentifierGetter) super.execute();
	}

	
	
}
