package org.cyk.utility.client.controller.proxy;

import java.io.Serializable;
import java.net.URI;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.system.OperatingSystemHelper;
import org.cyk.utility.system.SystemHelper;

public class ProxyClassUniformResourceIdentifierGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<URI> implements ProxyClassUniformResourceIdentifierGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private ProxyClassUniformResourceIdentifierStringBuilder stringBuilder;
	
	@Override
	protected URI __execute__() throws Exception {
		URI uri = UNIFORM_RESOURCE_IDENTIFIER;
		if(uri == null) {
			Class<?> aClass = getClazz();
			String string = null;		
			if(__inject__(StringHelper.class).isBlank(string)) {
				//1 - from application
				string = __inject__(ProxyClassUniformResourceIdentifierStringProvider.class).setClazz(aClass).execute().getOutput();
				
				if(__inject__(StringHelper.class).isBlank(string)) {
					//2 - from server properties
					if(aClass == null)
						__injectThrowableHelper__().throwRuntimeException(getClass()+" : class is required");
					String identifier = StringUtils.substringBefore(aClass.getName(), ".server")+".server"+".uri";
					string = __inject__(SystemHelper.class).getProperty(identifier);
					if(__inject__(StringHelper.class).isBlank(string)) {
						//3 - from operating system properties 
						string = __inject__(OperatingSystemHelper.class).getProperty(identifier);
						if(__inject__(StringHelper.class).isBlank(string)) {
							//4 - from request
							ProxyClassUniformResourceIdentifierStringBuilder stringBuilder = getStringBuilder(Boolean.TRUE);
							string = stringBuilder.execute().getOutput();
						}
					}
				}	
			}
			
			if(__injectStringHelper__().isNotBlank(string)) 
				uri = URI.create(string);	
		}
		
		/*
		Class<?> aClass = getClazz();
		if(aClass == null)
			__injectThrowableHelper__().throwRuntimeException(getClass()+" : class is required");
		String identifier = StringUtils.substringBefore(aClass.getName(), ".server")+".server"+".uri";
		String uriString = __inject__(SystemHelper.class).getProperty(identifier,Boolean.TRUE);
		//if(__injectStringHelper__().isBlank(uriString)) 
		//	__injectThrowableHelper__().throwRuntimeException(getClass()+" : uniform resource identifier is required for "+identifier+". It must be defined under system properties or environment variable");
		if(__injectStringHelper__().isNotBlank(uriString)) 
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
		return (ProxyClassUniformResourceIdentifierStringBuilder) __getInjectIfNull__(FIELD_STRING_BUILDER, injectIfNull);
	}

	@Override
	public ProxyClassUniformResourceIdentifierGetter setStringBuilder(ProxyClassUniformResourceIdentifierStringBuilder stringBuilder) {
		this.stringBuilder = stringBuilder;
		return this;
	}
	
	public static final String FIELD_STRING_BUILDER = "stringBuilder";
	
	public static URI UNIFORM_RESOURCE_IDENTIFIER = null;
	
}
