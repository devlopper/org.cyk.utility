package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.css.StyleClassBuilderWidth;
import org.cyk.utility.device.Device;
import org.cyk.utility.device.DeviceDesktop;
import org.cyk.utility.device.DevicePhone;
import org.cyk.utility.device.DeviceTablet;

public abstract class AbstractStyleClassBuilderWidthPrimefacesGridCssFunctionRunnableImpl extends AbstractFunctionRunnableImpl<StyleClassBuilderWidth> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public AbstractStyleClassBuilderWidthPrimefacesGridCssFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				setOutput(String.format(__getFormatPrefix__()+"-%s-%s", __getDeviceClass__(getFunction().getDevice()),__getWidth__(getFunction().getWidth())));
			}
		});
	}
	
	protected abstract String __getFormatPrefix__();
	
	protected abstract String __getDeviceClassWhenNull__();
	
	protected String __getDeviceClass__(Device device) {
		String deviceClass = null;
		if(device == null)
			deviceClass = __getDeviceClassWhenNull__();
		else if(device instanceof DeviceDesktop)
			deviceClass = "lg";
		else if(device instanceof DeviceTablet)
			deviceClass = "md";
		else if(device instanceof DevicePhone)
			deviceClass = "sm";
		else
			deviceClass = "xl";
		return deviceClass;
	}
	
	protected Integer __getWidth__(Integer width) {
		if(width == null)
			width = 12;
		return width;
	}
}