package org.cyk.utility.context;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.configuration.ConstantParameterName;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.type.BooleanHelper;

public abstract class AbstractSystemContextListener<CONTEXT> extends AbstractObject implements SystemContextListener<CONTEXT>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public SystemContextListener<CONTEXT> initialize(CONTEXT context) {
		__logInfo__("Context initialization running.");
		__initialize__(context);
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
		return ConstantParameterName.get(name, null, null, nullValue == null ? null : nullValue.toString());
	}
	
	protected static String getConfigurationParameterValue(String name) {
		return getConfigurationParameterValue(name, null);
	}
	
	protected static Integer getConfigurationParameterValueAsInteger(String name,Object nullValue) {
		return __inject__(NumberHelper.class).getInteger(getConfigurationParameterValue(name, nullValue));
	}
	
	protected static Integer getConfigurationParameterValueAsInteger(String name) {
		return getConfigurationParameterValueAsInteger(name, null);
	}
	
	protected static Boolean getConfigurationParameterValueAsBoolean(String name,Object nullValue) {
		return __inject__(BooleanHelper.class).get(getConfigurationParameterValue(name, nullValue));
	}
	
	protected static Boolean getConfigurationParameterValueAsBoolean(String name) {
		return getConfigurationParameterValueAsBoolean(name, null);
	}
}
