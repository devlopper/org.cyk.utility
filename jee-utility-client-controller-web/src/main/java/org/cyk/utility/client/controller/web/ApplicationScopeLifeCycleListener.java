package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.session.SessionAttributeGetterImpl;
import org.cyk.utility.client.controller.session.SessionAttributeSetterImpl;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		__inject__(FunctionRunnableMap.class).set(SessionAttributeSetterImpl.class, SessionAttributeSetterFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(SessionAttributeGetterImpl.class, SessionAttributeGetterFunctionRunnableImpl.class);
	}
	
	@Override
	public void __destroy__(Object object) {}
	
}
