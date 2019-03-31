package org.cyk.utility.client.controller.component.menu;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.scope.Scope;

public interface MenuGetter extends FunctionWithPropertiesAsInput<Menu> {

	Class<? extends Scope> getScopeClass();
	MenuGetter setScopeClass(Class<? extends Scope> scopeClass);
	
	Object getRequest();
	MenuGetter setRequest(Object request);
}
