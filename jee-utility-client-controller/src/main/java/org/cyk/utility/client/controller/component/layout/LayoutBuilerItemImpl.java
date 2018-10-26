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
	private Layout layout;
	
	@Override
	public Map<Class<? extends Device>, Integer> getWidthMap() {
		return widthMap;
	}
	
	@Override
	public Map<Class<? extends Device>, Integer> getWidthMap(Boolean instanciateIfNull) {
		return (Map<Class<? extends Device>, Integer>) __getInstanciateIfNull__(FIELD_WIDTH_MAP, instanciateIfNull);
	}

	@Override
	public LayoutBuilerItem setWidthMap(Map<Class<? extends Device>, Integer> widthMap) {
		this.widthMap = widthMap;
		return this;
	}

	@Override
	public LayoutBuilerItem setWidth(Integer width, Class<? extends Device>...deviceClasses) {
		Map<Class<? extends Device>, Integer> widthMap = getWidthMap();
		if(widthMap == null)
			setWidthMap(widthMap = new LinkedHashMap<>());
		if(deviceClasses == null)
			widthMap.put(null, width);
		else
			for(Class<? extends Device> index : deviceClasses) {
				widthMap.put(index, width);
			}
		return this;
	}

	@Override
	public LayoutBuilerItem setWidth(Integer width) {
		return setWidth(width, (Class<? extends Device>[])null);
	}

	@Override
	public LayoutBuilerItem setWidthForOther(Integer width) {
		return setWidth(width, DeviceTelevision.class);
	}
	
	@Override
	public LayoutBuilerItem setWidthForDesktop(Integer width) {
		return setWidth(width, DeviceDesktop.class);
	}
	
	@Override
	public LayoutBuilerItem setWidthForTablet(Integer width) {
		return setWidth(width, DeviceTablet.class);
	}
	
	@Override
	public LayoutBuilerItem setWidthForPhone(Integer width) {
		return setWidth(width, DevicePhone.class);
	}
	
	@Override
	public LayoutBuilerItem setWidthForAll(Integer width) {
		return setWidth(width, null,DevicePhone.class,DeviceTablet.class,DeviceDesktop.class,DeviceTelevision.class);
	}
	
	@Override
	public Layout getLayout() {
		return layout;
	}
	
	@Override
	public LayoutBuilerItem setLayout(Layout layout) {
		this.layout = layout;
		return this;
	}
	
	/**/
	
	public static final String FIELD_WIDTH_MAP = "widthMap";
}
