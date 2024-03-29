package org.cyk.utility.client.controller.component.menu;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.scope.Scope;

public class MenuGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Menu> implements MenuGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<? extends Scope> scopeClass;
	private Object request;
	
	@Override
	protected Menu __execute__() throws Exception {
		/*
		Object request = getRequest();
		Menu menu = null;
		
		Class<? extends Scope> scopeClass = getScopeClass();
		if(ClassHelper.isInstanceOf(scopeClass, ScopeSession.class)) {
			menu = (Menu) SessionHelper.getAttributeValue(SessionAttributeEnumeration.MENU);
		}
		if(menu == null) {
			MenuBuilderMap map = __inject__(MenuBuilderMapGetter.class).setRequest(request).execute().getOutput();
			if(map!=null) {
				MenuBuilder builder = null;
				for(@SuppressWarnings("rawtypes") Map.Entry<Class,MenuBuilder> entry : map.getEntries())
					if(ClassHelper.isInstanceOf(entry.getKey(), ScopeSession.class)) {
						builder = entry.getValue();
						break;
					}
				if(builder!=null) {
					if(builder.getRequest() == null)
						builder.setRequest(request);
					menu = builder.execute().getOutput();
				}
			}
			
			if(ClassHelper.isInstanceOf(scopeClass, ScopeSession.class)) {
				SessionHelper.setAttributeValueFromRequest(SessionAttributeEnumeration.MENU,menu,request);
			}
		}
		return menu;
		*/
		throw new RuntimeException("How is it used ????");
	}
	
	@Override
	public Class<? extends Scope> getScopeClass() {
		return scopeClass;
	}

	@Override
	public MenuGetter setScopeClass(Class<? extends Scope> scopeClass) {
		this.scopeClass = scopeClass;
		return this;
	}

	@Override
	public Object getRequest() {
		return request;
	}
	
	@Override
	public MenuGetter setRequest(Object request) {
		this.request = request;
		return this;
	}
}
