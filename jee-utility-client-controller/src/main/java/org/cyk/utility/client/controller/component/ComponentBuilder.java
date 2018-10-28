package org.cyk.utility.client.controller.component;

import java.util.Collection;
import java.util.Map;

import org.cyk.utility.css.StyleBuilder;
import org.cyk.utility.device.Device;
import org.cyk.utility.device.DeviceScreenArea;
import org.cyk.utility.device.DeviceScreenDimensionProportions;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface ComponentBuilder<COMPONENT extends Component> extends FunctionWithPropertiesAsInput<COMPONENT> {
	
	Class<COMPONENT> getComponentClass();
	ComponentBuilder<COMPONENT> setComponentClass(Class<COMPONENT> componentClass);
	
	ComponentBuilder<COMPONENT> setOutputProperty(Object key,Object value);
	ComponentBuilder<COMPONENT> setOutputPropertyName(Object value);
	ComponentBuilder<COMPONENT> setOutputPropertyValue(Object value);
	
	DeviceScreenArea getArea();
	DeviceScreenArea getArea(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setArea(DeviceScreenArea area);
	
	ComponentBuilder<COMPONENT> setAreaWidth(DeviceScreenDimensionProportions areaWidth);
	
	ComponentBuilder<COMPONENT> setAreaWidthProportionMap(Map<Class<? extends Device>,Integer> proportionMap);
	ComponentBuilder<COMPONENT> setAreaWidthProportions(Integer proportion,Collection<Class<? extends Device>> classes);
	ComponentBuilder<COMPONENT> setAreaWidthProportions(Integer proportion,Class<? extends Device>...classes);
	ComponentBuilder<COMPONENT> setAreaWidthProportions(Integer _default,Integer television,Integer desktop,Integer tablet,Integer phone);
	ComponentBuilder<COMPONENT> setAreaWidthProportionsForNotPhone(Integer width);
	
	StyleBuilder getLayoutItemStyle();
	StyleBuilder getLayoutItemStyle(Boolean injectIfNull);
	ComponentBuilder<COMPONENT> setLayoutItemStyle(StyleBuilder style);
	ComponentBuilder<COMPONENT> addLayoutItemStyleClasses(String...classes);
}
