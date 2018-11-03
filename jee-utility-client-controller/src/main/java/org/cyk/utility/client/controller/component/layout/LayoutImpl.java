package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.css.StyleClassBuilderWidth;
import org.cyk.utility.device.Device;
import org.cyk.utility.string.StringHelper;

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
		return addItemFromClass(__inject__(StringHelper.class).concatenate(classes,CharacterConstant.SPACE.toString()));
	}
	
	@Override
	public Layout addItemFromClasses(String... classes) {
		return addItemFromClasses(__inject__(CollectionHelper.class).instanciate(classes));
	}
	
	@Override
	public Layout addItemFromWidthClassBuilders(Collection<StyleClassBuilderWidth> styleClassBuilderWidths) {
		Collection<String> classes = new LinkedHashSet<>();
		if(__inject__(CollectionHelper.class).isNotEmpty(styleClassBuilderWidths))
			for(StyleClassBuilderWidth index : styleClassBuilderWidths)
				classes.add(index.execute().getOutput());
		return addItemFromClasses(classes);
	}
	
	@Override
	public Layout addItemFromWidthClassBuilders(StyleClassBuilderWidth... styleClassBuilderWidths) {
		return addItemFromWidthClassBuilders(__inject__(CollectionHelper.class).instanciate(styleClassBuilderWidths));
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
