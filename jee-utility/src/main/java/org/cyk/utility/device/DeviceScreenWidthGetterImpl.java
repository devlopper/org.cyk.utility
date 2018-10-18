package org.cyk.utility.device;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class DeviceScreenWidthGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Number> implements DeviceScreenWidthGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<? extends Device> deviceClass;
	
	@Override
	public Class<? extends Device> getDeviceClass() {
		return deviceClass;
	}

	@Override
	public DeviceScreenWidthGetter setDeviceClass(Class<? extends Device> deviceClass) {
		this.deviceClass = deviceClass;
		return this;
	}
	
}
