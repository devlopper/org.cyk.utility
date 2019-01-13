package org.cyk.utility.client.controller.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutItemBuilder;
import org.cyk.utility.client.controller.component.output.OutputBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.css.StyleBuilder;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.function.FunctionsExecutor;
import org.cyk.utility.instance.Instances;
import org.cyk.utility.string.Case;

public class ComponentsBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Components> implements ComponentsBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private LayoutBuilder layout;
	private StyleBuilder layoutStyle;
	private Instances components;
	private Boolean isHandleLayout;
	private Boolean isCreateLayoutItemOnAddComponent;

	@Override
	protected Components __execute__() throws Exception {
		Boolean isHandleLayout = getIsHandleLayout();
		if(isHandleLayout == null)
			isHandleLayout = Boolean.TRUE;
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
						finals.add(componentBuilder);
						if(inputBuilder.getMessage()!=null) {
							finals.add(inputBuilder.getMessage());
						}
						if(Boolean.TRUE.equals(isHandleLayout)) {
							//Width proportions
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
					}else if(index instanceof OutputBuilder<?, ?>) {
						OutputBuilder<?, ?> outputBuilder = (OutputBuilder<?, ?>) index;
						OutputStringTextBuilder outputStringText = null;
						if(outputBuilder.getField()!=null) {
							org.cyk.utility.client.controller.component.annotation.Input inputAnnotation = outputBuilder.getField()
									.getAnnotation(org.cyk.utility.client.controller.component.annotation.Input.class);
							if(inputAnnotation!=null) {
								outputStringText = __inject__(OutputStringTextBuilder.class);
								outputStringText.setValueInternalizationKeyValue(outputBuilder.getField().getName());
								outputStringText.getValueInternalization(Boolean.TRUE).setCase(Case.FIRST_CHARACTER_UPPER);
								finals.add(outputStringText);
							}
						}
						finals.add(componentBuilder);
						
						if(Boolean.TRUE.equals(isHandleLayout)) {
							//Width proportions
							if(outputStringText == null)
								outputBuilder.setAreaWidthProportionsForNotPhone(12);
							else {
								outputStringText.setAreaWidthProportionsForNotPhone(2);
								outputBuilder.setAreaWidthProportionsForNotPhone(10);
							}	
						}
					}else
						finals.add(componentBuilder);
					
				}else if(index instanceof Component) {
					Component component = (Component) index;
					finals.add(component);
				}
			}
		}
		
		//TODO build finals must be tune for fastest build
		if(__injectCollectionHelper__().isNotEmpty(finals)) {
			FunctionsExecutor functionsExecutor = __inject__(FunctionsExecutor.class);
			for(Object indexFinal : finals) {
				if(indexFinal instanceof ComponentBuilder)
					functionsExecutor.addFunctions((ComponentBuilder<?>)indexFinal);
			}
			functionsExecutor.execute();
			for(Object indexFinal : finals) {
				Component component = null;
				if(indexFinal instanceof ComponentBuilder)
					component = (Component) ((ComponentBuilder<?>)indexFinal).getComponent();
				else if(indexFinal instanceof Component)
					component = (Component) indexFinal;
				
				if(component!=null)
					components.add(component);
			}
			
			if(Boolean.TRUE.equals(isHandleLayout)) {
				
				//Derive layout
				if(components.getLayout() == null) {
					layout = __inject__(LayoutBuilder.class);
					layout.setStyle(getLayoutStyle());
					for(Component index : components.get()) {
						ComponentBuilder<?> componentBuilder = index.getBuilder(); //map.get(index);
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
	
	@Override
	public StyleBuilder getLayoutStyle() {
		return layoutStyle;
	}
	@Override
	public StyleBuilder getLayoutStyle(Boolean injectIfNull) {
		return (StyleBuilder) __getInjectIfNull__(FIELD_LAYOUT_STYLE, injectIfNull);
	}
	
	@Override
	public ComponentsBuilder setLayoutStyle(StyleBuilder layoutStyle) {
		this.layoutStyle = layoutStyle;
		return this;
	}
	
	@Override
	public Boolean getIsHandleLayout() {
		return isHandleLayout;
	}
	
	@Override
	public ComponentsBuilder setIsHandleLayout(Boolean isHandleLayout) {
		this.isHandleLayout = isHandleLayout;
		return this;
	}
	
	/**/
	
	public static final String FIELD_LAYOUT = "layout";
	public static final String FIELD_LAYOUT_STYLE = "layoutStyle";
	public static final String FIELD_COMPONENTS = "components";

}
