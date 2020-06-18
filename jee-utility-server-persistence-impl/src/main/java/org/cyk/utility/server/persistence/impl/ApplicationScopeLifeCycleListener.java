package org.cyk.utility.server.persistence.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManagerFactory;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.annotation.Persistence;
import org.cyk.utility.__kernel__.instance.InstanceGetter;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		if(EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY == null) {
			LogHelper.logInfo("Injecting Entity Manager Factory Instance", getClass());
			EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = DependencyInjection.inject(EntityManagerFactory.class);
		}
		LogHelper.logInfo("Entity Manager Factory Instance is : "+EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY,getClass());
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
		DependencyInjection.setQualifierClassTo(Persistence.Class.class, InstanceGetter.class);
		__inject__(org.cyk.utility.server.persistence.ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Override
	public void __destroy__(Object object) {}
	
	/**/
}
