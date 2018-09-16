package org.cyk.utility.client.controller.proxy;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.value.ValueHelper;

public abstract class AbstractProxyGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements ProxyGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getLog(Boolean.TRUE).setLevel(LogLevel.TRACE);
	}
	
	@Override
	protected Object __execute__() throws Exception {
		String uri = getUri();
		if(__injectStringHelper__().isBlank(uri)) {
			Object scheme;
			Object host;
			Object port;	
			String context;
			Object request = getRequest();
			if(request==null) {
				scheme = getScheme();
				host = getHost();
				port = getPort();	
				context = getContext();
			}else {
				if(request instanceof HttpServletRequest) {
					HttpServletRequest httpServletRequest = (HttpServletRequest) request;
					scheme = httpServletRequest.getScheme();
					host = httpServletRequest.getServerName();
					port = httpServletRequest.getServerPort();	
					context = httpServletRequest.getContextPath();
				}else {
					//TODO log warning
					scheme = null;
					host = null;
					port = null;	
					context = null;	
				}
			}
			
			scheme = __inject__(ValueHelper.class).defaultToIfNull(scheme,"http");
			host = __inject__(ValueHelper.class).defaultToIfNull(host,"localhost");
			port = __inject__(ValueHelper.class).defaultToIfNull(port,"80");	
			context = __inject__(ValueHelper.class).defaultToIfNull(context,"");
			if(StringUtils.startsWith(context, "/"))
				context = StringUtils.substring(context, 1);
			
			if(StringUtils.endsWith(context, "/client"))
				context = StringUtils.replace(context, "/client", "/server");
				
			uri = String.format("%s://%s:%s/%s", scheme,host,port,context);
		}
		
		if(__injectStringHelper__().isBlank(uri))
			__injectThrowableHelper__().throwRuntimeException(getClass()+" : uri is required");
		Class<?> aClass = getClazz();
		if(aClass == null)
			__injectThrowableHelper__().throwRuntimeException(getClass()+" : class is required");
		
		getLog(Boolean.TRUE).getMessageBuilder(Boolean.TRUE).addParameter("class", aClass).addParameter("uri", uri);
		
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
	
	@Override
	public Object getScheme() {
		return getProperties().getScheme();
	}

	@Override
	public ProxyGetter setScheme(Object scheme) {
		getProperties().setScheme(scheme);
		return this;
	}
	
	@Override
	public Object getHost() {
		return getProperties().getHost();
	}

	@Override
	public ProxyGetter setHost(Object host) {
		getProperties().setHost(host);
		return this;
	}

	@Override
	public Object getPort() {
		return getProperties().getPort();
	}
	
	@Override
	public ProxyGetter setPort(Object port) {
		getProperties().setPort(port);
		return this;
	}
	
	@Override
	public ProxyGetter setContext(String context) {
		getProperties().setContext(context);
		return this;
	}
	
	@Override
	public String getContext() {
		return (String) getProperties().getContext();
	}

	@Override
	public Object getRequest() {
		return getProperties().getRequest();
	}
	
	@Override
	public ProxyGetter setRequest(Object request) {
		getProperties().setRequest(request);
		return this;
	}
	
	
}
