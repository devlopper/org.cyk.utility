package org.cyk.utility.client.controller.component;

import java.util.Collection;
import java.util.Map;

import org.cyk.utility.css.StyleBuilder;
import org.cyk.utility.device.Device;

public interface VisibleComponentBuilder<COMPONENT extends VisibleComponent> extends ComponentBuilder<COMPONENT> {
	
	VisibleComponentArea getArea();
	VisibleComponentArea getArea(Boolean injectIfNull);
	VisibleComponentBuilder<COMPONENT> setArea(VisibleComponentArea area);
	
	VisibleComponentBuilder<COMPONENT> setAreaWidth(VisibleComponentAreaDimension areaWidth);
	
	VisibleComponentBuilder<COMPONENT> setAreaWidthProportionMap(Map<Class<? extends Device>,Integer> proportionMap);
	VisibleComponentBuilder<COMPONENT> setAreaWidthProportions(Integer proportion,Collection<Class<? extends Device>> classes);
	VisibleComponentBuilder<COMPONENT> setAreaWidthProportions(Integer proportion,Class<? extends Device>...classes);
	VisibleComponentBuilder<COMPONENT> setAreaWidthProportions(Integer _default,Integer television,Integer desktop,Integer tablet,Integer phone);
	VisibleComponentBuilder<COMPONENT> setAreaWidthProportionsForNotPhone(Integer width);
	
	StyleBuilder getStyle();
	StyleBuilder getStyle(Boolean injectIfNull);
	VisibleComponentBuilder<COMPONENT> setStyle(StyleBuilder styleBuilder);
	
	@Override ComponentBuilder<COMPONENT> setOutputProperty(Object key, Object value);
}
