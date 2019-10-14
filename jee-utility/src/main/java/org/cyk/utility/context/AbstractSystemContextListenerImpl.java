package org.cyk.utility.context;

import java.io.Serializable;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.identifier.resource.ProxyUniformResourceIdentifierGetter;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.value.ValueHelper;

public abstract class AbstractSystemContextListenerImpl<CONTEXT> extends AbstractObject implements SystemContextListener<CONTEXT>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public SystemContextListener<CONTEXT> initialize(CONTEXT context) {
		__logInfo__("Context initialization running.");
		__initialize__(context);
		ProxyUniformResourceIdentifierGetter.UNIFORM_RESOURCE_IDENTIFIER_STRING.initialize();
		__logInfo__("Context initialization done.");
		return this;
	}
	
	protected void __initialize__(CONTEXT context) {}
	
	@Override
	public SystemContextListener<CONTEXT> destroy(CONTEXT context) {
		__logInfo__("Context destruction running.");
		__destroy__(context);
		__logInfo__("Context destruction done.");
		return this;
	}
	
	protected void __destroy__(CONTEXT context) {}

	/**/
	
	protected static String getConfigurationParameterValue(String name,Object nullValue) {
		return (String) ConfigurationHelper.getValue(name, null, null, nullValue == null ? null : nullValue.toString());
	}
	
	protected static String getConfigurationParameterValue(String name) {
		return getConfigurationParameterValue(name, null);
	}
	
	protected static Integer getConfigurationParameterValueAsInteger(String name,Object nullValue) {
		return NumberHelper.getInteger(getConfigurationParameterValue(name, nullValue));
	}
	
	protected static Integer getConfigurationParameterValueAsInteger(String name) {
		return getConfigurationParameterValueAsInteger(name, null);
	}
	
	protected static Boolean getConfigurationParameterValueAsBoolean(String name,Object nullValue) {
		return ValueHelper.convertToBoolean(getConfigurationParameterValue(name, nullValue));
	}
	
	protected static Boolean getConfigurationParameterValueAsBoolean(String name) {
		return getConfigurationParameterValueAsBoolean(name, null);
	}
}
