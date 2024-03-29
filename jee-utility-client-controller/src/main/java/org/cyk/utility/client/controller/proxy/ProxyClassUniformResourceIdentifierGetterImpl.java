package org.cyk.utility.client.controller.proxy;

import java.io.Serializable;
import java.net.URI;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.OperatingSystemHelper;
import org.cyk.utility.__kernel__.system.SystemHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Deprecated
public class ProxyClassUniformResourceIdentifierGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<URI> implements ProxyClassUniformResourceIdentifierGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private ProxyClassUniformResourceIdentifierStringBuilder stringBuilder;
	
	@Override
	protected URI __execute__() throws Exception {
		URI uri = UNIFORM_RESOURCE_IDENTIFIER;
		if(uri == null) {
			Class<?> aClass = getClazz();
			String string = null;		
			if(StringHelper.isBlank(string)) {
				//1 - from application
				string = __inject__(ProxyClassUniformResourceIdentifierStringProvider.class).setClazz(aClass).execute().getOutput();
				
				if(StringHelper.isBlank(string)) {
					//2 - from server properties
					if(aClass == null)
						throw new RuntimeException(getClass()+" : class is required");
					String identifier = StringUtils.substringBefore(aClass.getName(), ".server")+".server"+".uri";
					string = SystemHelper.getProperty(identifier);
					if(StringHelper.isBlank(string)) {
						//3 - from operating system properties 
						string = OperatingSystemHelper.getProperty(identifier);
						if(StringHelper.isBlank(string)) {
							//4 - from request
							ProxyClassUniformResourceIdentifierStringBuilder stringBuilder = getStringBuilder(Boolean.TRUE);
							string = stringBuilder.execute().getOutput();
						}
					}
				}	
			}
			
			if(StringHelper.isNotBlank(string)) 
				uri = URI.create(string);	
		}
		
		/*
		Class<?> aClass = getClazz();
		if(aClass == null)
			throw new RuntimeException(getClass()+" : class is required");
		String identifier = StringUtils.substringBefore(aClass.getName(), ".server")+".server"+".uri";
		String uriString = __inject__(SystemHelper.class).getProperty(identifier,Boolean.TRUE);
		//if(StringHelper.isBlank(uriString)) 
		//	throw new RuntimeException(getClass()+" : uniform resource identifier is required for "+identifier+". It must be defined under system properties or environment variable");
		if(StringHelper.isNotBlank(uriString)) 
			uri = URI.create(uriString);
		*/
		
		return uri;
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

	@Override
	public ProxyClassUniformResourceIdentifierStringBuilder getStringBuilder() {
		return stringBuilder;
	}
	
	@Override
	public ProxyClassUniformResourceIdentifierStringBuilder getStringBuilder(Boolean injectIfNull) {
		if(stringBuilder == null && Boolean.TRUE.equals(injectIfNull))
			stringBuilder = __inject__(ProxyClassUniformResourceIdentifierStringBuilder.class);
		return stringBuilder;
	}

	@Override
	public ProxyClassUniformResourceIdentifierGetter setStringBuilder(ProxyClassUniformResourceIdentifierStringBuilder stringBuilder) {
		this.stringBuilder = stringBuilder;
		return this;
	}
	
	public static final String FIELD_STRING_BUILDER = "stringBuilder";
	
	public static URI UNIFORM_RESOURCE_IDENTIFIER = null;
	
}
