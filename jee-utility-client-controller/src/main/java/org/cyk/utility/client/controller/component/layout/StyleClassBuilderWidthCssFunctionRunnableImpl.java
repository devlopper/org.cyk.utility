package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.css.StyleClassBuilderWidth;
import org.cyk.utility.device.Device;
import org.cyk.utility.device.DeviceDesktop;
import org.cyk.utility.device.DevicePhone;
import org.cyk.utility.device.DeviceTablet;

public class StyleClassBuilderWidthCssFunctionRunnableImpl extends AbstractFunctionRunnableImpl<StyleClassBuilderWidth> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public StyleClassBuilderWidthCssFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				setOutput(String.format(__getFormatPrefix__()+FORMAT_SUFFIX, __getDeviceClass__(getFunction().getDevice()),__getWidth__(getFunction().getWidth())));
			}
		});
	}
	
	protected String __getFormatPrefix__() {
		return FORMAT_PREFIX;
	}
	
	protected String __getDeviceClassWhenNull__() {
		return DEVICE_CLASS_DEFAULT;
	}
	
	protected String __getDeviceClass__(Device device) {
		String deviceClass = null;
		if(device == null)
			deviceClass = __getDeviceClassWhenNull__();
		else if(device instanceof DeviceDesktop)
			deviceClass = DEVICE_CLASS_TOKEN_DESKTOP;
		else if(device instanceof DeviceTablet)
			deviceClass = DEVICE_CLASS_TOKEN_TABLET;
		else if(device instanceof DevicePhone)
			deviceClass = DEVICE_CLASS_TOKEN_PHONE;
		else
			deviceClass = DEVICE_CLASS_TOKEN_LARGE;
		return deviceClass;
	}
	
	protected Integer __getWidth__(Integer width) {
		if(width == null)
			width = WIDTH;
		return width;
	}
	
	/**/
	
	public static Integer WIDTH = 12;
	public static String FORMAT_PREFIX = "ui";
	public static String FORMAT_SUFFIX = "-%s-%s";
	
	public static String DEVICE_CLASS_DEFAULT = "g";
	public static String DEVICE_CLASS_TOKEN_DESKTOP = "lg";
	public static String DEVICE_CLASS_TOKEN_TABLET = "md";
	public static String DEVICE_CLASS_TOKEN_PHONE = "sm";
	public static String DEVICE_CLASS_TOKEN_LARGE = "xl";
}