package org.cyk.utility.client.controller.component.script;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;

public class ScriptBuilderImpl extends AbstractVisibleComponentBuilderImpl<Script> implements ScriptBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private String sourceUniformResourceLocator;
	private org.cyk.utility.programming.script.ScriptBuilder value;
	
	@Override
	protected void __execute__(Script script) {
		super.__execute__(script);
		String sourceUniformResourceLocator = getSourceUniformResourceLocator();
		script.setSourceUniformResourceLocator(sourceUniformResourceLocator);
		
		org.cyk.utility.programming.script.ScriptBuilder value = getValue();
		if(value!=null)
			script.setValue(value.execute().getOutput());
	}
	
	@Override
	public String getSourceUniformResourceLocator() {
		return sourceUniformResourceLocator;
	}

	@Override
	public ScriptBuilder setSourceUniformResourceLocator(String sourceUniformResourceLocator) {
		this.sourceUniformResourceLocator = sourceUniformResourceLocator;
		return this;
	}

	@Override
	public org.cyk.utility.programming.script.ScriptBuilder getValue() {
		return value;
	}
	
	@Override
	public org.cyk.utility.programming.script.ScriptBuilder getValue(Boolean injectIfNull) {
		return (org.cyk.utility.programming.script.ScriptBuilder) __getInjectIfNull__(FIELD_VALUE, injectIfNull);
	}

	@Override
	public ScriptBuilder setValue(org.cyk.utility.programming.script.ScriptBuilder value) {
		this.value = value;
		return this;
	}
	
	private static final String FIELD_VALUE = "value";

}
