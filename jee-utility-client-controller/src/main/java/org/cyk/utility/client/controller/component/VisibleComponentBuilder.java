package org.cyk.utility.client.controller.component;

import java.util.Collection;
import java.util.Map;

import org.cyk.utility.css.StyleBuilder;
import org.cyk.utility.device.Device;
import org.cyk.utility.device.DeviceScreenArea;
import org.cyk.utility.device.DeviceScreenDimensionProportions;

public interface VisibleComponentBuilder<COMPONENT extends VisibleComponent> extends ComponentBuilder<COMPONENT> {
	
	DeviceScreenArea getArea();
	DeviceScreenArea getArea(Boolean injectIfNull);
	VisibleComponentBuilder<COMPONENT> setArea(DeviceScreenArea area);
	
	VisibleComponentBuilder<COMPONENT> setAreaWidth(DeviceScreenDimensionProportions areaWidth);
	
	VisibleComponentBuilder<COMPONENT> setAreaWidthProportionMap(Map<Class<? extends Device>,Integer> proportionMap);
	VisibleComponentBuilder<COMPONENT> setAreaWidthProportions(Integer proportion,Collection<Class<? extends Device>> classes);
	VisibleComponentBuilder<COMPONENT> setAreaWidthProportions(Integer proportion,Class<? extends Device>...classes);
	VisibleComponentBuilder<COMPONENT> setAreaWidthProportions(Integer _default,Integer television,Integer desktop,Integer tablet,Integer phone);
	VisibleComponentBuilder<COMPONENT> setAreaWidthProportionsForNotPhone(Integer width);
	
	StyleBuilder getStyle();
	StyleBuilder getStyle(Boolean injectIfNull);
	VisibleComponentBuilder<COMPONENT> setStyle(StyleBuilder styleBuilder);
	
	VisibleComponentBuilder<COMPONENT> addStyleClasses(String...classes);
	
	@Override ComponentBuilder<COMPONENT> setOutputProperty(Object key, Object value);
}
