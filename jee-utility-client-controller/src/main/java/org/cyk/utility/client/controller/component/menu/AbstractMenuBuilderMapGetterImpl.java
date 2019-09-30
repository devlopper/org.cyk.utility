package org.cyk.utility.client.controller.component.menu;

import java.io.Serializable;
import java.security.Principal;

import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.request.RequestPrincipalGetter;
import org.cyk.utility.scope.ScopeSession;

public abstract class AbstractMenuBuilderMapGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<MenuBuilderMap> implements MenuBuilderMapGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Object request;
	
	@Override
	protected MenuBuilderMap __execute__() throws Exception {
		Object request = ValueHelper.returnOrThrowIfBlank("Menu Builder Map Request", getRequest());
		Principal principal = __inject__(RequestPrincipalGetter.class).setRequest(request).execute().getOutput();
		MenuBuilder sessionMenuBuilder = __instanciateSessionMenuBuilder__(request, principal);
		____execute____(sessionMenuBuilder,request,principal);
		return __inject__(MenuBuilderMap.class).set(ScopeSession.class,sessionMenuBuilder);
	}
	
	protected MenuBuilder __instanciateSessionMenuBuilder__(Object request,Principal principal) {
		return __inject__(MenuBuilder.class).setRenderType(__inject__(MenuRenderTypeRowBar.class));
	}
	
	protected void ____execute____(MenuBuilder sessionMenuBuilder,Object request,Principal principal) throws Exception {
		if(principal == null)
			____executePrincipalIsNull____(sessionMenuBuilder,request);
		else
			____executePrincipalIsNotNull____(sessionMenuBuilder,request, principal);
	}
	
	protected abstract void ____executePrincipalIsNull____(MenuBuilder sessionMenuBuilder,Object request) throws Exception;
	
	protected abstract void ____executePrincipalIsNotNull____(MenuBuilder sessionMenuBuilder,Object request,Principal principal) throws Exception;
	
	@Override
	public Object getRequest() {
		return request;
	}
	
	@Override
	public MenuBuilderMapGetter setRequest(Object request) {
		this.request = request;
		return this;
	}
}
