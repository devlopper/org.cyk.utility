package org.cyk.utility.client.controller.proxy;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierStringBuilder;
import org.cyk.utility.request.RequestGetter;
import org.cyk.utility.request.RequestProperty;
import org.cyk.utility.request.RequestPropertyValueGetter;
import org.cyk.utility.string.AbstractStringFunctionImpl;

public class ProxyClassUniformResourceIdentifierStringBuilderImpl extends AbstractStringFunctionImpl implements ProxyClassUniformResourceIdentifierStringBuilder,Serializable {
	private static final long serialVersionUID = 1L;
	
	private UniformResourceIdentifierStringBuilder uniformResourceIdentifierString;
	private Class<?> aClass;
	private Object request;
	
	@Override
	protected String __execute__() throws Exception {
		UniformResourceIdentifierStringBuilder uniformResourceIdentifierString = getUniformResourceIdentifierString();
		if(uniformResourceIdentifierString == null) {
			Object request = getRequest();
			if(request == null)
				request = __inject__(RequestGetter.class).execute().getOutput();
			String context = (String) __inject__(RequestPropertyValueGetter.class).setRequest(request).setProperty(RequestProperty.CONTEXT).execute().getOutput();
			context = __injectStringHelper__().addToBeginIfDoesNotStartWith(context, ConstantCharacter.SLASH.toString());
			context = __injectStringHelper__().addToEndIfDoesNotEndWith(context, ConstantCharacter.SLASH.toString());
			
			if(StringUtils.endsWith(context, CLIENT))
				context = StringUtils.replace(context, CLIENT, SERVER);

			uniformResourceIdentifierString = __inject__(UniformResourceIdentifierStringBuilder.class).setRequest(request).setContext(context);
		}
		return uniformResourceIdentifierString.execute().getOutput();
	}
	
	@Override
	public UniformResourceIdentifierStringBuilder getUniformResourceIdentifierString() {
		return uniformResourceIdentifierString;
	}
	
	@Override
	public UniformResourceIdentifierStringBuilder getUniformResourceIdentifierString(Boolean injectIfNull) {
		return (UniformResourceIdentifierStringBuilder) __getInjectIfNull__(FIELD_UNIFORM_RESOURCE_IDENTIFIER_STRING, injectIfNull);
	}
	
	@Override
	public ProxyClassUniformResourceIdentifierStringBuilder setUniformResourceIdentifierString(UniformResourceIdentifierStringBuilder uniformResourceIdentifierString) {
		this.uniformResourceIdentifierString = uniformResourceIdentifierString;
		return this;
	}
	
	@Override
	public Class<?> getClazz() {
		return aClass;
	}

	@Override
	public ProxyClassUniformResourceIdentifierStringBuilder setClazz(Class<?> aClass) {
		this.aClass = aClass;
		return this;
	}
	
	@Override
	public Object getRequest() {
		return request;
	}
	@Override
	public ProxyClassUniformResourceIdentifierStringBuilder setRequest(Object request) {
		this.request = request;
		return this;
	}

	@Override
	public ProxyClassUniformResourceIdentifierStringBuilder execute(Class<?> aClass) {
		setClazz(aClass).execute();
		return this;
	}

	@Override
	public ProxyClassUniformResourceIdentifierStringBuilder execute() {
		super.execute();
		return this;
	}
	
	public static final String FIELD_UNIFORM_RESOURCE_IDENTIFIER_STRING = "uniformResourceIdentifierString";
	public static final String CLIENT = "/client/";
	public static final String SERVER = "/server/";

}
