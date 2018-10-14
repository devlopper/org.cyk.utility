package org.cyk.utility.client.controller.component.layout;

import java.util.Map;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.device.Device;

public interface LayoutBuilerItem extends Objectable {

	Map<Class<? extends Device>,Integer> getWidthMap();
	LayoutBuilerItem setWidthMap(Map<Class<? extends Device>,Integer> widthMap);
	LayoutBuilerItem setWidth(Integer width,@SuppressWarnings("unchecked") Class<? extends Device>...deviceClasses);
	LayoutBuilerItem setWidth(Integer width);
	
	LayoutBuilerItem setWidthForOther(Integer width);
	LayoutBuilerItem setWidthForDesktop(Integer width);
	LayoutBuilerItem setWidthForTablet(Integer width);
	LayoutBuilerItem setWidthForPhone(Integer width);
}
