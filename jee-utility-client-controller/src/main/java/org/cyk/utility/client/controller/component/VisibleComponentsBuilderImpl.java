package org.cyk.utility.client.controller.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.client.controller.component.input.Input;
import org.cyk.utility.client.controller.component.layout.LayoutBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutItemBuilder;
import org.cyk.utility.device.Device;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class VisibleComponentsBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<VisibleComponents> implements VisibleComponentsBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private LayoutBuilder layoutBuilder;
	private Collection<VisibleComponent> components;
	
	@Override
	protected VisibleComponents __execute__() throws Exception {
		VisibleComponents visibleComponents = __inject__(VisibleComponents.class);
		LayoutBuilder layoutBuiler = getLayoutBuilder(Boolean.TRUE);
		visibleComponents.setLayout(layoutBuiler.execute().getOutput());
		Collection<VisibleComponent> components = getComponents();
		if(components!=null) {
			Integer indexVisibleComponentCount = 0;
			for(VisibleComponent indexVisibleComponent : components) {
				visibleComponents.add(indexVisibleComponent);
				if(visibleComponents.getLayout()!=null) {
					indexVisibleComponent.setLayoutItem(visibleComponents.getLayout().getChildAt(indexVisibleComponentCount));
					indexVisibleComponentCount++;
				}
			}
		}
		return visibleComponents;
	}
	
	@Override
	public LayoutBuilder getLayoutBuilder() {
		return layoutBuilder;
	}
	
	@Override
	public LayoutBuilder getLayoutBuilder(Boolean injectIfNull) {
		return (LayoutBuilder) __getInjectIfNull__(FIELD_LAYOUT_BUILDER, injectIfNull);
	}

	@Override
	public VisibleComponentsBuilder setLayoutBuilder(LayoutBuilder layoutBuilder) {
		this.layoutBuilder = layoutBuilder;
		return this;
	}

	@Override
	public Collection<VisibleComponent> getComponents() {
		return components;
	}
	
	@Override
	public Collection<VisibleComponent> getComponents(Boolean instanciateIfNull) {
		Collection<VisibleComponent> collection = getComponents();
		if(collection == null && Boolean.TRUE.equals(instanciateIfNull))
			setComponents(collection = new ArrayList<>());
		return collection;
	}

	@Override
	public VisibleComponentsBuilder setComponents(Collection<VisibleComponent> components) {
		this.components = components;
		return this;
	}
	
	@Override
	public VisibleComponentsBuilder addComponents(Collection<VisibleComponent> visibleComponents) {
		visibleComponents = __injectCollectionHelper__().removeNullValue(visibleComponents);
		if(__injectCollectionHelper__().isNotEmpty(visibleComponents)) {
			Collection<VisibleComponent> collection = getComponents(Boolean.TRUE);
			collection.addAll(visibleComponents);
			
			LayoutBuilder layoutBuilder = getLayoutBuilder(Boolean.TRUE);
			for(VisibleComponent indexComponent : visibleComponents) {
				Map<Class<? extends Device>,Integer> widthMap = null;
				if(indexComponent.getArea()!=null && indexComponent.getArea().getWidthProportions()!=null)
					widthMap = indexComponent.getArea().getWidthProportions().getMap();
				if(widthMap == null) {
					if(indexComponent instanceof Input<?>) {
						/*Input<?> input = (Input<?>) indexComponent;
						Integer layoutWidth = __inject__(LayoutWidthGetter.class).execute().getOutput().intValue();
						Integer labelWidth = layoutWidth / 6;
						Integer inputWidth = layoutWidth / 2;
						Integer messageWidth = layoutWidth / 3;
						input.getLabelComponent().setWidths(labelWidth, labelWidth, labelWidth, labelWidth, layoutWidth);
						input.setWidths(inputWidth, inputWidth, inputWidth, inputWidth, layoutWidth);
						input.getMessageComponent().setWidths(messageWidth, messageWidth, messageWidth, messageWidth, layoutWidth);
						*/
					}
				}
				layoutBuilder.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsAll(widthMap == null ? null : widthMap.get(null)));
			}
		}
		return this;
	}
	
	@Override
	public VisibleComponentsBuilder addComponents(VisibleComponent... visibleComponents) {
		return addComponents(__injectCollectionHelper__().instanciate(visibleComponents));
	}
	
	/**/
	
	public static final String FIELD_LAYOUT_BUILDER = "layoutBuilder";

}
