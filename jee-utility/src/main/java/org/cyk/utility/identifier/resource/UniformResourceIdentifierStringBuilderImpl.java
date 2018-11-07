package org.cyk.utility.identifier.resource;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.request.RequestProperty;
import org.cyk.utility.request.RequestPropertyValueGetter;
import org.cyk.utility.string.AbstractStringFunctionImpl;
import org.cyk.utility.string.StringConstant;
import org.cyk.utility.string.StringFormat;

public class UniformResourceIdentifierStringBuilderImpl extends AbstractStringFunctionImpl implements UniformResourceIdentifierStringBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Object request;
	private Object context;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getFormat(Boolean.TRUE).setValue(FORMAT);
	}
	
	@Override
	protected StringFormat __getFormat__(StringFormat format) {
		String scheme = __injectStringHelper__().getString(getScheme());
		if(__injectStringHelper__().isBlank(scheme)) {
			setScheme(scheme = __getRequestProperty__(RequestProperty.SCHEME));
		}
		
		String host = __injectStringHelper__().getString(getHost());
		if(__injectStringHelper__().isBlank(host)) {
			setHost(host = __getRequestProperty__(RequestProperty.HOST));
		}

		String port = __injectStringHelper__().getString(getPort());
		if(__injectStringHelper__().isBlank(port)) {
			setPort(port = __getRequestProperty__(RequestProperty.PORT));
		}
		
		String context = __injectStringHelper__().getString(getContext());
		if(__injectStringHelper__().isBlank(context)) {
			setContext(context = __getRequestProperty__(RequestProperty.CONTEXT));
		}
		
		String path = __injectValueHelper__().defaultToIfNull(__injectStringHelper__().getString(getPath()), StringConstant.EMPTY) ;
		
		if(__injectStringHelper__().isNotBlank(context))
			path = context + CharacterConstant.SLASH + path;
		
		while(StringUtils.contains(path, "//"))
			path = StringUtils.replace((String)path, "//", "/");
		path = __injectStringHelper__().removeToBeginIfDoesStartWith(path, CharacterConstant.SLASH);
		
		setPath(path);
		return super.__getFormat__(format);
	}
	
	protected String __getRequestProperty__(RequestProperty property) {
		return __injectStringHelper__().getString(__inject__(RequestPropertyValueGetter.class).setRequest(getRequest()).setProperty(property).execute().getOutput());
	}
	
	@Override
	public UniformResourceIdentifierStringBuilder setFormatArguments(Object... arguments) {
		getFormat(Boolean.TRUE).setArguments(arguments);
		return this;
	}

	@Override
	public UniformResourceIdentifierStringBuilder setScheme(Object scheme) {
		setFormatArguments(FORMAT_ARGUMENT_SCHEME,scheme);
		return this;
	}
	
	@Override
	public Object getScheme() {
		StringFormat stringFormat = getFormat();
		if(stringFormat!=null)
			return stringFormat.getArgument(FORMAT_ARGUMENT_SCHEME);
		return null;
	}

	@Override
	public UniformResourceIdentifierStringBuilder setHost(Object host) {
		setFormatArguments(FORMAT_ARGUMENT_HOST,host);
		return this;
	}
	
	@Override
	public Object getHost() {
		StringFormat stringFormat = getFormat();
		if(stringFormat!=null)
			return stringFormat.getArgument(FORMAT_ARGUMENT_HOST);
		return null;
	}

	@Override
	public UniformResourceIdentifierStringBuilder setPort(Object port) {
		setFormatArguments(FORMAT_ARGUMENT_PORT,port);
		return this;
	}
	
	@Override
	public Object getPort() {
		StringFormat stringFormat = getFormat();
		if(stringFormat!=null)
			return stringFormat.getArgument(FORMAT_ARGUMENT_PORT);
		return null;
	}

	@Override
	public UniformResourceIdentifierStringBuilder setPath(Object path) {
		setFormatArguments(FORMAT_ARGUMENT_PATH,path);
		return this;
	}
	
	@Override
	public Object getPath() {
		StringFormat stringFormat = getFormat();
		if(stringFormat!=null)
			return stringFormat.getArgument(FORMAT_ARGUMENT_PATH);
		return null;
	}
	
	@Override
	public Object getContext() {
		return context;
	}
	
	@Override
	public UniformResourceIdentifierStringBuilder setContext(Object context) {
		this.context = context;
		return this;
	}
	
	@Override
	public Object getRequest() {
		return request;
	}
	
	@Override
	public UniformResourceIdentifierStringBuilder setRequest(Object request) {
		this.request = request;
		return this;
	}
	
}
