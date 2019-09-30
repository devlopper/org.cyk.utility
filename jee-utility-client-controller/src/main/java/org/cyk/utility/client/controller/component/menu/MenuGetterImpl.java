package org.cyk.utility.client.controller.component.menu;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.client.controller.session.SessionAttributeEnumeration;
import org.cyk.utility.client.controller.session.SessionHelper;
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
		if(ClassHelper.isInstanceOf(scopeClass, ScopeSession.class)) {
			menu = (Menu) __inject__(SessionHelper.class).getAttributeValue(SessionAttributeEnumeration.MENU,request);
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
				__inject__(SessionHelper.class).setAttributeValue(SessionAttributeEnumeration.MENU,menu,request);
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
