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
		if(outputProperties == null && Boolean.TRUE.equals(injectIfNull))
			outputProperties = new Properties();
		return outputProperties;
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
	
	@Override
	public FunctionWithPropertiesAsInput<OUTPUT> setOutputPropertyName(Object value) {
		getOutputProperties(Boolean.TRUE).setName(value);
		return this;
	}
	
	@Override
	public Object getOutputPropertyName() {
		Properties outputProperties = getOutputProperties();
		return outputProperties == null ? null : outputProperties.getName();
	}
	
	@Override
	public FunctionWithPropertiesAsInput<OUTPUT> setOutputPropertyValue(Object value) {
		getOutputProperties(Boolean.TRUE).setValue(value);
		return this;
	}
	
	@Override
	public Object getOutputPropertyValue() {
		Properties outputProperties = getOutputProperties();
		return outputProperties == null ? null : outputProperties.getValue();
	}
	
	/**/
	
	public static final String FIELD_OUTPUT_PROPERTIES = "outputProperties";
}
