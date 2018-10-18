package org.cyk.utility.client.controller.component;

import java.util.Collection;

import org.cyk.utility.client.controller.component.layout.LayoutBuilder;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface VisibleComponentsBuilder extends FunctionWithPropertiesAsInput<VisibleComponents> {

	LayoutBuilder getLayoutBuilder();
	LayoutBuilder getLayoutBuilder(Boolean injectIfNull);
	VisibleComponentsBuilder setLayoutBuilder(LayoutBuilder layoutBuilder);
	
	Collection<VisibleComponent> getComponents();
	Collection<VisibleComponent> getComponents(Boolean instanciateIfNull);
	VisibleComponentsBuilder setComponents(Collection<VisibleComponent> visibleComponents);
	
	VisibleComponentsBuilder addComponents(Collection<VisibleComponent> visibleComponents);
	VisibleComponentsBuilder addComponents(VisibleComponent...visibleComponents);
	
}
