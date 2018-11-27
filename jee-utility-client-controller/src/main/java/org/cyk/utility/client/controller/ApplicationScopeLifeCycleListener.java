package org.cyk.utility.client.controller;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.proxy.ProxyGetterImpl;
import org.cyk.utility.client.controller.proxy.ProxyGetterRestEasyFunctionRunnableImpl;
import org.cyk.utility.instance.InstanceGetterImpl;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		__inject__(FunctionRunnableMap.class).set(InstanceGetterImpl.class, InstanceGetterFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(ProxyGetterImpl.class, ProxyGetterRestEasyFunctionRunnableImpl.class);
	}
	
	@Override
	public void __destroy__(Object object) {}
	
}
