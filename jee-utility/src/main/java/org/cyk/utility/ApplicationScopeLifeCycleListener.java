package org.cyk.utility;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		initialize();
	}
	
	public static void initialize() {
		initialize(ApplicationScopeLifeCycleListener.class, new Runnable() {
			@Override
			public void run() {
				org.cyk.utility.__kernel__.ApplicationScopeLifeCycleListener.initialize();
			}
		});
	}

	@Override
	public void __destroy__(Object object) {
		
	}
	
	/**/
	
	
	
	/**/
	
	@Deprecated
	public static final Integer LEVEL = new Integer("0");
	
}
