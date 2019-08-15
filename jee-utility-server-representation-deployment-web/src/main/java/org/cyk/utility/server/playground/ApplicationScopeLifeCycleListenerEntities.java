package org.cyk.utility.server.playground;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.server.persistence.AbstractApplicationScopeLifeCycleListenerEntities;
import org.cyk.utility.server.persistence.PersistableClassesGetter;

@ApplicationScoped
public class ApplicationScopeLifeCycleListenerEntities extends AbstractApplicationScopeLifeCycleListenerEntities implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		super.__initialize__(object);
		DependencyInjection.setQualifierClassTo(org.cyk.utility.__kernel__.annotation.Test.Class.class, PersistableClassesGetter.class);
	}
	
	@Override
	public void __destroy__(Object object) {}
	
	/**/
	
}