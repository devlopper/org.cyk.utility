package org.cyk.utility.configuration;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.context.ContextGetter;
import org.cyk.utility.context.ContextParameterValueGetter;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.log.Log;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.request.RequestParameterValueGetter;
import org.cyk.utility.system.OperatingSystemHelper;
import org.cyk.utility.system.SystemHelper;
import org.cyk.utility.value.IsNullChecker;

public class ConfigurationParameterValueGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements ConfigurationParameterValueGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Object context,request;
	private String name;
	private Class<? extends IsNullChecker> isNullValueCheckerClass;
	private Class<?> isNullValueCheckerQualifierClass;
	private Object nullValue;
	
	@Override
	protected Object __execute__() throws Exception {
		String name = __injectValueHelper__().returnOrThrowIfBlank("configuration parameter name", getName());
		Object value = null;
		Object request = getRequest();
		Log log = __inject__(Log.class);
		log.getMessageBuilder(Boolean.TRUE).addParameter("configuration parameter",name);
		String location = null;
		if(request != null)
			value = __inject__(RequestParameterValueGetter.class).setRequest(request).setParameterName(name).execute().getOutput();
		if(value == null) {
			value = __inject__(SystemHelper.class).getProperty(name);
			if(value == null) {
				Object context = getContext();
				if(context == null)
					context = __inject__(ContextGetter.class).execute().getOutput();
				if(context!=null)
					value = __inject__(ContextParameterValueGetter.class).setContext(context).setName(name).execute().getOutput();
				if(value == null) {
					value = __inject__(OperatingSystemHelper.class).getProperty(name);
					if(value == null)
						;
					else
						location = "operating system";
				}else
					location = "context";
			}else
				location = "system";
		}else
			location = "request";
		
		log.getMessageBuilder(Boolean.TRUE).addParameter("location", location);
		
		Class<? extends IsNullChecker> isNullValueCheckerClass = getIsNullValueCheckerClass();
		log.getMessageBuilder(Boolean.TRUE).addParameter("is null checker class", isNullValueCheckerClass);
		if(isNullValueCheckerClass!=null) {
			Boolean isNull = __injectValueHelper__().defaultToIfNull(__injectByQualifiersClasses__(isNullValueCheckerClass,getIsNullValueCheckerQualifierClass())
					.setValue(value).execute().getOutput(),Boolean.TRUE);
			log.getMessageBuilder(Boolean.TRUE).addParameter("is null ?", isNull);
			if(Boolean.TRUE.equals(isNull))
				value = null;
		}
		if(value == null)
			value = getNullValue();
		log.getMessageBuilder(Boolean.TRUE).addParameter("value", value);
		log.setLevel(LogLevel.INFO).execute();
		return value;
	}
	
	@Override
	public Function<Properties, Object> execute(String name, Object context, Object request,Object nullValue) {
		return setName(name).setContext(context).setRequest(request).setNullValue(nullValue).execute();
	}
	
	@Override
	public Function<Properties, Object> execute(String name, Object context, Object request) {
		return setName(name).setContext(context).setRequest(request).execute();
	}
	
	@Override
	public Object getContext() {
		return context;
	}

	@Override
	public ConfigurationParameterValueGetter setContext(Object context) {
		this.context = context;
		return this;
	}

	@Override
	public Object getRequest() {
		return request;
	}

	@Override
	public ConfigurationParameterValueGetter setRequest(Object request) {
		this.request = request;
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ConfigurationParameterValueGetter setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public Class<? extends IsNullChecker> getIsNullValueCheckerClass() {
		return isNullValueCheckerClass;
	}

	@Override
	public ConfigurationParameterValueGetter setIsNullValueCheckerClass(Class<? extends IsNullChecker> isNullValueCheckerClass) {
		this.isNullValueCheckerClass = isNullValueCheckerClass;
		return this;
	}
	
	@Override
	public Class<?> getIsNullValueCheckerQualifierClass() {
		return isNullValueCheckerQualifierClass;
	}
	
	@Override
	public ConfigurationParameterValueGetter setIsNullValueCheckerQualifierClass(Class<?> isNullValueCheckerQualifierClass) {
		this.isNullValueCheckerQualifierClass = isNullValueCheckerQualifierClass;
		return this;
	}

	@Override
	public Object getNullValue() {
		return nullValue;
	}

	@Override
	public ConfigurationParameterValueGetter setNullValue(Object nullValue) {
		this.nullValue = nullValue;
		return this;
	}

}
