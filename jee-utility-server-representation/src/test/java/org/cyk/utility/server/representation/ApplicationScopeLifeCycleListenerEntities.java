package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.persistence.AbstractApplicationScopeLifeCycleListenerEntities;

@ApplicationScoped
public class ApplicationScopeLifeCycleListenerEntities extends AbstractApplicationScopeLifeCycleListenerEntities implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __destroy__(Object object) {}
	
	/**/
	
}