package org.cyk.utility.client.controller.component;

import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.device.Device;

public interface VisibleComponentAreaDimension extends Objectable {

	Map<Class<? extends Device>,Integer> getProportionMap();
	Map<Class<? extends Device>,Integer> getProportionMap(Boolean injectIfNull);
	VisibleComponentAreaDimension setProportionMap(Map<Class<? extends Device>,Integer> proportionMap);
	VisibleComponentAreaDimension setProportions(Integer proportion,Collection<Class<? extends Device>> classes);
	VisibleComponentAreaDimension setProportions(Integer proportion,Class<? extends Device>...classes);
	VisibleComponentAreaDimension setProportions(Integer _default,Integer television,Integer desktop,Integer tablet,Integer phone);
	
}
