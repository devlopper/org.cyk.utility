package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.device.Device;
import org.cyk.utility.device.DeviceDesktop;
import org.cyk.utility.device.DevicePhone;
import org.cyk.utility.device.DeviceTablet;
import org.cyk.utility.device.DeviceTelevision;

public class LayoutBuilerItemImpl extends AbstractObject implements LayoutBuilerItem,Serializable {
	private static final long serialVersionUID = 1L;

	private Map<Class<? extends Device>, Integer> widthMap;
	
	@Override
	public Map<Class<? extends Device>, Integer> getWidthMap() {
		return widthMap;
	}

	@Override
	public LayoutBuilerItem setWidthMap(Map<Class<? extends Device>, Integer> widthMap) {
		this.widthMap = widthMap;
		return this;
	}

	@Override
	public LayoutBuilerItem setWidth(Integer width, @SuppressWarnings("unchecked") Class<? extends Device>...deviceClasses) {
		Map<Class<? extends Device>, Integer> widthMap = getWidthMap();
		if(widthMap == null)
			setWidthMap(widthMap = new LinkedHashMap<>());
		if(deviceClasses == null)
			widthMap.put(null, width);
		else
			for(Class<? extends Device> index : deviceClasses)
				widthMap.put(index, width);
		return this;
	}

	@Override
	public LayoutBuilerItem setWidth(Integer width) {
		return setWidth(width, (Class<? extends Device>[])null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LayoutBuilerItem setWidthForOther(Integer width) {
		return setWidth(width, DeviceTelevision.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LayoutBuilerItem setWidthForDesktop(Integer width) {
		return setWidth(width, DeviceDesktop.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LayoutBuilerItem setWidthForTablet(Integer width) {
		return setWidth(width, DeviceTablet.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LayoutBuilerItem setWidthForPhone(Integer width) {
		return setWidth(width, DevicePhone.class);
	}
}
