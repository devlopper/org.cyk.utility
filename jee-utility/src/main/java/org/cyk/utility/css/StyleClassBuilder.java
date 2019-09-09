package org.cyk.utility.css;

import org.cyk.utility.device.Device;
import org.cyk.utility.function.FunctionWithPropertiesAsInputAndStringAsOutput;

@Deprecated
public interface StyleClassBuilder extends FunctionWithPropertiesAsInputAndStringAsOutput {

	Device getDevice();
	StyleClassBuilder setDevice(Device device);
	
}
