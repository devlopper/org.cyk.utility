package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

public class LayoutImpl extends AbstractVisibleComponentImpl implements Layout, Serializable {
	private static final long serialVersionUID = 1L;
	
	private LayoutType type;
	
	@Override
	public Layout addItemFromClass(Object clazz) {
		LayoutItem item = __inject__(LayoutItem.class);
		item.getProperties().setClass(clazz);
		addChild(item);
		return this;
	}
	
	@Override
	public Layout addItemFromClasses(Collection<String> classes) {
		return addItemFromClass(StringHelper.concatenate(classes,ConstantCharacter.SPACE.toString()));
	}
	
	@Override
	public Layout addItemFromClasses(String... classes) {
		return addItemFromClasses(CollectionHelper.listOf(classes));
	}
	/*
	@Override
	public Layout addItemFromWidthClassBuilders(Collection<StyleClassBuilderWidth> styleClassBuilderWidths) {
		Collection<String> classes = new LinkedHashSet<>();
		if(CollectionHelper.isNotEmpty(styleClassBuilderWidths))
			for(StyleClassBuilderWidth index : styleClassBuilderWidths)
				classes.add(index.execute().getOutput());
		return addItemFromClasses(classes);
	}
	
	@Override
	public Layout addItemFromWidthClassBuilders(StyleClassBuilderWidth... styleClassBuilderWidths) {
		return addItemFromWidthClassBuilders(CollectionHelper.listOf(styleClassBuilderWidths));
	}
	
	@Override
	public Layout addItemFromDeviceClassAndWidths(Object... objects) {
		Collection<StyleClassBuilderWidth> styleClassBuilderWidths = null;
		if(__inject__(ArrayHelper.class).isNotEmpty(objects)) {
			styleClassBuilderWidths = new ArrayList<>();
			for(Integer index = 0 ; index < objects.length ; index = index + 2) {
				@SuppressWarnings("unchecked")
				Class<Device> deviceClass = (Class<Device>) objects[index];
				Integer width = (Integer) objects[index + 1];
				styleClassBuilderWidths.add(__inject__(StyleClassBuilderWidth.class).setDevice(deviceClass == null ? null : __inject__(deviceClass)).setWidth(width));
			}
		}
		return addItemFromWidthClassBuilders(styleClassBuilderWidths);
	}
	*/
	@Override
	public LayoutItem getChildAt(Integer index) {
		return (LayoutItem) super.getChildAt(index);
	}

	@Override
	public LayoutType getType() {
		return type;
	}

	@Override
	public Layout setType(LayoutType type) {
		this.type = type;
		return this;
	}
	
}
