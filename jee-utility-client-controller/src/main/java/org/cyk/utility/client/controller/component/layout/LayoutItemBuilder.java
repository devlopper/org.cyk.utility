package org.cyk.utility.client.controller.component.layout;

import java.util.Collection;

import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.device.DeviceScreenArea;
import org.cyk.utility.device.DeviceScreenDimensionProportions;

public interface LayoutItemBuilder extends VisibleComponentBuilder<LayoutItem> {
	
	@Override LayoutItemBuilder addRoles(Collection<ComponentRole> roles);
	@Override LayoutItemBuilder addRoles(ComponentRole... roles);
	
	DeviceScreenDimensionProportions getAreaWidthProportions();
	DeviceScreenDimensionProportions getAreaWidthProportions(Boolean injectIfNull);
	LayoutItemBuilder setAreaWidthProportionsAllClasses(Integer _default,Integer television,Integer desktop,Integer tablet,Integer phone);
	LayoutItemBuilder setAreaWidthProportionsAll(Integer value);
	LayoutItemBuilder setAreaWidthProportionsDefault(Integer value);
	LayoutItemBuilder setAreaWidthProportionsPhone(Integer value);
	LayoutItemBuilder setAreaWidthProportionsNotPhone(Integer value);
	LayoutItemBuilder setAreaWidthProportionsTablet(Integer value);
	LayoutItemBuilder setAreaWidthProportionsDesktop(Integer value);
	LayoutItemBuilder setAreaWidthProportionsTelevision(Integer value);
	
	LayoutItemBuilder addStyleClasses(String...classes);
	
	LayoutBuilder getLayout();
	LayoutItemBuilder setLayout(LayoutBuilder layout);
	
	@Override LayoutItemBuilder setOutputPropertyValue(Object value);
	
	@Override LayoutItemBuilder setArea(DeviceScreenArea area);
}
