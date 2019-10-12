package org.cyk.utility.css;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.function.FunctionRunnable;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.device.Device;
import org.cyk.utility.device.DeviceDesktop;
import org.cyk.utility.device.DevicePhone;
import org.cyk.utility.device.DeviceTablet;
import org.cyk.utility.device.DeviceTelevision;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Deprecated @Disabled
public class StyleClassBuilderWidthUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	/* Primefaces Grid Css */
	
	@Test
	public void getClass_primefacesGridCss_deviceNull_widthNull() {
		assertClass(StyleClassBuilderWidthPrimefacesGridCssFunctionRunnableImpl.class,null,null,"ui-g-12");
	}
	
	@Test
	public void getClass_primefacesGridCss_deviceDesktop_widthNull() {
		assertClass(StyleClassBuilderWidthPrimefacesGridCssFunctionRunnableImpl.class,DeviceDesktop.class,null,"ui-lg-12");
	}
	
	@Test
	public void getClass_primefacesGridCss_deviceTablet_widthNull() {
		assertClass(StyleClassBuilderWidthPrimefacesGridCssFunctionRunnableImpl.class,DeviceTablet.class,null,"ui-md-12");
	}
	
	@Test
	public void getClass_primefacesGridCss_devicePhone_widthNull() {
		assertClass(StyleClassBuilderWidthPrimefacesGridCssFunctionRunnableImpl.class,DevicePhone.class,null,"ui-sm-12");
	}
	
	@Test
	public void getClass_primefacesGridCss_deviceOther_widthNull() {
		assertClass(StyleClassBuilderWidthPrimefacesGridCssFunctionRunnableImpl.class,DeviceTelevision.class,null,"ui-xl-12");
	}
	
	@Test
	public void getClass_primefacesGridCss_deviceNull_width1() {
		assertClass(StyleClassBuilderWidthPrimefacesGridCssFunctionRunnableImpl.class,null,1,"ui-g-1");
	}
	
	@Test
	public void getClass_primefacesGridCss_deviceDesktop_width1() {
		assertClass(StyleClassBuilderWidthPrimefacesGridCssFunctionRunnableImpl.class,DeviceDesktop.class,1,"ui-lg-1");
	}
	
	@Test
	public void getClass_primefacesGridCss_deviceTablet_width1() {
		assertClass(StyleClassBuilderWidthPrimefacesGridCssFunctionRunnableImpl.class,DeviceTablet.class,1,"ui-md-1");
	}
	
	@Test
	public void getClass_primefacesGridCss_devicePhone_width1() {
		assertClass(StyleClassBuilderWidthPrimefacesGridCssFunctionRunnableImpl.class,DevicePhone.class,1,"ui-sm-1");
	}
	
	@Test
	public void getClass_primefacesGridCss_deviceOther_width1() {
		assertClass(StyleClassBuilderWidthPrimefacesGridCssFunctionRunnableImpl.class,DeviceTelevision.class,1,"ui-xl-1");
	}
	
	/* Primefaces Flex Grid Css */
	
	@Test
	public void getClass_primefacesFlexGridCss_deviceNull_widthNull() {
		assertClass(StyleClassBuilderWidthPrimefacesFlexGridCssFunctionRunnableImpl.class,null,null,"p-col-12");
	}
	
	@Test
	public void getClass_primefacesFlexGridCss_deviceDesktop_widthNull() {
		assertClass(StyleClassBuilderWidthPrimefacesFlexGridCssFunctionRunnableImpl.class,DeviceDesktop.class,null,"p-lg-12");
	}
	
	@Test
	public void getClass_primefacesFlexGridCss_deviceTablet_widthNull() {
		assertClass(StyleClassBuilderWidthPrimefacesFlexGridCssFunctionRunnableImpl.class,DeviceTablet.class,null,"p-md-12");
	}
	
	@Test
	public void getClass_primefacesFlexGridCss_devicePhone_widthNull() {
		assertClass(StyleClassBuilderWidthPrimefacesFlexGridCssFunctionRunnableImpl.class,DevicePhone.class,null,"p-sm-12");
	}
	
	@Test
	public void getClass_primefacesFlexGridCss_deviceOther_widthNull() {
		assertClass(StyleClassBuilderWidthPrimefacesFlexGridCssFunctionRunnableImpl.class,DeviceTelevision.class,null,"p-xl-12");
	}
	
	@Test
	public void getClass_primefacesFlexGridCss_deviceNull_width1() {
		assertClass(StyleClassBuilderWidthPrimefacesFlexGridCssFunctionRunnableImpl.class,null,1,"p-col-1");
	}
	
	@Test
	public void getClass_primefacesFlexGridCss_deviceDesktop_width1() {
		assertClass(StyleClassBuilderWidthPrimefacesFlexGridCssFunctionRunnableImpl.class,DeviceDesktop.class,1,"p-lg-1");
	}
	
	@Test
	public void getClass_primefacesFlexGridCss_deviceTablet_width1() {
		assertClass(StyleClassBuilderWidthPrimefacesFlexGridCssFunctionRunnableImpl.class,DeviceTablet.class,1,"p-md-1");
	}
	
	@Test
	public void getClass_primefacesFlexGridCss_devicePhone_width1() {
		assertClass(StyleClassBuilderWidthPrimefacesFlexGridCssFunctionRunnableImpl.class,DevicePhone.class,1,"p-sm-1");
	}
	
	@Test
	public void getClass_primefacesFlexGridCss_deviceOther_width1() {
		assertClass(StyleClassBuilderWidthPrimefacesFlexGridCssFunctionRunnableImpl.class,DeviceTelevision.class,1,"p-xl-1");
	}
	
	/**/
	
	private void assertClass(Class<?> styleClassBuilderWidthPrimefacesGridCssFunctionRunnableClass,Class<? extends Device> deviceClass,Integer width,String expectedClass) {
		__inject__(FunctionRunnableMap.class).set(StyleClassBuilderWidthImpl.class, (Class<? extends FunctionRunnable<?>>) styleClassBuilderWidthPrimefacesGridCssFunctionRunnableClass,null,Boolean.TRUE);
		StyleClassBuilderWidth builder = __inject__(StyleClassBuilderWidth.class);
		if(deviceClass!=null)
			builder.setDevice(__inject__(deviceClass));
		builder.setWidth(width);
		assertionHelper.assertEquals(expectedClass, builder.execute().getOutput());
	}
	
	/**/
	
	public static class StyleClassBuilderWidthPrimefacesGridCssFunctionRunnableImpl extends AbstractFunctionRunnableImpl<StyleClassBuilderWidth> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public StyleClassBuilderWidthPrimefacesGridCssFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					Device device = getFunction().getDevice();
					String deviceClass = null;
					if(device == null)
						deviceClass = "g";
					else if(device instanceof DeviceDesktop)
						deviceClass = "lg";
					else if(device instanceof DeviceTablet)
						deviceClass = "md";
					else if(device instanceof DevicePhone)
						deviceClass = "sm";
					else
						deviceClass = "xl";
					Integer width = getFunction().getWidth();
					if(width == null)
						width = 12;
					setOutput(String.format("ui-%s-%s", deviceClass,width));
				}
			});
		}
	}
	
	public static class StyleClassBuilderWidthPrimefacesFlexGridCssFunctionRunnableImpl extends AbstractFunctionRunnableImpl<StyleClassBuilderWidth> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public StyleClassBuilderWidthPrimefacesFlexGridCssFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					Device device = getFunction().getDevice();
					String deviceClass = null;
					if(device == null)
						deviceClass = "col";
					else if(device instanceof DeviceDesktop)
						deviceClass = "lg";
					else if(device instanceof DeviceTablet)
						deviceClass = "md";
					else if(device instanceof DevicePhone)
						deviceClass = "sm";
					else
						deviceClass = "xl";
					Integer width = getFunction().getWidth();
					if(width == null)
						width = 12;
					setOutput(String.format("p-%s-%s", deviceClass,width));
				}
			});
		}
	}
}
