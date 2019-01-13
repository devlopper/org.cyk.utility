package org.cyk.utility.client.controller.component;

import java.util.Collection;

import org.cyk.utility.client.controller.component.layout.LayoutBuilder;
import org.cyk.utility.css.StyleBuilder;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.instance.Instances;

public interface ComponentsBuilder extends FunctionWithPropertiesAsInput<Components> {

	ComponentsBuilder setIsHandleLayout(Boolean isHandleLayout);
	Boolean getIsHandleLayout();
	
	LayoutBuilder getLayout();
	LayoutBuilder getLayout(Boolean injectIfNull);
	ComponentsBuilder setLayout(LayoutBuilder layout);
	
	StyleBuilder getLayoutStyle();
	StyleBuilder getLayoutStyle(Boolean injectIfNull);
	ComponentsBuilder setLayoutStyle(StyleBuilder layoutStyle);
	
	ComponentsBuilder setIsCreateLayoutItemOnAddComponent(Boolean isCreateLayoutItemOnAddComponent);
	Boolean getIsCreateLayoutItemOnAddComponent();
	
	Instances getComponents();
	Instances getComponents(Boolean injectIfNull);
	ComponentsBuilder setComponents(Instances components);
	ComponentsBuilder addComponents(Collection<Object> components);
	ComponentsBuilder addComponents(Object...components);
	
}
