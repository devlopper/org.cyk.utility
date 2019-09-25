package org.cyk.utility.identifier.resource;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

import javax.enterprise.context.Dependent;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.identifier.resource.RequestProperty;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.map.MapHelper;
import org.cyk.utility.object.ObjectByObjectMap;
import org.cyk.utility.request.RequestPropertyValueGetter;
import org.cyk.utility.string.AbstractStringFunctionImpl;
import org.cyk.utility.string.StringByStringMap;
import org.cyk.utility.string.StringFormat;

@Dependent @Deprecated
public class UniformResourceIdentifierStringBuilderImpl extends AbstractStringFunctionImpl implements UniformResourceIdentifierStringBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Object request;
	private Object context;
	private String string;
	private ObjectByObjectMap parameterMap;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getFormat(Boolean.TRUE).setValue(FORMAT);
	}
	
	@Override
	protected StringFormat __getFormat__(StringFormat format) {
		String string = getString();
		String scheme = StringHelper.get(getScheme());
		if(StringHelper.isBlank(scheme))
			setScheme(scheme = __getRequestProperty__(RequestProperty.SCHEME,string));
		
		String host = StringHelper.get(getHost());
		if(StringHelper.isBlank(host))
			setHost(host = __getRequestProperty__(RequestProperty.HOST,string));

		String port = StringHelper.get(getPort());
		if(StringHelper.isBlank(port))
			setPort(port = __getRequestProperty__(RequestProperty.PORT,string));
		
		setFormatArguments(FORMAT_ARGUMENT_HOST_PORT_SEPARATOR,port == null ? ConstantEmpty.STRING : ConstantCharacter.COLON);
		
		String context = StringHelper.get(getContext());
		if(StringHelper.isBlank(context))
			setContext(context = __getRequestProperty__(RequestProperty.CONTEXT,string));
		
		Object pathObject = getPath();
		if(pathObject == null && StringHelper.isNotBlank(string))
			setPath(pathObject = __getRequestProperty__(RequestProperty.PATH,string));
		String path = __injectValueHelper__().defaultToIfNull(StringHelper.get(getPath()), ConstantEmpty.STRING) ;
		
		if(StringHelper.isNotBlank(context))
			path = context + ConstantCharacter.SLASH + path;
		
		while(StringUtils.contains(path, "//"))
			path = StringUtils.replace((String)path, "//", "/");
		path = StringHelper.removeToBeginIfDoesStartWith(path, ConstantCharacter.SLASH);
		
		ObjectByObjectMap parameterMap = getParameterMap();
		if(Boolean.TRUE.equals(__inject__(MapHelper.class).isNotEmpty(parameterMap))) {
			StringByStringMap finalParameterMap = __inject__(StringByStringMap.class).setIsSequential(Boolean.TRUE).setKeyValueSeparator(ConstantCharacter.EQUAL)
					.setEntrySeparator(ConstantCharacter.AMPERSTAMP);
			for(Map.Entry<Object, Object> index : parameterMap.getEntries()) {
				String name = null;
				if(index.getKey()!=null) {
					if(index.getKey() instanceof UniformResourceIdentifierParameterNameStringBuilder)
						name = ((UniformResourceIdentifierParameterNameStringBuilder)index.getKey()).execute().getOutput();
					else
						name = index.getKey().toString();
				}
				if(StringHelper.isNotBlank(name)) {
					String value = null;
					if(index.getValue()!=null) {
						if(index.getValue() instanceof UniformResourceIdentifierParameterValueStringBuilder)
							value = ((UniformResourceIdentifierParameterValueStringBuilder)index.getValue()).execute().getOutput();
						else
							value = index.getValue().toString();
					}
					
					if(StringHelper.isNotBlank(value)) {
						finalParameterMap.set(name,value);	
					}
				}
			}
			path = path + ConstantCharacter.QUESTION_MARK.toString() + finalParameterMap.getRepresentationAsString();
		}
		
		setPath(path);
		
		String query = StringHelper.get(getQuery());
		if(StringHelper.isBlank(query))
			setQuery(query = __injectValueHelper__().defaultToIfNull(__getRequestProperty__(RequestProperty.QUERY,string),ConstantEmpty.STRING));
		
		setFormatArguments(FORMAT_ARGUMENT_PATH_QUERY_SEPARATOR,Boolean.TRUE.equals(StringHelper.isBlank(query)) ? ConstantEmpty.STRING : ConstantCharacter.QUESTION_MARK);
		
		return super.__getFormat__(format);
	}
	
	protected String __getRequestProperty__(RequestProperty property,String string) {
		String value = null;
		if(property!=null) {
			if(StringHelper.isBlank(string)) {
				Object request = getRequest();
				if(request!=null)
					value = StringHelper.get(__inject__(RequestPropertyValueGetter.class).setRequest(request).setProperty(property).execute().getOutput());
			}else {
				URI uri = URI.create(string);
				if(uri!=null) {
					switch(property) {
					case SCHEME: value = uri.getScheme(); break;
					case HOST: value = uri.getHost(); break;
					case PORT: 
						Integer port = uri.getPort();
						value = port == -1 ? null : String.valueOf(port);
						break;
					case PATH: value = uri.getPath();break;
					case QUERY: 
						String query = uri.getQuery();
						value = StringHelper.isBlank(query) ? ConstantEmpty.STRING : query;
						break;
					default: value = null;
					}		
				}
			}
		}
		return value;
	}
	
	protected String __getRequestProperty__(RequestProperty property) {
		return __getRequestProperty__(property, null);
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
		setFormatArguments(FORMAT_ARGUMENT_PORT,port == null ? ConstantEmpty.STRING : port);
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
	public Object getQuery() {
		StringFormat stringFormat = getFormat();
		if(stringFormat!=null)
			return stringFormat.getArgument(FORMAT_ARGUMENT_QUERY);
		return null;
	}
	
	@Override
	public UniformResourceIdentifierStringBuilder setQuery(Object query) {
		setFormatArguments(FORMAT_ARGUMENT_QUERY,query);
		return this;
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
	
	@Override
	public String getString() {
		return string;
	}
	
	public UniformResourceIdentifierStringBuilder setString(String string) {
		this.string = string;
		return this;
	}
	
	@Override
	public ObjectByObjectMap getParameterMap() {
		return parameterMap;
	}
	
	@Override
	public UniformResourceIdentifierStringBuilder setParameterMap(ObjectByObjectMap parameterMap) {
		this.parameterMap = parameterMap;
		return this;
	}
	
	@Override
	public ObjectByObjectMap getParameterMap(Boolean injectIfNull) {
		ObjectByObjectMap map = (ObjectByObjectMap) __getInjectIfNull__(FIELD_PARAMETER_MAP, injectIfNull);
		map.setIsSequential(Boolean.TRUE);
		return map;
	}
	
	@Override
	public UniformResourceIdentifierStringBuilder setParameters(Object... keyValues) {
		getParameterMap(Boolean.TRUE).set(keyValues);
		return this;
	}
	
	public static final String FIELD_PARAMETER_MAP = "parameterMap";
	
}
