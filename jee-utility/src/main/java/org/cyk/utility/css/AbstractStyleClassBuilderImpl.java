package org.cyk.utility.css;

import java.io.Serializable;

import org.cyk.utility.device.Device;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl;

@Deprecated
public abstract class AbstractStyleClassBuilderImpl extends AbstractFunctionWithPropertiesAsInputAndStringAsOutputImpl implements StyleClassBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Device device;
	
	@Override
	public Device getDevice() {
		return device;
	}
	
	@Override
	public StyleClassBuilder setDevice(Device device) {
		this.device = device;
		return this;
	}
	
}
