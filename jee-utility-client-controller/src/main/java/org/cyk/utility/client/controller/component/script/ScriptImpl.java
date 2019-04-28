package org.cyk.utility.client.controller.component.script;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;

public class ScriptImpl extends AbstractVisibleComponentImpl implements Script,Serializable {
	private static final long serialVersionUID = 1L;

	private String sourceUniformResourceLocator;
	private org.cyk.utility.programming.script.Script value;
	
	@Override
	public org.cyk.utility.programming.script.Script getValue() {
		return value;
	}

	@Override
	public Script setValue(org.cyk.utility.programming.script.Script value) {
		this.value = value;
		return this;
	}

	@Override
	public String getSourceUniformResourceLocator() {
		return sourceUniformResourceLocator;
	}

	@Override
	public Script setSourceUniformResourceLocator(String sourceUniformResourceLocator) {
		this.sourceUniformResourceLocator = sourceUniformResourceLocator;
		return this;
	}

}
