package org.cyk.utility.client.controller.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutItemBuilder;
import org.cyk.utility.client.controller.component.output.OutputBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.collection.CollectionHelperImpl;
import org.cyk.utility.css.Style;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.function.Function;
import org.cyk.utility.function.FunctionHelperImpl;
import org.cyk.utility.instance.Instances;
import org.cyk.utility.internationalization.InternationalizationKey;
import org.cyk.utility.runnable.RunnableHelperImpl;
import org.cyk.utility.string.Case;

public class ComponentsBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Components> implements ComponentsBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private LayoutBuilder layout;
	private Style layoutStyle;
	private Instances components;
	private Boolean isHandleLayout;
	private Boolean isCreateLayoutItemOnAddComponent;
	private Object request;

	@Override
	protected Components __execute__() throws Exception {
		Boolean isHandleLayout = getIsHandleLayout();
		if(isHandleLayout == null)
			isHandleLayout = Boolean.TRUE;
		Components components = __inject__(Components.class);
		LayoutBuilder layout = getLayout();
		if(layout!=null && CollectionHelperImpl.__isNotEmpty__(layout.getChildren()))
			components.setLayout(layout.execute().getOutput());
		Instances instances = getComponents();
		Collection<Object> finals = null;
		if(CollectionHelperImpl.__isNotEmpty__(instances)) {
			finals = new ArrayList<>();
			Integer inputLabelWidthProportion = layout == null || layout.getGridRowModel() == null || layout.getGridRowModel().getWidthProportions() == null 
					|| layout.getGridRowModel().getWidthProportions().get(0) == null
					? null : layout.getGridRowModel().getWidthProportions().get(0);
			
			Integer inputWidthProportion = layout == null || layout.getGridRowModel() == null || layout.getGridRowModel().getWidthProportions() == null 
					|| layout.getGridRowModel().getWidthProportions().get(1) == null
					? null : layout.getGridRowModel().getWidthProportions().get(1);
			
			Integer inputMessageWidthProportion = layout == null || layout.getGridRowModel() == null || layout.getGridRowModel().getWidthProportions() == null 
					|| layout.getGridRowModel().getWidthProportions().get(2) == null
					? null : layout.getGridRowModel().getWidthProportions().get(2);
			
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
									inputBuilder.setAreaWidthProportionsForNotPhone(9);
									inputBuilder.getMessage().setAreaWidthProportionsForNotPhone(3);
								}
							else {
								if(inputBuilder.getMessage()==null) {
									inputBuilder.getLabel().setAreaWidthProportionsForNotPhone(3);
									inputBuilder.setAreaWidthProportionsForNotPhone(9);
								} else {
									inputBuilder.getLabel().setAreaWidthProportionsForNotPhone(inputLabelWidthProportion == null ? 3 : inputLabelWidthProportion);
									inputBuilder.setAreaWidthProportionsForNotPhone(inputWidthProportion == null ? 6 : inputWidthProportion);
									inputBuilder.getMessage().setAreaWidthProportionsForNotPhone(inputMessageWidthProportion == null ? 3 : inputMessageWidthProportion);
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
								outputStringText.getValueInternationalizationString(Boolean.TRUE).setKey(new InternationalizationKey().setValue(outputBuilder.getField().getName()));
								outputStringText.getValueInternationalizationString(Boolean.TRUE).setKase(Case.FIRST_CHARACTER_UPPER);
								finals.add(outputStringText);
							}
						}
						finals.add(componentBuilder);
						
						if(Boolean.TRUE.equals(isHandleLayout)) {
							//Width proportions
							if(outputStringText == null)
								outputBuilder.setAreaWidthProportionsForNotPhone(12);
							else {
								outputStringText.setAreaWidthProportionsForNotPhone(inputLabelWidthProportion == null ? 3 : inputLabelWidthProportion);
								outputBuilder.setAreaWidthProportionsForNotPhone(inputWidthProportion == null ? 9 : inputWidthProportion);
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
		if(CollectionHelperImpl.__isNotEmpty__(finals)) {
			Collection<Function<?,?>> functions = new ArrayList<>();
			for(Object indexFinal : finals) {
				if(indexFinal instanceof ComponentBuilder)
					functions.add((Function<?,?>)indexFinal);
			}
			RunnableHelperImpl.__run__(FunctionHelperImpl.__getRunnables__(functions), "components builders final");
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
				if(components.getLayout() == null || CollectionHelperImpl.__isEmpty__(layout.getItems())) {
					if(layout == null)
						layout = __inject__(LayoutBuilder.class);
					if(layout.getStyle() == null)
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
					if(layout.getRequest() == null)
						layout.setRequest(getRequest());
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
		if(layout == null && Boolean.TRUE.equals(injectIfNull))
			layout = __inject__(LayoutBuilder.class);
		return layout;
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
		if(components == null && Boolean.TRUE.equals(injectIfNull))
			components = __inject__(Instances.class);
		return components;
	}

	@Override
	public ComponentsBuilder setComponents(Instances components) {
		this.components = components;
		return this;
	}
	
	@Override
	public ComponentsBuilder addComponents(Collection<Object> components) {
		if(CollectionHelperImpl.__isNotEmpty__(components)) {
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
		return addComponents(CollectionHelperImpl.__instanciate__(components));
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
	public Style getLayoutStyle() {
		return layoutStyle;
	}
	
	@Override
	public Style getLayoutStyle(Boolean injectIfNull) {
		if(layoutStyle == null && Boolean.TRUE.equals(injectIfNull))
			layoutStyle = __inject__(Style.class);
		return layoutStyle;
	}
	
	@Override
	public ComponentsBuilder setLayoutStyle(Style layoutStyle) {
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
	
	@Override
	public Object getRequest() {
		return request;
	}
	
	@Override
	public ComponentsBuilder setRequest(Object request) {
		this.request = request;
		return this;
	}
	
	/**/
	
	public static final String FIELD_LAYOUT = "layout";
	public static final String FIELD_LAYOUT_STYLE = "layoutStyle";
	public static final String FIELD_COMPONENTS = "components";

}
