package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.client.controller.AbstractApplicationScopeLifeCycleListenerEntities;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMapGetter;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListenerEntities implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		super.__initialize__(object);
		__inject__(org.cyk.utility.playground.client.controller.entities.ApplicationScopeLifeCycleListener.class).initialize(null);
		__setQualifierClassTo__(org.cyk.utility.playground.server.System.class, MenuBuilderMapGetter.class);
	}
	
	@Override
	public void __destroy__(Object object) {}
	
	/**/
	
}
