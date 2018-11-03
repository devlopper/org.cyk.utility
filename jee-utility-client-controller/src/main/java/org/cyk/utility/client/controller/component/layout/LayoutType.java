package org.cyk.utility.client.controller.component.layout;

import org.cyk.utility.client.controller.Objectable;
import org.cyk.utility.client.controller.component.ComponentRoles;

public interface LayoutType extends Objectable {

	ComponentRoles getRoles();
	ComponentRoles getRoles(Boolean injectIfNull);
	LayoutType setRoles(ComponentRoles roles);
	
}
