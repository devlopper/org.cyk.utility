package org.cyk.utility.client.controller.component;

import java.util.Collection;

import org.cyk.utility.__kernel__.object.Objects;
import org.cyk.utility.client.controller.component.layout.LayoutBuilder;
import org.cyk.utility.css.Style;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface ComponentsBuilder extends FunctionWithPropertiesAsInput<Components> {

	ComponentsBuilder setIsHandleLayout(Boolean isHandleLayout);
	Boolean getIsHandleLayout();
	
	LayoutBuilder getLayout();
	LayoutBuilder getLayout(Boolean injectIfNull);
	ComponentsBuilder setLayout(LayoutBuilder layout);
	
	Style getLayoutStyle();
	Style getLayoutStyle(Boolean injectIfNull);
	ComponentsBuilder setLayoutStyle(Style layoutStyle);
	
	ComponentsBuilder setIsCreateLayoutItemOnAddComponent(Boolean isCreateLayoutItemOnAddComponent);
	Boolean getIsCreateLayoutItemOnAddComponent();
	
	Objects getComponents();
	Objects getComponents(Boolean injectIfNull);
	ComponentsBuilder setComponents(Objects components);
	ComponentsBuilder addComponents(Collection<Object> components);
	ComponentsBuilder addComponents(Object...components);
	
	Object getRequest();
	ComponentsBuilder setRequest(Object request);

}
