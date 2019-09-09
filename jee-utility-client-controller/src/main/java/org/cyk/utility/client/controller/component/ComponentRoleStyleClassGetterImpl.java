package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Deprecated
public class ComponentRoleStyleClassGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<String> implements ComponentRoleStyleClassGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private ComponentRole role;
	
	@Override
	public ComponentRole getRole() {
		return role;
	}

	@Override
	public ComponentRoleStyleClassGetter setRole(ComponentRole role) {
		this.role = role;
		return this;
	}

}
