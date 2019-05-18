package org.cyk.utility.client.controller.component.menu;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.client.controller.session.SessionAttributeEnumeration;
import org.cyk.utility.client.controller.session.SessionAttributeGetter;
import org.cyk.utility.client.controller.session.SessionAttributeSetter;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.scope.Scope;
import org.cyk.utility.scope.ScopeSession;

public class MenuGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Menu> implements MenuGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<? extends Scope> scopeClass;
	private Object request;
	
	@Override
	protected Menu __execute__() throws Exception {
		Object request = getRequest();
		Menu menu = null;
		Class<? extends Scope> scopeClass = getScopeClass();
		if(__injectClassHelper__().isInstanceOf(scopeClass, ScopeSession.class)) {
			menu = __inject__(SessionAttributeGetter.class).setRequest(request).setAttribute(SessionAttributeEnumeration.MENU).execute().getOutputAs(Menu.class);
		}
		if(menu == null) {
			MenuBuilderMap map = __inject__(MenuBuilderMapGetter.class).setRequest(request).execute().getOutput();
			if(map!=null) {
				MenuBuilder builder = null;
				for(@SuppressWarnings("rawtypes") Map.Entry<Class,MenuBuilder> entry : map.getEntries())
					if(__injectClassHelper__().isInstanceOf(entry.getKey(), ScopeSession.class)) {
						builder = entry.getValue();
						break;
					}
				if(builder!=null) {
					if(builder.getRequest() == null)
						builder.setRequest(request);
					menu = builder.execute().getOutput();
				}
			}
			
			if(__injectClassHelper__().isInstanceOf(scopeClass, ScopeSession.class)) {
				__inject__(SessionAttributeSetter.class).setRequest(request).setAttribute(SessionAttributeEnumeration.MENU).setValue(menu).execute();
			}
		}
		
		return menu;
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
