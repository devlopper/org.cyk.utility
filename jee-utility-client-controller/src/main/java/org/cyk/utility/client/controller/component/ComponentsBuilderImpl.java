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
import org.cyk.utility.instance.Instances;

public class ComponentsBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Components> implements ComponentsBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private LayoutBuilder layout;
	private Instances components;
	private Boolean isCreateLayoutItemOnAddComponent;
	
	@Override
	protected Components __execute__() throws Exception {
		Components components = __inject__(Components.class);
		LayoutBuilder layout = getLayout();
		if(layout!=null)
			components.setLayout(layout.execute().getOutput());
		Instances instances = getComponents();
		Collection<Object> finals = null;
		
		if(__injectCollectionHelper__().isNotEmpty(instances)) {
			finals = new ArrayList<>();
			for(Object index : instances.get()) {
				if(index instanceof ComponentBuilder) {
					ComponentBuilder<?> componentBuilder = (ComponentBuilder<?>) index;	
					//Derived builders
					if(index instanceof InputBuilder<?, ?>) {
						InputBuilder<?, ?> inputBuilder = (InputBuilder<?, ?>) index;
						if(inputBuilder.getLabel()!=null) {
							finals.add(inputBuilder.getLabel());
						}
					}
					finals.add(componentBuilder);
					if(index instanceof InputBuilder<?, ?>) {
						InputBuilder<?, ?> inputBuilder = (InputBuilder<?, ?>) index;
						if(inputBuilder.getMessage()!=null) {
							finals.add(inputBuilder.getMessage());
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
				}else if(index instanceof Component) {
					Component component = (Component) index;
					finals.add(component);
				}
			}
			
		}
		
		if(__injectCollectionHelper__().isNotEmpty(finals)) {
			Map<Component,ComponentBuilder<?>> map = new HashMap<>();
			//Build components
			for(Object indexFinal : finals) {
				ComponentBuilder<?> componentBuilder = null;
				Component component = null;
				if(indexFinal instanceof ComponentBuilder) {
					componentBuilder = (ComponentBuilder<?>)indexFinal;
					component = (Component) componentBuilder.execute().getOutput();
				}else if(indexFinal instanceof Component)
					component = (Component) indexFinal;
				
				if(component!=null) {
					components.add(component);
					map.put(component, componentBuilder);
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
					if(componentBuilder!=null) {
						layoutItemBuilder.setArea(componentBuilder.getArea()).setOutputPropertyValue(index.toString());
						layoutItemBuilder.setStyle(componentBuilder.getLayoutItemStyle());
					}
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
	public Instances getComponents() {
		return components;
	}
	
	@Override
	public Instances getComponents(Boolean injectIfNull) {
		return (Instances) __getInjectIfNull__(FIELD_COMPONENTS, injectIfNull);
	}

	@Override
	public ComponentsBuilder setComponents(Instances components) {
		this.components = components;
		return this;
	}
	
	@Override
	public ComponentsBuilder addComponents(Collection<Object> components) {
		if(__injectCollectionHelper__().isNotEmpty(components)) {
			getComponents(Boolean.TRUE).add((Collection<Object>)components);
			if(Boolean.TRUE.equals(getIsCreateLayoutItemOnAddComponent())) {
				for(@SuppressWarnings("unused") Object index : components) {
					getLayout(Boolean.TRUE).addItems(__inject__(LayoutItemBuilder.class));
				}
			}
		}
		return this;
	}
	
	@Override
	public ComponentsBuilder addComponents(Object...components) {
		return addComponents(__injectCollectionHelper__().instanciate(components));
	}
	
	@Override
	public Boolean getIsCreateLayoutItemOnAddComponent() {
		return isCreateLayoutItemOnAddComponent;
	}
	
	@Override
	public ComponentsBuilder setIsCreateLayoutItemOnAddComponent(Boolean isCreateLayoutItemOnAddComponent) {
		this.isCreateLayoutItemOnAddComponent = isCreateLayoutItemOnAddComponent;
		return this;
	}
	
	/**/
	
	public static final String FIELD_LAYOUT = "layout";
	public static final String FIELD_COMPONENTS = "components";

}
