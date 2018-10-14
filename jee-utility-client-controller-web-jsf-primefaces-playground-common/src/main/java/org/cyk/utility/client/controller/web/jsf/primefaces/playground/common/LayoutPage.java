package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;
import org.cyk.utility.device.DeviceDesktop;
import org.cyk.utility.device.DevicePhone;
import org.cyk.utility.device.DeviceTablet;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class LayoutPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private Layout formResponsive01;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		
		formResponsive01.addItemFromDeviceClassAndWidths(null,2,DeviceDesktop.class,2,DeviceTablet.class,2,DevicePhone.class,12);
		formResponsive01.addItemFromDeviceClassAndWidths(null,10,DeviceDesktop.class,10,DeviceTablet.class,10,DevicePhone.class,12);
		
		formResponsive01.addItemFromDeviceClassAndWidths(null,2,DeviceDesktop.class,2,DeviceTablet.class,2,DevicePhone.class,12);
		formResponsive01.addItemFromDeviceClassAndWidths(null,10,DeviceDesktop.class,10,DeviceTablet.class,10,DevicePhone.class,12);
		
		formResponsive01.addItemFromDeviceClassAndWidths(null,2,DeviceDesktop.class,2,DeviceTablet.class,2,DevicePhone.class,12);
		formResponsive01.addItemFromDeviceClassAndWidths(null,10,DeviceDesktop.class,10,DeviceTablet.class,10,DevicePhone.class,12);
			
	}
	
}
