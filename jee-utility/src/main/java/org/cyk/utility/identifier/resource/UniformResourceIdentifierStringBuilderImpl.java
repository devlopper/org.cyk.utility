package org.cyk.utility.identifier.resource;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.map.MapHelper;
import org.cyk.utility.object.ObjectByObjectMap;
import org.cyk.utility.request.RequestProperty;
import org.cyk.utility.request.RequestPropertyValueGetter;
import org.cyk.utility.string.AbstractStringFunctionImpl;
import org.cyk.utility.string.StringByStringMap;
import org.cyk.utility.string.StringConstant;
import org.cyk.utility.string.StringFormat;

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
		String scheme = __injectStringHelper__().getString(getScheme());
		if(__injectStringHelper__().isBlank(scheme))
			setScheme(scheme = __getRequestProperty__(RequestProperty.SCHEME,string));
		
		String host = __injectStringHelper__().getString(getHost());
		if(__injectStringHelper__().isBlank(host))
			setHost(host = __getRequestProperty__(RequestProperty.HOST,string));

		String port = __injectStringHelper__().getString(getPort());
		if(__injectStringHelper__().isBlank(port))
			setPort(port = __getRequestProperty__(RequestProperty.PORT,string));
		
		String context = __injectStringHelper__().getString(getContext());
		if(__injectStringHelper__().isBlank(context))
			setContext(context = __getRequestProperty__(RequestProperty.CONTEXT,string));
		
		Object pathObject = getPath();
		if(pathObject == null && __injectStringHelper__().isNotBlank(string))
			setPath(pathObject = __getRequestProperty__(RequestProperty.PATH,string));
		String path = __injectValueHelper__().defaultToIfNull(__injectStringHelper__().getString(getPath()), StringConstant.EMPTY) ;
		
		if(__injectStringHelper__().isNotBlank(context))
			path = context + CharacterConstant.SLASH + path;
		
		while(StringUtils.contains(path, "//"))
			path = StringUtils.replace((String)path, "//", "/");
		path = __injectStringHelper__().removeToBeginIfDoesStartWith(path, CharacterConstant.SLASH);
		
		ObjectByObjectMap parameterMap = getParameterMap();
		if(Boolean.TRUE.equals(__inject__(MapHelper.class).isNotEmpty(parameterMap))) {
			StringByStringMap finalParameterMap = __inject__(StringByStringMap.class).setIsSequential(Boolean.TRUE).setKeyValueSeparator(CharacterConstant.EQUAL)
					.setEntrySeparator(CharacterConstant.AMPERSTAMP);
			for(Map.Entry<Object, Object> index : parameterMap.getEntries()) {
				String name = null;
				if(index.getKey()!=null) {
					if(index.getKey() instanceof UniformResourceIdentifierParameterNameStringBuilder)
						name = ((UniformResourceIdentifierParameterNameStringBuilder)index.getKey()).execute().getOutput();
					else
						name = index.getKey().toString();
				}
				if(__injectStringHelper__().isNotBlank(name)) {
					String value = null;
					if(index.getValue()!=null) {
						if(index.getValue() instanceof UniformResourceIdentifierParameterValueStringBuilder)
							value = ((UniformResourceIdentifierParameterValueStringBuilder)index.getValue()).execute().getOutput();
						else
							value = index.getValue().toString();
					}
					
					if(__injectStringHelper__().isNotBlank(value)) {
						finalParameterMap.set(name,value);	
					}
				}
			}
			path = path + CharacterConstant.QUESTION_MARK.toString() + finalParameterMap.getRepresentationAsString();
		}
		
		setPath(path);
		return super.__getFormat__(format);
	}
	
	protected String __getRequestProperty__(RequestProperty property,String string) {
		String value = null;
		if(property!=null) {
			if(__injectStringHelper__().isBlank(string)) {
				Object request = getRequest();
				if(request!=null)
					value = __injectStringHelper__().getString(__inject__(RequestPropertyValueGetter.class).setRequest(request).setProperty(property).execute().getOutput());
			}else {
				URI uri = URI.create(string);
				if(uri!=null) {
					switch(property) {
					case SCHEME: value = uri.getScheme(); break;
					case HOST: value = uri.getHost(); break;
					case PORT: value = String.valueOf(uri.getPort()); break;
					case PATH: value = uri.getPath();break;
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
