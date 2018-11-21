package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.net.URI;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.client.controller.proxy.ProxyClassUniformResourceIdentifierGetter;
import org.cyk.utility.client.controller.proxy.ProxyGetter;
import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.internationalization.InternalizationStringBuilder;
import org.cyk.utility.type.TypeHelper;

public abstract class AbstractObject extends org.cyk.utility.__kernel__.object.dynamic.AbstractObject implements Objectable,Serializable {
	private static final long serialVersionUID = 1L;

	/**/
	
	protected static InstanceHelper __injectInstanceHelper__() {
		return __inject__(InstanceHelper.class);
	}
	
	protected static InternalizationStringBuilder __injectInternalizationStringBuilder__() {
		return __inject__(InternalizationStringBuilder.class);
	}
	
	/* URI */
	
	protected static ProxyClassUniformResourceIdentifierGetter __injectProxyClassUniformResourceIdentifierGetter__() {
		return __inject__(ProxyClassUniformResourceIdentifierGetter.class);
	}
	
	protected static ProxyClassUniformResourceIdentifierGetter __injectProxyClassUniformResourceIdentifierGetter__(Class<?> aClass) {
		return __injectProxyClassUniformResourceIdentifierGetter__().setClazz(aClass);
	}
	
	protected static String __getUri__(Class<?> aClass) {
		String string = null;
		URI uri = __injectProxyClassUniformResourceIdentifierGetter__(aClass).execute().getOutput();
		if(uri!=null)
			string = uri.toString();
		return string;
	}
	
	/* Proxy */
	
	protected static ProxyGetter __injectProxyGetter__() {
		return __inject__(ProxyGetter.class);
	}
	
	protected static ProxyGetter __injectProxyGetter__(Object request,String uri,Class<?> aClass) {
		return __injectProxyGetter__().setRequest(request).setUri(uri).setClazz(aClass);
	}
	
	@SuppressWarnings("unchecked")
	protected static <T> T __getProxy__(Class<T> aClass,Object request,String uri) {
		return (T) __injectProxyGetter__(request,uri, aClass).execute().getOutput();
	}
	
	protected static <T> T __getProxyByUri__(Class<T> aClass,String uri) {
		return (T) __getProxy__(aClass,null,uri);
	}
	
	protected static <T> T __getProxyByRequest__(Class<T> aClass,Object request) {
		return (T) __getProxy__(aClass,request,null);
	}
	
	protected static <T> T __getProxy__(Class<T> aClass) {
		return __getProxy__(aClass,null,null);
	}
	
	/* Response */
	
	@SuppressWarnings("unchecked")
	protected static <T> Collection<T> __readEntityAsCollection__(Response response,Class<T> aClass) {
		return (Collection<T>) response.readEntity(__inject__(TypeHelper.class).instanciateGenericCollectionParameterizedTypeForJaxrs(Collection.class,aClass));
	}
	
	/**/
	
	protected static ControllerLayer __injectControllerLayer__() {
		return __inject__(ControllerLayer.class);
	}
	
	
}
