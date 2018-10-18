package org.cyk.utility.client.controller.component.layout;

import org.cyk.utility.device.Device;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface LayoutWidthGetter extends FunctionWithPropertiesAsInput<Number> {

	Class<? extends Device> getDeviceClass();
	LayoutWidthGetter setDeviceClass(Class<? extends Device> deviceClass);
	
}
