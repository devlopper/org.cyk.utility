package org.cyk.utility;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.string.repository.StringRepositoryResourceBundle;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __initialize__(Object object) {
		for(String index : new String[] {"word","phrase"})
			__inject__(StringRepositoryResourceBundle.class).addBundle("org.cyk.utility.string.repository."+index);
	}
	
	@Override
	protected void __destroy__(Object object) {}
	
}
