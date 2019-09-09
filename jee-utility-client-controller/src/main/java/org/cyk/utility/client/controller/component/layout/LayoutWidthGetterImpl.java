package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;

import org.cyk.utility.device.Device;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Deprecated
public class LayoutWidthGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Number> implements LayoutWidthGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<? extends Device> deviceClass;
	
	@Override
	public Class<? extends Device> getDeviceClass() {
		return deviceClass;
	}

	@Override
	public LayoutWidthGetter setDeviceClass(Class<? extends Device> deviceClass) {
		this.deviceClass = deviceClass;
		return this;
	}
	
}
