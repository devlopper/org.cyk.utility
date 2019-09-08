package org.cyk.utility.client.controller.component;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

@Deprecated
public interface ComponentRoleStyleClassGetter extends FunctionWithPropertiesAsInput<String> {

	ComponentRole getRole();
	ComponentRoleStyleClassGetter setRole(ComponentRole role);
	
}
