package org.cyk.utility.css;

import java.io.Serializable;

import org.cyk.utility.device.Device;

public class StyleClassBuilderWidthImpl extends AbstractStyleClassBuilderImpl implements StyleClassBuilderWidth,Serializable {
	private static final long serialVersionUID = -2220084599379512598L;

	private Integer width;
	
	@Override
	public Integer getWidth() {
		return width;
	}

	@Override
	public StyleClassBuilderWidth setWidth(Integer width) {
		this.width = width;
		return this;
	}

	@Override
	public StyleClassBuilderWidth setDevice(Device device) {
		return (StyleClassBuilderWidth) super.setDevice(device);
	}
}
