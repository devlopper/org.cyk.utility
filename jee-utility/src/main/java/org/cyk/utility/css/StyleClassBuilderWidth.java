package org.cyk.utility.css;

import org.cyk.utility.device.Device;

public interface StyleClassBuilderWidth extends StyleClassBuilder {

	Integer getWidth();
	StyleClassBuilderWidth setWidth(Integer width);
	
	@Override StyleClassBuilderWidth setDevice(Device device);
}
