package org.cyk.utility.css;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.css.CascadeStyleSheetHelper.StyleClassProportionGrid;
import org.cyk.utility.device.DeviceDesktop;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class CascadeStyleSheetHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		__inject__(CascadeStyleSheetHelper.class).setStyleClassProportionGrid(StyleClassProportionGrid.PRIMEFACES_GRID_CSS);
	}
	
	@Test
	public void buildStyleClassProportion_primefacesGridCss_device_null_propertion_null() {
		assertThat(__inject__(CascadeStyleSheetHelper.class).buildStyleClassProportion(null,null)).isEqualTo("ui-g-12");
	}
	
	@Test
	public void buildStyleClassProportion_primefacesGridCss_device_null_propertion_1() {
		assertThat(__inject__(CascadeStyleSheetHelper.class).buildStyleClassProportion(null,1)).isEqualTo("ui-g-1");
	}
	
	@Test
	public void buildStyleClassProportion_primefacesGridCss_device_desktop_propertion_null() {
		assertThat(__inject__(CascadeStyleSheetHelper.class).buildStyleClassProportion(DeviceDesktop.class,null)).isEqualTo("ui-lg-12");
	}
	
	@Test
	public void buildStyleClassProportion_primefacesGridCss_device_desktop_propertion_1() {
		assertThat(__inject__(CascadeStyleSheetHelper.class).buildStyleClassProportion(DeviceDesktop.class,1)).isEqualTo("ui-lg-1");
	}
}
