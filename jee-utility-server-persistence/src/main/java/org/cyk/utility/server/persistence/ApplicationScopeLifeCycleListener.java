package org.cyk.utility.server.persistence;

import java.io.Serializable;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;

@Deprecated //implementation will only trigger needed initialisations
//@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		//__inject__(FunctionRunnableMap.class).set(InstanceGetterImpl.class, InstanceGetterFunctionRunnableImpl.class);
	}
	
	@Override
	public void __destroy__(Object object) {}
	
}
