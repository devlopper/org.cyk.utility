package org.cyk.utility.device;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface DeviceScreenWidthGetter extends FunctionWithPropertiesAsInput<Number> {

	Class<? extends Device> getDeviceClass();
	DeviceScreenWidthGetter setDeviceClass(Class<? extends Device> deviceClass);
	
}
