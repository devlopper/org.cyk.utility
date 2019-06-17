package org.cyk.utility.__kernel__;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.enterprise.inject.AmbiguousResolutionException;

import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;
import org.junit.jupiter.api.Test;

public class BeanDiscoveryModeAllUnitTest extends AbstractBeanDiscoveryModeUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected BeanDiscoveryMode __getBeanDiscoveryMode__() {
		return BeanDiscoveryMode.ALL;
	}
	
	@Test
	public void inject_I_isNotNull(){
		assertThrows(AmbiguousResolutionException.class, () -> {__inject__(IAmbiguous.class);});
	}
	
}
