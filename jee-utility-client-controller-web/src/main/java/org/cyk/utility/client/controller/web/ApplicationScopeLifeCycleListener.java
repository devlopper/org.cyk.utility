package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.annotation.Web;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.session.SessionUserGetterImpl;
import org.cyk.utility.context.ContextParameterValueGetter;
import org.cyk.utility.request.RequestPrincipalGetter;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		__setQualifiersClasses__(ContextParameterValueGetter.class, Web.class);
		__setQualifiersClasses__(RequestPrincipalGetter.class, Web.class);
		__inject__(FunctionRunnableMap.class).set(SessionUserGetterImpl.class, SessionUserGetterFunctionRunnableImpl.class,LEVEL);
		
		__inject__(org.cyk.utility.client.controller.ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Override
	public void __destroy__(Object object) {}
	
	/**/
	
	public static final Integer LEVEL = new Integer(org.cyk.utility.client.controller.ApplicationScopeLifeCycleListener.LEVEL+1);
}
