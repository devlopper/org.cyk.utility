package org.cyk.utility.device;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface DeviceScreenArea extends Objectable {
	
	DeviceScreenDimensionProportions getWidthProportions();
	DeviceScreenDimensionProportions getWidthProportions(Boolean injectIfNull);
	DeviceScreenArea setWidthProportions(DeviceScreenDimensionProportions widthProportions);
	
	DeviceScreenDimensionProportions getHeightProportions();
	DeviceScreenDimensionProportions getHeightProportions(Boolean injectIfNull);
	DeviceScreenArea setHeightProportions(DeviceScreenDimensionProportions heightProportions);
	
}
