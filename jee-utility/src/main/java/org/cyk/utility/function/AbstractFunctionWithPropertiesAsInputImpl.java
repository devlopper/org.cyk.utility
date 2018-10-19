package org.cyk.utility.function;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;

public abstract class AbstractFunctionWithPropertiesAsInputImpl<OUTPUT> extends AbstractFunctionImpl<Properties, OUTPUT> implements FunctionWithPropertiesAsInput<OUTPUT>,Serializable {
	private static final long serialVersionUID = 1L;

	protected Properties outputProperties;
	
	@Override
	public Properties getOutputProperties() {
		return outputProperties;
	}
	
	@Override
	public Properties getOutputProperties(Boolean injectIfNull) {
		return (Properties) __getInjectIfNull__(FIELD_OUTPUT_PROPERTIES, injectIfNull);
	}
	
	@Override
	public FunctionWithPropertiesAsInput<OUTPUT> setOutputProperties(Properties outputProperties) {
		this.outputProperties = outputProperties;
		return this;
	}
	
	@Override
	public Object getOutputProperty(Object key) {
		return Properties.getFromPath(getOutputProperties(), key);
	}
	
	@Override
	public FunctionWithPropertiesAsInput<OUTPUT> setOutputProperty(Object key, Object value) {
		Properties.setFromPath(getOutputProperties(), new Object[] {key},value);
		return this;
	}
	
	/**/
	
	public static final String FIELD_OUTPUT_PROPERTIES = "outputProperties";
}
