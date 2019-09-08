package org.cyk.utility.device;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.DependencyInjection;
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
		if(widthProportions == null && Boolean.TRUE.equals(injectIfNull))
			widthProportions = DependencyInjection.inject(DeviceScreenDimensionProportions.class);
		return widthProportions;
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
		if(heightProportions == null && Boolean.TRUE.equals(injectIfNull))
			heightProportions = DependencyInjection.inject(DeviceScreenDimensionProportions.class);
		return heightProportions;
	}

	@Override
	public DeviceScreenArea setHeightProportions(DeviceScreenDimensionProportions heightProportions) {
		this.heightProportions = heightProportions;
		return this;
	}

	public static final String FIELD_WIDTH_PROPORTIONS = "widthProportions";
	public static final String FIELD_HEIGHT_PROPORTIONS = "heightProportions";
	
}
