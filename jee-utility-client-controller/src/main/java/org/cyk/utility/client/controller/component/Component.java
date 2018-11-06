package org.cyk.utility.client.controller.component;

import org.cyk.utility.client.controller.Objectable;
import org.cyk.utility.client.controller.component.layout.LayoutItem;

public interface Component extends Objectable {

	LayoutItem getLayoutItem();
	Component setLayoutItem(LayoutItem layoutItem);
	
	ComponentRoles getRoles();
	ComponentRoles getRoles(Boolean injectIfNull);
	Component setRoles(ComponentRoles roles);
	
	
	Object getTargetModel();
	void setTargetModel(Object targetModel);//void because of java bean issue
}
