package org.cyk.utility.common.cdi;

import java.io.Serializable;

import javax.inject.Inject;

public abstract class AbstractStartupBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -6732016930047737820L;
	
	@Inject protected StartupBeanExtension startupBeanExtension;

}
