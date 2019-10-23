package org.cyk.utility.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.log.LogLevel;
import org.cyk.utility.server.representation.AbstractRepresentationFunctionImpl;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		__inject__(org.cyk.utility.server.representation.ApplicationScopeLifeCycleListener.class).initialize(null);
		AbstractRepresentationFunctionImpl.LOG_LEVEL = LogLevel.TRACE;
	}
	
	@Override
	public void __destroy__(Object object) {}
}
