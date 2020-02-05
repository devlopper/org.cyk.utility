package org.cyk.utility.server.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.persistence.query.Queries;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		Queries.IS_REGISTERABLE_TO_ENTITY_MANAGER = Boolean.TRUE;
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
		__inject__(org.cyk.utility.ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Override
	public void __destroy__(Object object) {}
}
