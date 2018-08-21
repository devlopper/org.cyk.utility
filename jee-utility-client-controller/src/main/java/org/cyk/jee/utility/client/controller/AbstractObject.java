package org.cyk.jee.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.cyk.utility.instance.InstanceHelper;

public abstract class AbstractObject extends org.cyk.utility.__kernel__.object.dynamic.AbstractObject implements Objectable,Serializable {
	private static final long serialVersionUID = 1L;

	/**/
	
	protected static InstanceHelper __injectInstanceHelper__() {
		return __inject__(InstanceHelper.class);
	}
	
	/* URI */
	
	protected static ProxyClassUniformResourceIdentifierGetter __injectProxyClassUniformResourceIdentifierGetter__() {
		return __inject__(ProxyClassUniformResourceIdentifierGetter.class);
	}
	
	protected static ProxyClassUniformResourceIdentifierGetter __injectProxyClassUniformResourceIdentifierGetter__(Class<?> aClass) {
		return __injectProxyClassUniformResourceIdentifierGetter__().setClazz(aClass);
	}
	
	protected static String __getUri__(Class<?> aClass) {
		return __injectProxyClassUniformResourceIdentifierGetter__().execute(aClass).getOutput().toString();
	}
	
	/* Proxy */
	
	protected static ProxyGetter __injectProxyGetter__() {
		return __inject__(ProxyGetter.class);
	}
	
	protected static ProxyGetter __injectProxyGetter__(String uri,Class<?> aClass) {
		return __injectProxyGetter__().setUri(uri).setClazz(aClass);
	}
	
	@SuppressWarnings("unchecked")
	protected static <T> T __getProxy__(Class<T> aClass,String uri) {
		return (T) __injectProxyGetter__(uri, aClass).execute().getOutput();
	}
	
	protected static <T> T __getProxy__(Class<T> aClass) {
		return __getProxy__(aClass,__getUri__(aClass));
	}
	
	/* Response */
	
	protected static <T> Collection<T> __readEntityAsCollection__(Response response,Class<T> aClass) {
		return response.readEntity(new GenericType<Collection<T>>(){});
	}
}
