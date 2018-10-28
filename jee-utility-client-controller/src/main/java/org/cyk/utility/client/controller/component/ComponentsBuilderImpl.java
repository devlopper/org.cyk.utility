package org.cyk.utility.client.controller.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutItemBuilder;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class ComponentsBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Components> implements ComponentsBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private LayoutBuilder layout;
	private ComponentBuilders components;
	
	@Override
	protected Components __execute__() throws Exception {
		Components components = __inject__(Components.class);
		LayoutBuilder layout = getLayout();
		if(layout!=null)
			components.setLayout(layout.execute().getOutput());
		ComponentBuilders builders = getComponents();
		Collection<ComponentBuilder<?>> finalBuilders = null;
		
		if(__injectCollectionHelper__().isNotEmpty(builders)) {
			finalBuilders = new ArrayList<>();
			for(ComponentBuilder<?> index : builders.get()) {
				if(index!=null) {
					//Derived builders
					if(index instanceof InputBuilder<?, ?>) {
						InputBuilder<?, ?> inputBuilder = (InputBuilder<?, ?>) index;
						if(inputBuilder.getLabel()!=null) {
							finalBuilders.add(inputBuilder.getLabel());
						}
					}
					finalBuilders.add(index);
					if(index instanceof InputBuilder<?, ?>) {
						InputBuilder<?, ?> inputBuilder = (InputBuilder<?, ?>) index;
						if(inputBuilder.getMessage()!=null) {
							finalBuilders.add(inputBuilder.getMessage());
						}
					}
					
					//Width proportions
					if(index instanceof InputBuilder<?, ?>) {
						InputBuilder<?, ?> inputBuilder = (InputBuilder<?, ?>) index;
						if(inputBuilder.getLabel()==null)
							if(inputBuilder.getMessage()==null) {
								//Nothing to do
							} else {
								inputBuilder.setAreaWidthProportionsForNotPhone(10);
								inputBuilder.getMessage().setAreaWidthProportionsForNotPhone(2);
							}
						else {
							if(inputBuilder.getMessage()==null) {
								inputBuilder.getLabel().setAreaWidthProportionsForNotPhone(2);
								inputBuilder.setAreaWidthProportionsForNotPhone(10);
							} else {
								inputBuilder.getLabel().setAreaWidthProportionsForNotPhone(2);
								inputBuilder.setAreaWidthProportionsForNotPhone(6);
								inputBuilder.getMessage().setAreaWidthProportionsForNotPhone(4);
							}
						}								
					}
				}
			}
			
		}
		
		if(__injectCollectionHelper__().isNotEmpty(finalBuilders)) {
			Map<Component,ComponentBuilder<?>> map = new HashMap<>();
			//Build components
			for(@SuppressWarnings("rawtypes") ComponentBuilder indexBuilder : finalBuilders) {
				Component component = (Component)indexBuilder.execute().getOutput();
				if(component!=null) {
					components.add(component);
					map.put(component, indexBuilder);
				}
			}
			//Derive layout
			if(components.getLayout() == null) {
				layout = __inject__(LayoutBuilder.class);
				for(Component index : components.get()) {
					ComponentBuilder<?> componentBuilder = map.get(index);
					LayoutItemBuilder layoutItemBuilder = __inject__(LayoutItemBuilder.class);
					//if(componentBuilder instanceof VisibleComponentBuilder)
					//	layoutItemBuilder.setStyle(((VisibleComponentBuilder<?>) componentBuilder).getStyle());
					layoutItemBuilder.setArea(componentBuilder.getArea()).setOutputPropertyValue(index.toString());
					layoutItemBuilder.setStyle(componentBuilder.getLayoutItemStyle());
					layout.addItems(layoutItemBuilder);
				}
				components.setLayout(layout.execute().getOutput());
			}
			//Set layout items
			if(components.getLayout() != null) {
				Integer indexLayoutItem = 0;
				for(Component index : components.get()) {
					index.setLayoutItem(components.getLayout().getChildAt(indexLayoutItem));
					indexLayoutItem = indexLayoutItem + 1;
					
				}
			}
		}
		
		
		return components;
	}
	
	@Override
	public LayoutBuilder getLayout() {
		return layout;
	}
	
	@Override
	public LayoutBuilder getLayout(Boolean injectIfNull) {
		return (LayoutBuilder) __getInjectIfNull__(FIELD_LAYOUT, injectIfNull);
	}
	
	@Override
	public ComponentsBuilder setLayout(LayoutBuilder layout) {
		this.layout = layout;
		return this;
	}

	@Override
	public ComponentBuilders getComponents() {
		return components;
	}
	
	@Override
	public ComponentBuilders getComponents(Boolean injectIfNull) {
		return (ComponentBuilders) __getInjectIfNull__(FIELD_COMPONENTS, injectIfNull);
	}

	@Override
	public ComponentsBuilder setComponents(ComponentBuilders components) {
		this.components = components;
		return this;
	}
	
	@Override
	public ComponentsBuilder addComponents(@SuppressWarnings("rawtypes") Collection<ComponentBuilder> components) {
		getComponents(Boolean.TRUE).add(components);
		return this;
	}
	
	@Override
	public ComponentsBuilder addComponents(@SuppressWarnings("rawtypes") ComponentBuilder...components) {
		return addComponents(__injectCollectionHelper__().instanciate(components));
	}
	
	/**/
	
	public static final String FIELD_LAYOUT = "layout";
	public static final String FIELD_COMPONENTS = "components";

}
