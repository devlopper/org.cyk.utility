package org.cyk.utility.device;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class DeviceScreenDimensionProportionsUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void isNull() {
		DeviceScreenDimensionProportions proportions = __inject__(DeviceScreenDimensionProportions.class);
		assertThat(proportions.getMap()).isNull();
	}
	
	@Test
	public void isDefaultOnly() {
		DeviceScreenDimensionProportions proportions = __inject__(DeviceScreenDimensionProportions.class).setDefault(3);
		assertThat(proportions.getMap()).containsOnly(entry(null,3));
	}
	
	@Test
	public void isPhoneOnly() {
		DeviceScreenDimensionProportions proportions = __inject__(DeviceScreenDimensionProportions.class).setPhone(3);
		assertThat(proportions.getMap()).containsOnly(entry(DevicePhone.class,3));
	}
	
	@Test
	public void isTabletOnly() {
		DeviceScreenDimensionProportions proportions = __inject__(DeviceScreenDimensionProportions.class).setTablet(3);
		assertThat(proportions.getMap()).containsOnly(entry(DeviceTablet.class,3));
	}
	
	@Test
	public void isDesktopOnly() {
		DeviceScreenDimensionProportions proportions = __inject__(DeviceScreenDimensionProportions.class).setDesktop(3);
		assertThat(proportions.getMap()).containsOnly(entry(DeviceDesktop.class,3));
	}
	
	@Test
	public void isTelevisionOnly() {
		DeviceScreenDimensionProportions proportions = __inject__(DeviceScreenDimensionProportions.class).setTelevision(3);
		assertThat(proportions.getMap()).containsOnly(entry(DeviceTelevision.class,3));
	}
	
	@Test
	public void isAll() {
		DeviceScreenDimensionProportions proportions = __inject__(DeviceScreenDimensionProportions.class).setAll(3);
		assertThat(proportions.getMap()).containsOnly(entry(null,3),entry(DeviceTelevision.class,3),entry(DeviceDesktop.class,3)
				,entry(DeviceTablet.class,3),entry(DevicePhone.class,3));
	}
}
