package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.AbstractApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.annotation.Web;
import org.cyk.utility.client.controller.component.theme.ThemeColorGetter;
import org.cyk.utility.request.RequestPrincipalGetter;

@ApplicationScoped
public class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void __initialize__(Object object) {
		__setQualifierClassTo__(Web.class,RequestPrincipalGetter.class,ThemeColorGetter.class);
		__inject__(org.cyk.utility.client.controller.ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Override
	public void __destroy__(Object object) {}
	
	/**/
	
	@Deprecated
	public static final Integer LEVEL = Integer.valueOf(org.cyk.utility.client.controller.ApplicationScopeLifeCycleListener.LEVEL+1);
}
