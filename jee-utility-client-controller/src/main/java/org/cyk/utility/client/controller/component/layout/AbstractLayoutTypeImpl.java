package org.cyk.utility.client.controller.component.layout;

import java.io.Serializable;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.ComponentRoles;

public abstract class AbstractLayoutTypeImpl extends AbstractObject implements LayoutType,Serializable {
	private static final long serialVersionUID = 1L;

	private ComponentRoles roles;
	
	@Override
	public ComponentRoles getRoles() {
		return roles;
	}
	
	@Override
	public ComponentRoles getRoles(Boolean injectIfNull) {
		return (ComponentRoles) __getInjectIfNull__(FIELD_ROLES, injectIfNull);
	}
	
	@Override
	public LayoutType setRoles(ComponentRoles roles) {
		this.roles = roles;
		return this;
	}
	
	public static final String FIELD_ROLES = "roles";
}
