package org.cyk.utility.client.controller.component.script;

import org.cyk.utility.client.controller.component.VisibleComponent;

public interface Script extends VisibleComponent {

	String getSourceUniformResourceLocator();
	Script setSourceUniformResourceLocator(String sourceUniformResourceLocator);
	
	org.cyk.utility.programming.script.Script getValue();
	Script setValue(org.cyk.utility.programming.script.Script value);
	
}
