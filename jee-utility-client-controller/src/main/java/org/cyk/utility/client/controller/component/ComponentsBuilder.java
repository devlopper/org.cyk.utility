package org.cyk.utility.client.controller.component;

import java.util.Collection;

import org.cyk.utility.client.controller.component.layout.LayoutBuilder;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.instance.Instances;

public interface ComponentsBuilder extends FunctionWithPropertiesAsInput<Components> {

	LayoutBuilder getLayout();
	LayoutBuilder getLayout(Boolean injectIfNull);
	ComponentsBuilder setLayout(LayoutBuilder layout);
	
	ComponentsBuilder setIsCreateLayoutItemOnAddComponent(Boolean isCreateLayoutItemOnAddComponent);
	Boolean getIsCreateLayoutItemOnAddComponent();
	
	Instances getComponents();
	Instances getComponents(Boolean injectIfNull);
	ComponentsBuilder setComponents(Instances components);
	ComponentsBuilder addComponents(Collection<Object> components);
	ComponentsBuilder addComponents(Object...components);

}
