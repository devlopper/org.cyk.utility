package org.cyk.utility.device;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

@Dependent
public class DeviceScreenAreaImpl extends AbstractObject implements DeviceScreenArea,Serializable{
	private static final long serialVersionUID = 1L;

	private DeviceScreenDimensionProportions widthProportions,heightProportions;
	
	@Override
	public DeviceScreenDimensionProportions getWidthProportions() {
		return widthProportions;
	}

	@Override
	public DeviceScreenDimensionProportions getWidthProportions(Boolean injectIfNull) {
		return (DeviceScreenDimensionProportions) __getInjectIfNull__(FIELD_WIDTH_PROPORTIONS, injectIfNull);
	}

	@Override
	public DeviceScreenArea setWidthProportions(DeviceScreenDimensionProportions widthProportions) {
		this.widthProportions = widthProportions;
		return this;
	}

	@Override
	public DeviceScreenDimensionProportions getHeightProportions() {
		return heightProportions;
	}

	@Override
	public DeviceScreenDimensionProportions getHeightProportions(Boolean injectIfNull) {
		return (DeviceScreenDimensionProportions) __getInjectIfNull__(FIELD_HEIGHT_PROPORTIONS, injectIfNull);
	}

	@Override
	public DeviceScreenArea setHeightProportions(DeviceScreenDimensionProportions heightProportions) {
		this.heightProportions = heightProportions;
		return this;
	}

	public static final String FIELD_WIDTH_PROPORTIONS = "widthProportions";
	public static final String FIELD_HEIGHT_PROPORTIONS = "heightProportions";
	
}
