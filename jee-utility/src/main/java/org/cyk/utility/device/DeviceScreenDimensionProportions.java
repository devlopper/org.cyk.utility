package org.cyk.utility.device;

import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface DeviceScreenDimensionProportions extends Objectable {

	Map<Class<? extends Device>,Integer> getMap();
	Map<Class<? extends Device>,Integer> getMap(Boolean instanciateIfNull);
	DeviceScreenDimensionProportions setMap(Map<Class<? extends Device>,Integer> map);
	
	DeviceScreenDimensionProportions setByClasses(Integer value,Collection<Class<? extends Device>> classes);
	DeviceScreenDimensionProportions setByClasses(Integer value,Class<? extends Device>...classes);
	DeviceScreenDimensionProportions setAllClasses(Integer _default,Integer television,Integer desktop,Integer tablet,Integer phone);
	
	DeviceScreenDimensionProportions setPhone(Integer value);
	DeviceScreenDimensionProportions setTablet(Integer value);
	DeviceScreenDimensionProportions setDesktop(Integer value);
	DeviceScreenDimensionProportions setTelevision(Integer value);
	DeviceScreenDimensionProportions setDefault(Integer value);
	DeviceScreenDimensionProportions setAll(Integer value);
	
}