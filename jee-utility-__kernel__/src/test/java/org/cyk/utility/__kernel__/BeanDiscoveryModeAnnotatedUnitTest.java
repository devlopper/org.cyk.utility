package org.cyk.utility.__kernel__;

import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;

public class BeanDiscoveryModeAnnotatedUnitTest extends AbstractBeanDiscoveryModeUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected BeanDiscoveryMode __getBeanDiscoveryMode__() {
		return BeanDiscoveryMode.ANNOTATED;
	}
	
	
	
}
