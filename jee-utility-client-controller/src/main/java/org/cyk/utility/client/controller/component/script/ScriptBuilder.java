package org.cyk.utility.client.controller.component.script;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;

public interface ScriptBuilder extends VisibleComponentBuilder<Script> {

	String getSourceUniformResourceLocator();
	ScriptBuilder setSourceUniformResourceLocator(String sourceUniformResourceLocator);
	
	org.cyk.utility.programming.script.ScriptBuilder getValue();
	org.cyk.utility.programming.script.ScriptBuilder getValue(Boolean injectIfNull);
	ScriptBuilder setValue(org.cyk.utility.programming.script.ScriptBuilder value);
	
}
