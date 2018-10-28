package org.cyk.utility.client.controller.component;

import java.util.Collection;

import org.cyk.utility.client.controller.component.layout.LayoutBuilder;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface ComponentsBuilder extends FunctionWithPropertiesAsInput<Components> {

	LayoutBuilder getLayout();
	LayoutBuilder getLayout(Boolean injectIfNull);
	ComponentsBuilder setLayout(LayoutBuilder layout);
	
	ComponentBuilders getComponents();
	ComponentBuilders getComponents(Boolean injectIfNull);
	ComponentsBuilder setComponents(ComponentBuilders components);
	ComponentsBuilder addComponents(@SuppressWarnings("rawtypes") Collection<ComponentBuilder> components);
	ComponentsBuilder addComponents(@SuppressWarnings("rawtypes") ComponentBuilder...components);
	
}
